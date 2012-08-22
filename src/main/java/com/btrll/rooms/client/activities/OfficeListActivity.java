package com.btrll.rooms.client.activities;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.activities.home.Topic;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.util.JSOModel;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;

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
						clientFactory.getPlaceController().goTo(
								new MapPlace((long) getOffices().get(index)
										.getInt("id")));
						return;
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

	// private void refreshOfficeList() {
	// JsonRequest req = new JsonRequest(new AsyncCallback<JSOModel>() {
	// @Override
	// public void onFailure(Throwable t) {
	// logger.warning("failed to get office list " + t);
	// Window.alert("Unable to fetch Office list. \n"
	// + t.getLocalizedMessage());
	// }
	//
	// @Override
	// public void onSuccess(JSOModel config) {
	// // Early exit if this activity has already been
	// // canceled.
	// if (eventBus == null) {
	// return;
	// }
	//
	// logger.fine("successfully requested office list");
	//
	// ArrayList<Topic> list = new ArrayList<Topic>();
	// JsArray<JSOModel> o = config.getArray("offices");
	// for (int i = 0; i < o.length(); i++) {
	// list.add(new Topic(o.get(i).get("name"), 5));
	// }
	//
	// setOfficeList(list);
	// }
	// });
	// req.send("config.json");
	// }
	private native JsArray<JSOModel> getOffices() /*-{
		return $wnd.config.offices;
	}-*/;

	private void refreshOfficeList() {
		JsArray<JSOModel> o = getOffices();

		ArrayList<Topic> list = new ArrayList<Topic>();
		for (int i = 0; i < o.length(); i++) {
			list.add(new Topic(o.get(i).get("name"), o.get(i).get("id")));
		}

		setOfficeList(list);

	}

	private void setOfficeList(ArrayList<Topic> officeList) {
		getOfficeListView().setTopics(officeList);
	}

}
