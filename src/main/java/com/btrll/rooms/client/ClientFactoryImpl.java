package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutActivity;
import com.btrll.rooms.client.activities.AboutView;
import com.btrll.rooms.client.activities.OfficeListActivity;
import com.btrll.rooms.client.activities.OfficeListView;
import com.btrll.rooms.client.activities.RoomListActivity;
import com.btrll.rooms.client.activities.RoomListView;
import com.btrll.rooms.client.activities.gauth.GauthActivity;
import com.btrll.rooms.client.activities.gauth.GauthView;
import com.btrll.rooms.client.activities.map.MapActivity;
import com.btrll.rooms.client.activities.map.MapView;
import com.btrll.rooms.client.activities.room.RoomActivity;
import com.btrll.rooms.client.activities.room.RoomView;
import com.btrll.rooms.client.util.Gapi;
import com.btrll.rooms.client.util.GA;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus;
	private PlaceController placeController;
	private OfficeListActivity.View officeListViewImpl;
	private RoomListActivity.View uiView;
	private AboutActivity.View aboutView;
	private Gapi gapi;
	private GauthActivity.View gauthView;
	private RoomActivity.View roomView;
	private MapActivity.View mapView;
	private ModelDao modelDao;
	private GA ga;

	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();

		placeController = new PlaceController(eventBus);

		officeListViewImpl = new OfficeListView();
	}

	@Override
	public OfficeListActivity.View getOfficeListView() {
		if (officeListViewImpl == null) {
			officeListViewImpl = new OfficeListView();
		}
		return officeListViewImpl;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public RoomListActivity.View getRoomListView() {
		if (uiView == null) {
			uiView = new RoomListView();
		}
		return uiView;
	}

	@Override
	public AboutActivity.View getAboutView() {
		if (aboutView == null) {
			aboutView = new AboutView();
		}

		return aboutView;
	}

	@Override
	public GauthActivity.View getGauthView() {
		if (gauthView == null) {
			gauthView = new GauthView();
		}
		return gauthView;
	}

	@Override
	public Gapi getGapi() {
		if (gapi == null) {
			gapi = new Gapi(getEventBus());
		}
		return gapi;
	}

	@Override
	public MapActivity.View getMapView() {
		if (mapView == null) {
			mapView = new MapView();
		}
		return mapView;
	}

	@Override
	public RoomActivity.View getRoomView() {
		if (roomView == null) {
			roomView = new RoomView();
		}
		return roomView;
	}

	@Override
	public ModelDao getModelDao() {
		if (modelDao == null) {
			modelDao = new ModelDao();
		}
		return modelDao;
	}

	@Override
	public GA getGA() {
		if (ga == null) {
			ga = new GA("UA-34537948-1");
		}
		return ga;
	}

}
