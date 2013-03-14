package com.btrll.rooms.client;

import java.util.logging.Logger;

import com.btrll.rooms.client.activities.gauth.GauthActivity;
import com.btrll.rooms.client.activities.gauth.GauthEvent;
import com.btrll.rooms.client.css.AppBundle;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.UmbrellaException;
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
	static final Logger logger = Logger.getLogger("Rooms");

	// private final Messages messages = GWT.create(Messages.class);

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

		if (MGWT.getOsDetection().isTablet()) {
			// very nasty workaround because GWT does not correctly support
			// @media
			StyleInjector.inject(AppBundle.INSTANCE.css().getText());
		}

		final SimplePanel gPanel = new SimplePanel();
		final GauthActivity a = new GauthActivity(clientFactory);

		// HandlerRegistration addHandler =
		clientFactory.getEventBus().addHandler(GauthEvent.getType(),
				new GauthEvent.Handler() {
					@Override
					public void onGauth(GauthEvent event) {
						if (event.isAuthNeeded()) {
							logger.fine("auth is needed");
						} else {
							logger.fine("auth is done, loading the ui");
							if (null == a.mayStop()) {
								a.onStop();
								RootPanel.get().remove(gPanel);
								// gPanel.remove(clientFactory.getGauthView());
								Timer t = new Timer() {
									@Override
									public void run() {
										loadUi(clientFactory);
									}
								};
								t.schedule(1);
							}
						}
					}
				});

		// start Gauth, assuming we need to test the auth
		a.start(gPanel, clientFactory.getEventBus());

		HasWidgets container = RootPanel.get();
		container.add(gPanel);

		// remove host page spinner after ours is added
		DOM.getElementById("spinner").removeFromParent();
	}

	private void loadUi(ClientFactory clientFactory) {
		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);

		if (MGWT.getOsDetection().isTablet()) {
			createTabletDisplay(clientFactory);
		} else {
			createPhoneDisplay(clientFactory);
		}

		AppHistoryObserver historyObserver = new AppHistoryObserver(
				clientFactory);

		MGWTPlaceHistoryHandler historyHandler = new MGWTPlaceHistoryHandler(
				historyMapper, historyObserver);

		historyHandler.register(clientFactory.getPlaceController(),
				clientFactory.getEventBus(), new HomePlace());
		historyHandler.handleCurrentHistory();

		// Geolocation.getGeoLocationCached();
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
				if (GWT.isScript()) {
					// in the event of failure, just reload the page
					Window.alert("Oops, that's an error.\nPlease let jthrasher@brightroll.com know: "
							+ e.getMessage());
					Window.Location.replace("/#map:1");//.reload();
				} else {
					Window.alert("uncaught: " + e.getMessage());
					String s = buildStackTrace(e, "RuntimeException:\n");
					logger.severe(s);
					Window.alert(s);
					e.printStackTrace();
				}
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
			log += ": ";
			log += t.getMessage();

			// umbrella exception stack is not useful, so skip
			if (!(t instanceof UmbrellaException)) {
				StackTraceElement[] stackTrace = t.getStackTrace();
				if (stackTrace != null) {
					StringBuffer trace = new StringBuffer();

					for (int i = 0; i < stackTrace.length; i++) {
						trace.append("\n");
						trace.append(stackTrace[i].getClassName() + "."
								+ stackTrace[i].getMethodName() + "("
								+ stackTrace[i].getFileName() + ":"
								+ stackTrace[i].getLineNumber() + ")");
					}

					log += trace.toString();
				}
			}
			//
			Throwable cause = t.getCause();
			if (cause != null && cause != t) {
				log += "\n";
				log += buildStackTrace(cause, "CausedBy:\n");
			}
		}
		return log;
	}
}
