package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutActivity;
import com.btrll.rooms.client.activities.OfficeListActivity;
import com.btrll.rooms.client.activities.RoomListActivity;
import com.btrll.rooms.client.activities.gauth.GauthActivity;
import com.btrll.rooms.client.activities.map.MapActivity;
import com.btrll.rooms.client.activities.room.RoomActivity;
import com.btrll.rooms.client.util.Gapi;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	public OfficeListActivity.View getOfficeListView();

	public EventBus getEventBus();

	public PlaceController getPlaceController();

	/**
	 * @return
	 */
	public RoomListActivity.View getUIView();

	public AboutActivity.View getAboutView();

	public GauthActivity.View getGauthView();

	public MapActivity.View getMapView();

	public RoomActivity.View getRoomView();

	public Gapi getGapi();

}
