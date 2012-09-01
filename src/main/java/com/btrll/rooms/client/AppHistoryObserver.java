package com.btrll.rooms.client;

import java.util.logging.Logger;

import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.RoomListEntrySelectedEvent;
import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.activities.gauth.GauthEvent;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.event.ActionEvent;
import com.btrll.rooms.client.event.ActionNames;
import com.btrll.rooms.client.model.CalendarListResource;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.googlecode.mgwt.dom.client.event.mouse.HandlerRegistrationCollection;
import com.googlecode.mgwt.mvp.client.history.HistoryHandler;
import com.googlecode.mgwt.mvp.client.history.HistoryObserver;
import com.googlecode.mgwt.ui.client.MGWT;

public class AppHistoryObserver implements HistoryObserver {
	static final Logger logger = Logger.getLogger("AppHistoryObserver");
	private boolean sentToGauth = false; // true if the user needed Gauth flow

	@Override
	public void onPlaceChange(Place place, HistoryHandler handler) {

	}

	@Override
	public void onHistoryChanged(Place place, HistoryHandler handler) {

	}

	@Override
	public void onAppStarted(Place place, HistoryHandler historyHandler) {
		if (MGWT.getOsDetection().isPhone()) {
			onPhoneNav(place, historyHandler);
		} else {
			// tablet
			onTabletNav(place, historyHandler);

		}

	}

	@Override
	public HandlerRegistration bind(EventBus eventBus,
			final HistoryHandler historyHandler) {
		HandlerRegistrationCollection col = new HandlerRegistrationCollection();

		col.addHandlerRegistration(RoomListEntrySelectedEvent.register(
				eventBus, new RoomListEntrySelectedEvent.Handler() {

					@Override
					public void onRoomSelected(RoomListEntrySelectedEvent event) {
						CalendarListResource resourceId = event.getResourceId();
						Window.alert("going to: " + resourceId.getSummary());
						Place place = new RoomPlace(resourceId.getId());

						if (MGWT.getOsDetection().isTablet()) {
							historyHandler.replaceCurrentPlace(place);
							historyHandler.goTo(place, true);
						} else {
							historyHandler.goTo(place);
						}
					}
				}));

		col.addHandlerRegistration(ActionEvent.register(eventBus,
				ActionNames.BACK, new ActionEvent.Handler() {

					@Override
					public void onAction(ActionEvent event) {

						History.back();

					}
				}));

		col.addHandlerRegistration(eventBus.addHandler(GauthEvent.getType(),
				new GauthEvent.Handler() {
					@Override
					public void onGauth(GauthEvent event) {
						logger.fine("GauthEvent auth needed: "
								+ event.isAuthNeeded());
						if (!event.isAuthNeeded())
							historyHandler.goTo(new HomePlace());

					}
				}));
		// HandlerRegistration mission =
		// eventBus.addHandler(GauthEvent.getType(),
		// new GauthEvent.Handler() {
		// @Override
		// public void onGauth(GauthEvent event) {
		// if (!event.isAuthNeeded()) {
		// Timer ttt = new Timer() {
		//
		// @Override
		// public void run() {
		// doListTodaysMeetings();
		// }
		// };
		// ttt.schedule(10 * 1000);
		// }
		// }
		// });

		return col;
	}

	private native void doListTodaysMeetings() /*-{
		var d1 = new Date();
		d1.setHours(0, 0, 0, 0);
		var d2 = new Date();
		d2.setHours(24, 0, 0, 0);
		alert(d1 + "\n" + d2);
		$wnd.gapi.client.calendar.events
				.list(
						{
							'calendarId' : 'brightroll.com_2d3430343930353038373432@resource.calendar.google.com',
							'orderBy' : 'startTime',
							'singleEvents' : true,
							'timeMax' : d2,
							'timeMin' : d1
						}).execute(function(resp, raw) {
					$wnd.alert(raw);
					console.log(raw);
				});
	}-*/;

	private void onPhoneNav(Place place, HistoryHandler historyHandler) {
		if (place instanceof RoomPlace) { // detail view
			historyHandler.replaceCurrentPlace(new HomePlace());
			historyHandler.pushPlace(new RoomListPlace());
		} else if (place instanceof RoomListPlace) {
			historyHandler.replaceCurrentPlace(new HomePlace());
		} else if (place instanceof AboutPlace) {
			historyHandler.replaceCurrentPlace(new HomePlace());
		} else if (!(place instanceof HomePlace)) {
			logger.warning("unhandled nav place: " + place);
		}

	}

	private void onTabletNav(Place place, HistoryHandler historyHandler) {
		if (place instanceof AboutPlace) {
			historyHandler.replaceCurrentPlace(new HomePlace());
		} else if (place instanceof RoomListPlace) {
			historyHandler.replaceCurrentPlace(new HomePlace());
		} else if (place instanceof RoomPlace) {
			historyHandler.replaceCurrentPlace(new HomePlace());
		} else if (!(place instanceof HomePlace)) {
			logger.warning("unhandled nav place: " + place);
		}
	}
}
