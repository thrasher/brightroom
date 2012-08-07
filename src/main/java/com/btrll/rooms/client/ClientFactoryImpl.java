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
import com.btrll.rooms.client.activities.AboutViewGwtImpl;
import com.btrll.rooms.client.activities.ShowCaseListView;
import com.btrll.rooms.client.activities.ShowCaseListViewGwtImpl;
import com.btrll.rooms.client.activities.UIView;
import com.btrll.rooms.client.activities.UIViewImpl;
import com.btrll.rooms.client.activities.animation.AnimationView;
import com.btrll.rooms.client.activities.animation.AnimationViewGwtImpl;
import com.btrll.rooms.client.activities.animationdone.AnimationDoneView;
import com.btrll.rooms.client.activities.animationdone.AnimationDoneViewGwtImpl;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshDisplay;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshDisplayGwtImpl;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * @author Daniel Kurka
 * 
 */
public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus;
	private PlaceController placeController;
	private ShowCaseListView homeViewImpl;
	private UIView uiView;
	private AboutView aboutView;
	private AnimationView animationView;
	private AnimationDoneView animationDoneView;
	private PullToRefreshDisplayGwtImpl pullToRefreshView;

	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();

		placeController = new PlaceController(eventBus);

		homeViewImpl = new ShowCaseListViewGwtImpl();
	}

	@Override
	public ShowCaseListView getHomeView() {
		if (homeViewImpl == null) {
			homeViewImpl = new ShowCaseListViewGwtImpl();
		}
		return homeViewImpl;
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
	public PullToRefreshDisplay getPullToRefreshDisplay() {
		if (pullToRefreshView == null) {
			pullToRefreshView = new PullToRefreshDisplayGwtImpl();
		}
		return pullToRefreshView;
	}

}
