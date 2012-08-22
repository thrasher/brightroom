package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutView;
import com.btrll.rooms.client.activities.OfficeListView;
import com.btrll.rooms.client.activities.UIView;
import com.btrll.rooms.client.activities.animation.AnimationView;
import com.btrll.rooms.client.activities.animationdone.AnimationDoneView;
import com.btrll.rooms.client.activities.gauth.GauthActivity;
import com.btrll.rooms.client.util.Gapi;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	public OfficeListView getOfficeListView();

	public EventBus getEventBus();

	public PlaceController getPlaceController();

	/**
	 * @return
	 */
	public UIView getUIView();

	public AboutView getAboutView();

	public AnimationView getAnimationView();

	public AnimationDoneView getAnimationDoneView();

	public GauthActivity.View getGauthView();

	public com.btrll.rooms.client.activities.map.MapActivity.View getMapView();

	public Gapi getGapi();

}
