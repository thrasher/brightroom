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
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.googlecode.mgwt.dom.client.event.mouse.HandlerRegistrationCollection;
import com.googlecode.mgwt.mvp.client.history.HistoryHandler;
import com.googlecode.mgwt.mvp.client.history.HistoryObserver;
import com.googlecode.mgwt.ui.client.MGWT;

public class AppHistoryObserver implements HistoryObserver {
	static final Logger logger = Logger.getLogger("AppHistoryObserver");

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
						logger.fine("going to: " + resourceId.getSummary());
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

		return col;
	}

	private void onPhoneNav(Place place, HistoryHandler historyHandler) {
		if (place instanceof RoomPlace) { // detail view
			RoomPlace rp = (RoomPlace) place;
			historyHandler.replaceCurrentPlace(new HomePlace());
			historyHandler.pushPlace(new RoomListPlace(rp.getResourceId()));
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
