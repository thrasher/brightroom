package com.btrll.rooms.client.activities.room;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.model.CalendarListResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class RoomActivity extends DetailActivity {
	public interface View extends DetailActivity.View {
		public void setRoom(String svg);
	}

	static final Logger logger = Logger.getLogger("RoomActivity");
	private final ClientFactory clientFactory;
	private final String roomId;

	public RoomActivity(ClientFactory clientFactory, RoomPlace place) {
		super(clientFactory.getRoomView(), "nav");
		this.clientFactory = clientFactory;
		this.roomId = place.getResourceId();
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		logger.fine("RoomActivity start");
		super.start(panel, eventBus);

		final View view = clientFactory.getRoomView();
		view.getMainButtonText().setText("Nav");
		view.getBackbuttonText().setText("UI");

		panel.setWidget(view);

		refreshRoom();
	}

	private void refreshRoom() {
		CalendarListResource room = clientFactory.getModelDao().getRoomById(
				roomId);

		setRoom(room);

		doListTodaysMeetings(roomId);
	}

	private void setRoom(CalendarListResource room) {
		final View view = clientFactory.getRoomView();
		view.getHeader().setText(room.getSummary());
		// eventBus.fireEventFromSource(new RoomListUpdateEvent(roomList),
		// this);
	}

	/**
	 * Fetch the list of events in the given room today.
	 * 
	 * @param roomId
	 */
	private native void doListTodaysMeetings(String roomId) /*-{
		var is_between = function(object, before, after) {
			return object > before && object < after
		};

		var currently_happening = function(event) {
			var event_start = new Date(event.start.dateTime);
			var event_end = new Date(event.end.dateTime);
			var now = new Date();
			return is_between(now, event_start, event_end);
		};

		var busy = function(calendar) {
			if (calendar.result.items === undefined) {
				return false;
			}
			for ( var i = 0; i < calendar.result.items.length; i++) {
				if (currently_happening(calendar.result.items[i]))
					return true;
			}

			return false;
		};

		var update_page_availability = function(calendar, description_id,
				image_id) {
			description = $doc.getElementById(description_id);
			image = $doc.getElementById(image_id);
			if (busy(calendar)) {
				description.textContent = "Busy";
				image.setAttribute("src", "images/busy.png");
			} else {
				description.textContent = "Available";
				image.setAttribute("src", "images/available.png");
			}
		}

		var d1 = new Date();
		d1.setHours(0, 0, 0, 0);
		var d2 = new Date(d1.getTime());
		d2.setHours(24, 0, 0, 0);
		// alert(d1 + "\n" + d2);
		$wnd.gapi.client.calendar.events.list({
			'calendarId' : roomId,
			'orderBy' : 'startTime',
			'singleEvents' : true,
			'timeMax' : d2,
			'timeMin' : d1
		}).execute(
				function(resp, raw) {
					if (resp.error) {
						console.log("error: " + resp.message);
						$wnd.alert("Sorry: " + resp.message);
						//												update_page_availability($wnd.mission,
						//														'availability-description',
						//														'availability-image');

						return;
					}
					console.log(resp);
					//					$wnd.alert(raw);
					update_page_availability(resp, 'availability-description',
							'availability-image');

				});
	}-*/;

}
