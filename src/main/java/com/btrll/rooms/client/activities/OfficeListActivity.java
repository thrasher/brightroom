/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.btrll.rooms.client.activities;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.activities.animation.AnimationPlace;
import com.btrll.rooms.client.activities.home.Topic;
import com.btrll.rooms.client.util.JSOModel;
import com.btrll.rooms.client.util.JsonRequest;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;

/**
 * @author Daniel Kurka
 * 
 */
public class OfficeListActivity extends MGWTAbstractActivity {
	static final Logger logger = Logger.getLogger("OfficeListActivity");

	private final ClientFactory clientFactory;
	private EventBus eventBus;

	public OfficeListActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
		OfficeListView view = getOfficeListView();

		view.setTitle("BrightRoom");
		view.setRightButtonText("about");

		view.getFirstHeader().setText("Location");

		addHandlerRegistration(view.getCellSelectedHandler()
				.addCellSelectedHandler(new CellSelectedHandler() {

					@Override
					public void onCellSelected(CellSelectedEvent event) {
						int index = event.getIndex();						
						if (index == 0) {
							clientFactory.getPlaceController().goTo(
									new AnimationPlace());
							return;
						}
						if (index == 1) {
							clientFactory.getPlaceController().goTo(
									new UIPlace());

							return;
						}

					}
				}));

		addHandlerRegistration(view.getAboutButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						clientFactory.getPlaceController().goTo(
								new AboutPlace());

					}
				}));

		panel.setWidget(view);

		// refresh the office list now
		refreshOfficeList();
	}

	@Override
	public void onStop() {
		super.onStop();
		eventBus = null;
	}

	private OfficeListView getOfficeListView() {
		return clientFactory.getOfficeListView();
	}

	private void refreshOfficeList() {
		JsonRequest req = new JsonRequest(new AsyncCallback<JSOModel>() {
			@Override
			public void onFailure(Throwable t) {
				logger.warning("failed to get office list " + t);
				Window.alert("Unable to fetch Office list. \n"
						+ t.getLocalizedMessage());
			}

			@Override
			public void onSuccess(JSOModel config) {
				// Early exit if this activity has already been
				// canceled.
				if (eventBus == null) {
					return;
				}

				logger.fine("successfully requested office list");

				ArrayList<Topic> list = new ArrayList<Topic>();
				JsArray<JSOModel> o = config.getArray("offices");
				for (int i = 0; i < o.length(); i++) {
					list.add(new Topic(o.get(i).get("name"), 5));
				}

				setOfficeList(list);
			}
		});
		req.send("config.json");
	}

	private void setOfficeList(ArrayList<Topic> officeList) {
		getOfficeListView().setTopics(officeList);
	}

}
