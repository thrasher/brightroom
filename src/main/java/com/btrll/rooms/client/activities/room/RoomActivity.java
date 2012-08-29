package com.btrll.rooms.client.activities.room;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.model.Config;
import com.btrll.rooms.client.model.Office;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class RoomActivity extends DetailActivity {
	public interface View extends DetailActivity.View {
		public void setRoom(String svg);
	}

	static final Logger logger = Logger.getLogger("RoomActivity");
	private final ClientFactory clientFactory;
	private final RoomPlace place;

	public RoomActivity(ClientFactory clientFactory, RoomPlace place) {
		super(clientFactory.getRoomView(), "nav");
		this.clientFactory = clientFactory;
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		logger.fine("RoomActivity start");
		super.start(panel, eventBus);

		// Office office = getOffice(place.getResourceId());

		final View view = clientFactory.getRoomView();
		view.getHeader().setText("Room Name");
		view.getMainButtonText().setText("Nav");
		view.getBackbuttonText().setText("UI");

		panel.setWidget(view);
	}

	private static Office getOffice(long id) {
		return getConfig().getOffices().get((int) id - 1);
	}

	private static native Config getConfig() /*-{
		return $wnd.config;
	}-*/;

}
