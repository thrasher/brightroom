package com.btrll.rooms.client;

import com.btrll.rooms.client.css.AppBundle;
import com.btrll.rooms.client.places.HomePlace;
import com.btrll.rooms.client.util.Gapi;
import com.btrll.rooms.client.util.Geolocation;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.mvp.client.AnimationMapper;
import com.googlecode.mgwt.mvp.client.history.MGWTPlaceHistoryHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort.DENSITY;
import com.googlecode.mgwt.ui.client.dialog.TabletPortraitOverlay;
import com.googlecode.mgwt.ui.client.layout.MasterRegionHandler;
import com.googlecode.mgwt.ui.client.layout.OrientationRegionHandler;
import com.googlecode.mgwt.ui.client.util.SuperDevModeUtil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Rooms implements EntryPoint {

	private final Messages messages = GWT.create(Messages.class);

	private void start() {
		SuperDevModeUtil.showDevMode();

		ViewPort viewPort = new MGWTSettings.ViewPort();
		viewPort.setTargetDensity(DENSITY.MEDIUM);
		viewPort.setUserScaleAble(false).setMinimumScale(1.0)
				.setMinimumScale(1.0).setMaximumScale(1.0);

		MGWTSettings settings = new MGWTSettings();
		settings.setViewPort(viewPort);
		// settings.setIconUrl("images/logo.png"); // varies by device
		// settings.setStartUrl("images/"); // varies by device
		settings.setAddGlosToIcon(true);
		settings.setFullscreen(true);
		settings.setPreventScrolling(true);

		MGWT.applySettings(settings);
		MGWT.showAddressBar(false); // hide address bar on iPhones

		final ClientFactory clientFactory = new ClientFactoryImpl();

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);

		if (MGWT.getOsDetection().isTablet()) {

			// very nasty workaround because GWT does not corretly support
			// @media
			StyleInjector.inject(AppBundle.INSTANCE.css().getText());
			createTabletDisplay(clientFactory);
		} else {

			createPhoneDisplay(clientFactory);

		}

		AppHistoryObserver historyObserver = new AppHistoryObserver();

		MGWTPlaceHistoryHandler historyHandler = new MGWTPlaceHistoryHandler(
				historyMapper, historyObserver);

		historyHandler.register(clientFactory.getPlaceController(),
				clientFactory.getEventBus(), new HomePlace());
		historyHandler.handleCurrentHistory();

		final Gapi gapi = new Gapi(clientFactory.getEventBus());
		
//		Geolocation.getGeoLocationCached();

//		executeQuery("calendarList-rooms.json",
//				new AsyncCallback<JavaScriptObject>() {
//
//					@Override
//					public void onFailure(Throwable arg0) {
//						Window.alert("failure: " + arg0);
//					}
//
//					@Override
//					public void onSuccess(JavaScriptObject arg0) {
//						Window.alert("success: " + arg0);
//					}
//				});

	}

	public void executeQuery(String query,
			final AsyncCallback<JavaScriptObject> callback) {
		String url = GWT.getHostPageBaseURL() + query;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				URL.encode(url));
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable caught) {
					callback.onFailure(caught);
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					if (Response.SC_OK == response.getStatusCode()) {
						try {
							callback.onSuccess(JsonUtils.safeEval(response
									.getText()));
						} catch (IllegalArgumentException iax) {
							callback.onFailure(iax);
						}
					} else {
						// Better to use a typed exception here to indicate the
						// specific
						// cause of the failure.
						callback.onFailure(new Exception("Bad return code: "
								+ response.getStatusCode()));
					}
				}
			});
		} catch (RequestException e) {
			callback.onFailure(e);
		}
	}

	private void createPhoneDisplay(ClientFactory clientFactory) {
		AnimatableDisplay display = GWT.create(AnimatableDisplay.class);

		PhoneActivityMapper appActivityMapper = new PhoneActivityMapper(
				clientFactory);

		PhoneAnimationMapper appAnimationMapper = new PhoneAnimationMapper();

		AnimatingActivityManager activityManager = new AnimatingActivityManager(
				appActivityMapper, appAnimationMapper,
				clientFactory.getEventBus());

		activityManager.setDisplay(display);

		RootPanel.get().add(display);

	}

	private void createTabletDisplay(ClientFactory clientFactory) {
		SimplePanel navContainer = new SimplePanel();
		navContainer.getElement().setId("nav");
		navContainer.getElement().addClassName("landscapeonly");
		AnimatableDisplay navDisplay = GWT.create(AnimatableDisplay.class);

		final TabletPortraitOverlay tabletPortraitOverlay = new TabletPortraitOverlay();

		new OrientationRegionHandler(navContainer, tabletPortraitOverlay,
				navDisplay);
		new MasterRegionHandler(clientFactory.getEventBus(), "nav",
				tabletPortraitOverlay);

		ActivityMapper navActivityMapper = new TabletNavActivityMapper(
				clientFactory);

		AnimationMapper navAnimationMapper = new TabletNavAnimationMapper();

		AnimatingActivityManager navActivityManager = new AnimatingActivityManager(
				navActivityMapper, navAnimationMapper,
				clientFactory.getEventBus());

		navActivityManager.setDisplay(navDisplay);

		RootPanel.get().add(navContainer);

		SimplePanel mainContainer = new SimplePanel();
		mainContainer.getElement().setId("main");
		AnimatableDisplay mainDisplay = GWT.create(AnimatableDisplay.class);

		TabletMainActivityMapper tabletMainActivityMapper = new TabletMainActivityMapper(
				clientFactory);

		AnimationMapper tabletMainAnimationMapper = new TabletMainAnimationMapper();

		AnimatingActivityManager mainActivityManager = new AnimatingActivityManager(
				tabletMainActivityMapper, tabletMainAnimationMapper,
				clientFactory.getEventBus());

		mainActivityManager.setDisplay(mainDisplay);
		mainContainer.setWidget(mainDisplay);

		RootPanel.get().add(mainContainer);

	}

	@Override
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				Window.alert("uncaught: " + e.getMessage());
				String s = buildStackTrace(e, "RuntimeExceotion:\n");
				Window.alert(s);
				e.printStackTrace();

			}
		});

		new Timer() {
			@Override
			public void run() {
				start();
			}
		}.schedule(1);

	}

	private String buildStackTrace(Throwable t, String log) {

		if (t != null) {
			log += t.getClass().toString();
			log += t.getMessage();
			//
			StackTraceElement[] stackTrace = t.getStackTrace();
			if (stackTrace != null) {
				StringBuffer trace = new StringBuffer();

				for (int i = 0; i < stackTrace.length; i++) {
					trace.append(stackTrace[i].getClassName() + "."
							+ stackTrace[i].getMethodName() + "("
							+ stackTrace[i].getFileName() + ":"
							+ stackTrace[i].getLineNumber());
				}

				log += trace.toString();
			}
			//
			Throwable cause = t.getCause();
			if (cause != null && cause != t) {

				log += buildStackTrace(cause, "CausedBy:\n");

			}
		}
		return log;
	}
}
