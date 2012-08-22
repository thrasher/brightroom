package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutView;
import com.btrll.rooms.client.activities.AboutViewGwtImpl;
import com.btrll.rooms.client.activities.OfficeListView;
import com.btrll.rooms.client.activities.OfficeListViewGwtImpl;
import com.btrll.rooms.client.activities.UIView;
import com.btrll.rooms.client.activities.UIViewImpl;
import com.btrll.rooms.client.activities.animation.AnimationView;
import com.btrll.rooms.client.activities.animation.AnimationViewGwtImpl;
import com.btrll.rooms.client.activities.animationdone.AnimationDoneView;
import com.btrll.rooms.client.activities.animationdone.AnimationDoneViewGwtImpl;
import com.btrll.rooms.client.activities.gauth.GauthActivity;
import com.btrll.rooms.client.activities.gauth.GauthView;
import com.btrll.rooms.client.activities.map.MapActivity;
import com.btrll.rooms.client.activities.map.MapView;
import com.btrll.rooms.client.util.Gapi;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus;
	private PlaceController placeController;
	private OfficeListView officeListViewImpl;
	private UIView uiView;
	private AboutView aboutView;
	private AnimationView animationView;
	private AnimationDoneView animationDoneView;
	private Gapi gapi;
	private GauthActivity.View gauthView;
	private com.btrll.rooms.client.activities.map.MapView mapView;

	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();

		placeController = new PlaceController(eventBus);

		officeListViewImpl = new OfficeListViewGwtImpl();
	}

	@Override
	public OfficeListView getOfficeListView() {
		if (officeListViewImpl == null) {
			officeListViewImpl = new OfficeListViewGwtImpl();
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
	public UIView getUIView() {
		if (uiView == null) {
			uiView = new UIViewImpl();
		}
		return uiView;
	}

	@Override
	public AboutView getAboutView() {
		if (aboutView == null) {
			aboutView = new AboutViewGwtImpl();
		}

		return aboutView;
	}

	@Override
	public AnimationView getAnimationView() {
		if (animationView == null) {
			animationView = new AnimationViewGwtImpl();
		}
		return animationView;
	}

	@Override
	public AnimationDoneView getAnimationDoneView() {
		if (animationDoneView == null) {
			animationDoneView = new AnimationDoneViewGwtImpl();
		}
		return animationDoneView;
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
}
