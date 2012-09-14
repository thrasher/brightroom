package com.btrll.rooms.client.activities.map;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.model.CalendarListResource;
import com.btrll.rooms.client.model.Office;
import com.btrll.rooms.client.util.Gapi;
import com.btrll.rooms.client.util.GapiResponseEvent;
import com.btrll.rooms.client.util.JSOModel;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler.PullActionHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel.Pullhandler;

public class MapActivity extends DetailActivity {
	public interface View extends DetailActivity.View {
		public void setHeaderPullHandler(Pullhandler pullHandler);

		public void setFooterPullHandler(Pullhandler pullHandler);

		public PullArrowWidget getPullHeader();

		public PullArrowWidget getPullFooter();

		public void refresh();

		public HasRefresh getPullPanel();

		public void setMap(String svg);
	}

	static final Logger logger = Logger.getLogger("MapActivity");
	private final ClientFactory clientFactory;
	private final String officeId;
	int batchCallId;
	private EventBus eventBus;
	private Timer refreshTimer;

	/**
	 * The delay in milliseconds between calls to refresh.
	 */
	private static final int REFRESH_DELAY = 2000;

	public MapActivity(ClientFactory clientFactory, MapPlace place) {
		super(clientFactory.getMapView(), "nav");
		this.clientFactory = clientFactory;
		this.officeId = place.getOfficeId();

		exportStaticMethods();
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		logger.fine("MapActivity start");
		super.start(panel, eventBus);
		this.eventBus = eventBus;

		addHandlerRegistration(eventBus.addHandler(GapiResponseEvent.getType(),
				new GapiResponseEvent.Handler() {

					@Override
					public void onGapiResponse(GapiResponseEvent event) {
						// handle success
						if (event.getCallId() == batchCallId) {
							handleBatchResponse(event.getResp());
						}
					}
				}));

		Office office = clientFactory.getModelDao().getOfficeById(officeId);

		final View view = clientFactory.getMapView();
		view.getHeader().setText(office.getName());
		view.getMainButtonText().setText("Nav");
		view.getBackbuttonText().setText("Back");

		view.getPullHeader().setHTML("pull down");
		view.getPullFooter().setHTML("pull up");

		PullArrowStandardHandler headerHandler = new PullArrowStandardHandler(
				view.getPullHeader(), view.getPullPanel());
		headerHandler.setErrorText("Error");
		headerHandler.setLoadingText("Loading " + office.getName());
		headerHandler.setNormalText("pull to update " + office.getName());
		headerHandler.setPulledText("release to refresh " + office.getName());
		headerHandler.setPullActionHandler(new PullActionHandler() {
			@Override
			public void onPullAction(final AsyncCallback<Void> callback) {
				new Timer() {
					@Override
					public void run() {
						// callback.onFailure(null);
						// view.render(list);
						view.refresh();
						callback.onSuccess(null);
						refreshTaskList();
					}
					// TODO change to 1ms, once jsni hook reloads data
				}.schedule(1);
			}
		});
		view.setHeaderPullHandler(headerHandler);

		PullArrowStandardHandler footerHandler = new PullArrowStandardHandler(
				view.getPullFooter(), view.getPullPanel());
		footerHandler.setErrorText("Error");
		footerHandler.setLoadingText("Loading " + office.getName());
		footerHandler.setNormalText("pull to update " + office.getName());
		footerHandler.setPulledText("release to refresh " + office.getName());
		footerHandler.setPullActionHandler(new PullActionHandler() {
			@Override
			public void onPullAction(final AsyncCallback<Void> callback) {
				new Timer() {
					@Override
					public void run() {
						// TODO: refresh display data
						// callback.onFailure(null);
						// display.render(list);
						view.refresh();
						callback.onSuccess(null);
						refreshTaskList();
					}
					// TODO change to 1ms, once jsni hook reloads data
				}.schedule(1);
			}
		});
		view.setFooterPullHandler(footerHandler);

		view.setMap(office.getMap());

		panel.setWidget(view);

		// Create a timer to periodically refresh the task list.
		refreshTimer = new Timer() {
			@Override
			public void run() {
				refreshTaskList();
			}
		};
		refreshTaskList();
	}

	@Override
	public void onStop() {
		eventBus = null;
		// Kill the refresh timer.
		if (refreshTimer != null) {
			refreshTimer.cancel();
		}
	}

	private void refreshTaskList() {
		Office office = clientFactory.getModelDao().getOfficeById(officeId);

		batchCallId = Gapi.getCallId();
		clientFactory.getGapi().batchCalendars(batchCallId,
				clientFactory.getModelDao().getRooms(office));

		// TODO: Restart the timer.
		// refreshTimer.schedule(REFRESH_DELAY);
	}

	private native void exportStaticMethods() /*-{
		var x = this;
		$wnd.__btrll_handleMapClick = function(id) {
			$entry(x.@com.btrll.rooms.client.activities.map.MapActivity::handleMapClick(Ljava/lang/String;)(id));
		}
	}-*/;

	private void handleMapClick(String id) {
		CalendarListResource c = clientFactory.getModelDao().getRoomByD3id(id);
		if (c == null) {
			Window.alert("Sorry, " + id + " is not a BrightRoom.");
			return;
		}
		clientFactory.getPlaceController().goTo(new RoomPlace(c.getId()));
	}

	private void handleBatchResponse(JSOModel resp) {
		// Early exit if this activity has already been canceled.
		if (eventBus == null) {
			return;
		}

		// TODO: wow, this is some sloppy sh!t
		Office office = clientFactory.getModelDao().getOfficeById(officeId);
		JsArray<CalendarListResource> list = clientFactory.getModelDao()
				.getRooms(office);
		for (int i = 0; i < list.length(); i++) {
			CalendarListResource room = list.get(i);
			JSOModel foo = resp.getObject(room.getId());
			if (foo != null) {
				JSOModel result = foo.getObject("result");
				JSOModel event = clientFactory.getModelDao().findCurrentEvent(
						result.getArray("items"));
				boolean isBusy = result.getArray("items") != null
						&& event != null;
				// Window.alert(result.get("summary") + " : " + isBusy);
				updateColor(room.getD3id(), isBusy);
			}
		}
	}

	private native void updateColor(String abbr, boolean isBusy) /*-{
		if (isBusy) {
			$wnd.colorRoomOccupied(abbr);
		} else {
			$wnd.colorRoomAvalable(abbr);
		}
	}-*/;

}
