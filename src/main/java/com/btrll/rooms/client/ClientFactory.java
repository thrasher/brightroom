/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutView;
import com.btrll.rooms.client.activities.OfficeListView;
import com.btrll.rooms.client.activities.UIView;
import com.btrll.rooms.client.activities.animation.AnimationView;
import com.btrll.rooms.client.activities.animationdone.AnimationDoneView;
import com.btrll.rooms.client.activities.gauth.GauthActivity;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshDisplay;
import com.btrll.rooms.client.util.Gapi;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author Daniel Kurka
 * 
 */
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

	public PullToRefreshDisplay getPullToRefreshDisplay();

	public GauthActivity.View getGauthView();
	
	public Gapi getGapi();

}
