package com.btrll.rooms.client.activities.room;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.model.CalendarListResource;
import com.btrll.rooms.client.util.JSOModel;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.Button;

public class RoomActivity extends DetailActivity {
	public interface View extends DetailActivity.View {
		public Button getCheckButton();

		public Button getBookButton();

		public void setCheck();

		public void setBusy(boolean isBusy);

		public void setRoomName(String name);
	}

	static final Logger logger = Logger.getLogger("RoomActivity");
	private final ClientFactory clientFactory;
	private final String roomId;
	private CalendarListResource room;

	public RoomActivity(ClientFactory clientFactory, RoomPlace place) {
		super(clientFactory.getRoomView(), "nav");
		this.clientFactory = clientFactory;
		this.roomId = place.getResourceId();
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		logger.fine("RoomActivity start");
		super.start(panel, eventBus);

		getView().getMainButtonText().setText("Nav");
		getView().getBackbuttonText().setText("UI");

		addHandlerRegistration(getView().getCheckButton().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						refreshRoom();
					}
				}));
		this.addHandlerRegistration(getView().getBookButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						quickAdd(roomId, "BrightRoom");
					}
				}));

		panel.setWidget(getView());

		refreshRoom();
	}

	private View getView() {
		return clientFactory.getRoomView();
	}

	private void refreshRoom() {
		getView().setCheck();
		CalendarListResource room = clientFactory.getModelDao().getRoomById(
				roomId);

		setRoom(room);

		// doListTodaysMeetings(roomId);
		checkRoom(roomId);
	}

	private void setRoom(CalendarListResource room) {
		this.room = room;
		getView().getHeader().setText(room.getSummary());
		getView().setRoomName(room.getSummary());
		// eventBus.fireEventFromSource(new RoomListUpdateEvent(roomList),
		// this);
	}

	private native void checkRoom(String roomId) /*-{
		var x = this;
		var d1 = new Date();
		//		d1.setHours(0, 0, 0, 0);
		var d2 = new Date(d1.getTime());
		d2.setHours(24, 0, 0, 0);
		// alert(d1 + "\n" + d2);
		$wnd.gapi.client.calendar.events
				.list({
					'calendarId' : roomId,
					'orderBy' : 'startTime',
					'singleEvents' : true,
					'timeMax' : d2,
					'timeMin' : d1
				})
				.execute(
						function(resp, raw) {
							x.@com.btrll.rooms.client.activities.room.RoomActivity::handleCheckRoom(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;)(resp, raw);
						});
	}-*/;

	private void handleCheckRoom(JSOModel resp, String raw) {
		if (logger.isLoggable(Level.FINE)) {
			resp.logToConsole();
		}
		if (resp.get("error", null) != null) {
			logger.warning(raw);
			Window.alert("Sorry " + resp.get("code") + ": "
					+ resp.get("message"));
			return;
		}

		JSOModel event = findCurrentEvent(resp.getArray("items"));
		boolean isBusy = resp.getArray("items") != null && event != null;

		getView().setBusy(isBusy);
		if (isBusy) {
			// htmlsafe?
			getView().setRoomName(
					room.getSummary() + " in use by "
							+ event.getObject("organizer").get("email")
							+ " for '" + event.get("summary") + "'"
			// + " until "
			// + event.getObject("end").get("dateTime")
					);
		} else {
			getView().setRoomName(room.getSummary() + " is available");
		}
	}

	private JSOModel findCurrentEvent(JsArray<JSOModel> items) {
		for (int i = 0; i < items.length(); i++) {
			JSOModel event = items.get(i);
			if (happeningNow(event)) {
				return event;
			}
		}
		return null;
	}

	private boolean happeningNow(JSOModel event) {
		// sample: 2012-09-02T23:00:00-07:00
		// DateTimeFormat format = DateTimeFormat
		// .getFormat(DateTimeFormat.PredefinedFormat.ISO_8601);
		DateTimeFormat format = DateTimeFormat
				.getFormat("yyyy-MM-ddTHH:mm:ssZ");

		String startTimeS = event.getObject("start").get("dateTime");
		Date startTime = format.parse(startTimeS);
		String endTimeS = event.getObject("end").get("dateTime");
		Date endTime = format.parse(endTimeS);
		Date now = new Date();

		return startTime.getTime() < now.getTime()
				&& now.getTime() < endTime.getTime();
	}

	private native void quickAdd(String roomId, String text) /*-{
		var x = this;
		var start = new Date();
		var end = new Date(start);
		end.setMinutes(start.getMinutes() + 15);
		console.log("this is it: " + roomId);
		// NOTE: not documented, the resource is added as an attendee
		var resource = {
			"summary" : text,
			"start" : {
				"dateTime" : start
			},
			"end" : {
				"dateTime" : end
			},
			"attendees" : [ {
				"email" : roomId
			} ]
		};
		var request = $wnd.gapi.client.calendar.events.insert({
			'calendarId' : 'primary',
			'resource' : resource
		});
		request
				.execute(function(resp, raw) {
					x.@com.btrll.rooms.client.activities.room.RoomActivity::handleQuickAdd(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;)(resp, raw);
				});
	}-*/;

	private void handleQuickAdd(JSOModel resp, String raw) {
		if (logger.isLoggable(Level.FINE)) {
			resp.logToConsole();
		}
		if (resp.get("error", null) != null) {
			logger.warning(raw);
			Window.alert("Sorry " + resp.get("code") + ": "
					+ resp.get("message"));
			return;
		}
		Dialogs.alert("Success!", room.getSummary() + "\nis a BrightRoom.",
				null);
		refreshRoom();
	}
}
