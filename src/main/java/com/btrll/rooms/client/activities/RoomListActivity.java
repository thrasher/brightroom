package com.btrll.rooms.client.activities;

import java.util.ArrayList;
import java.util.List;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.event.ActionEvent;
import com.btrll.rooms.client.event.ActionNames;
import com.btrll.rooms.client.model.CalendarListResource;
import com.btrll.rooms.client.model.Office;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.googlecode.mgwt.ui.client.widget.celllist.HasCellSelectedHandler;

public class RoomListActivity extends MGWTAbstractActivity {
	public interface View extends IsWidget {
		public void setBackButtonText(String text);

		public HasTapHandlers getBackButton();

		public void setTitle(String title);

		public HasCellSelectedHandler getList();

		public void renderTopics(List<Topic> Topics);

		public void setSelectedIndex(int index, boolean selected);
	}

	private final ClientFactory clientFactory;
	// private final MapPlace place;
	private int oldIndex;
	private final Office office;

	public RoomListActivity(ClientFactory clientFactory, MapPlace place) {
		this.clientFactory = clientFactory;
		this.office = clientFactory.getModelDao().getOfficeById(
				place.getOfficeId());
	}

	public RoomListActivity(ClientFactory clientFactory, RoomListPlace place) {
		this.clientFactory = clientFactory;
		this.office = clientFactory.getModelDao().getOfficeById(
				place.getOfficeId());
	}

	public RoomListActivity(ClientFactory clientFactory, RoomPlace place) {
		this.clientFactory = clientFactory;
		this.office = clientFactory.getModelDao().getOfficeByRoomId(
				place.getResourceId());
	}

	public boolean isCurrent(Place place) {
		if (place instanceof MapPlace) {
			MapPlace mapPlace = (MapPlace) place;
			return mapPlace.getOfficeId().equals(office.getId());
		} else if (place instanceof RoomListPlace) {
			RoomListPlace rlp = (RoomListPlace) place;
			return rlp.getOfficeId().equals(office.getId());
		} else if (place instanceof RoomPlace) {
			RoomPlace rp = (RoomPlace) place;
			JsArray<CalendarListResource> rooms = clientFactory.getModelDao()
					.getRooms(office);
			for (int i = 0; i < rooms.length(); i++) {
				CalendarListResource room = rooms.get(i);
				if (room.getId().equals(rp.getResourceId())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		final View view = clientFactory.getRoomListView();

		view.setBackButtonText("Home");
		view.setTitle(office.getName());

		addHandlerRegistration(view.getBackButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						ActionEvent.fire(eventBus, ActionNames.BACK);

					}
				}));

		addHandlerRegistration(view.getList().addCellSelectedHandler(
				new CellSelectedHandler() {

					@Override
					public void onCellSelected(CellSelectedEvent event) {
						int index = event.getIndex();

						view.setSelectedIndex(oldIndex, false);
						view.setSelectedIndex(index, true);
						oldIndex = index;

						// TODO: avoid re-query by storing model better
						String summary = event.getTargetElement()
								.getInnerText();
						CalendarListResource room = clientFactory.getModelDao()
								.getRoomBySummary(summary);
						RoomListEntrySelectedEvent.fire(eventBus, room);

					}
				}));

		panel.setWidget(view);

		refreshRoomList();
	}

	private void refreshRoomList() {
		JsArray<CalendarListResource> rooms = clientFactory.getModelDao()
				.getRooms(office);

		ArrayList<Topic> list = new ArrayList<Topic>();
		for (int i = 0; i < rooms.length(); i++) {
			CalendarListResource room = rooms.get(i);
			list.add(new Topic(room.getSummary(), room.getId()));
		}

		setRoomList(list);
	}

	private void setRoomList(List<Topic> roomList) {
		clientFactory.getRoomListView().renderTopics(roomList);
		// eventBus.fireEventFromSource(new RoomListUpdateEvent(roomList),
		// this);
	}

}
