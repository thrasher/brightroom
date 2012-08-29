package com.btrll.rooms.client.activities;

import java.util.ArrayList;
import java.util.List;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.activities.RoomListEntrySelectedEvent.UIEntry;
import com.btrll.rooms.client.event.ActionEvent;
import com.btrll.rooms.client.event.ActionNames;
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

		public void renderItems(List<Item> items);

		public void setSelectedIndex(int index, boolean selected);
	}

	private final ClientFactory clientFactory;

	private int oldIndex;

	private List<Item> items;

	public RoomListActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;

	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		final View view = clientFactory.getUIView();

		view.setBackButtonText("Home");
		view.setTitle("San Francisco");

		addHandlerRegistration(view.getBackButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						ActionEvent.fire(eventBus, ActionNames.BACK);

					}
				}));
		items = createItems();
		view.renderItems(items);

		addHandlerRegistration(view.getList().addCellSelectedHandler(
				new CellSelectedHandler() {

					@Override
					public void onCellSelected(CellSelectedEvent event) {
						int index = event.getIndex();

						view.setSelectedIndex(oldIndex, false);
						view.setSelectedIndex(index, true);
						oldIndex = index;

						RoomListEntrySelectedEvent.fire(eventBus,
								items.get(index).getEntry());

					}
				}));

		panel.setWidget(view);

	}

	/**
	 * @return
	 */
	private List<Item> createItems() {
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(new Item("ButtonBar", UIEntry.BUTTON_BAR));
		list.add(new Item("Buttons", UIEntry.BUTTONS));
		list.add(new Item("Carousel", UIEntry.CAROUSEL));
		list.add(new Item("Elements", UIEntry.ELEMENTS));
		list.add(new Item("Forms", UIEntry.FORMS));
		list.add(new Item("Group List", UIEntry.GROUP_LIST));
		list.add(new Item("Popups", UIEntry.POPUPS));
		list.add(new Item("ProgressBar", UIEntry.PROGRESS_BAR));
		list.add(new Item("ProgressIndicator", UIEntry.PROGRESS_INDICATOR));
		list.add(new Item("PullToRefresh", UIEntry.PULL_TO_REFRESH));
		list.add(new Item("Scroll Widget", UIEntry.SCROLL_WIDGET));
		list.add(new Item("Searchbox", UIEntry.SEARCH_BOX));
		list.add(new Item("Slider", UIEntry.SLIDER));
		list.add(new Item("TabBar", UIEntry.TABBAR));
		return list;
	}

}
