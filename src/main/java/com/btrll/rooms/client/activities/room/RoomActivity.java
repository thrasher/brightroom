package com.btrll.rooms.client.activities.room;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.Messages;
import com.btrll.rooms.client.model.CalendarListResource;
import com.btrll.rooms.client.util.Gapi;
import com.btrll.rooms.client.util.GapiResponseEvent;
import com.btrll.rooms.client.util.JSOModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Timer;
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

		public void setRoomName(SafeHtml name);
	}

	private static final Templates TEMPLATES = GWT.create(Templates.class);

	interface Templates extends SafeHtmlTemplates {
		// @Template("{0} in use by {1} for <a href=\"{2}\">{3}</a>")
		@Template("{0} in use by {1} for {3}")
		SafeHtml messageWithLink(String roomSummary, String organizer,
				String eventUrl, String eventSummary);
	}

	static final Logger logger = Logger.getLogger("RoomActivity");
	private final ClientFactory clientFactory;
	private final String roomId;
	private CalendarListResource room;
	private int checkRoomCallId;
	private int reserveCallId;

	public RoomActivity(ClientFactory clientFactory, RoomPlace place) {
		super(clientFactory.getRoomView(), "nav");
		this.clientFactory = clientFactory;
		this.roomId = place.getResourceId();
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		logger.fine("RoomActivity start");
		super.start(panel, eventBus);

		getView().getMainButtonText().setText(Messages.INSTANCE.nav());
		getView().getBackbuttonText().setText(Messages.INSTANCE.back());

		addHandlerRegistration(getView().getCheckButton().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						refreshRoom();
					}
				}));
		addHandlerRegistration(getView().getBookButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						reserveCallId = Gapi.getCallId();
						clientFactory.getGapi().reserveRoom(roomId, 15,
								reserveCallId);
					}
				}));
		addHandlerRegistration(eventBus.addHandler(GapiResponseEvent.getType(),
				new GapiResponseEvent.Handler() {

					@Override
					public void onGapiResponse(GapiResponseEvent event) {
						int callId = event.getCallId();
						if (callId == checkRoomCallId) {
							handleCheckRoom(event.getResp());
						} else if (callId == reserveCallId) {
							handleReserveResponse(event.getResp());
						}
					}
				}));

		panel.setWidget(getView());

		// this may become an async callback
		room = clientFactory.getModelDao().getRoomById(roomId);
		getView().getHeader().setText(room.getSummary());
		getView().setRoomName(
				new SafeHtmlBuilder().appendEscaped(room.getSummary())
						.toSafeHtml());

		refreshRoom();
	}

	private View getView() {
		return clientFactory.getRoomView();
	}

	private void refreshRoom() {
		getView().setCheck();

		checkRoomCallId = Gapi.getCallId();
		clientFactory.getGapi().checkRoom(room.getId(), checkRoomCallId);
	}

	private void handleReserveResponse(JSOModel resp) {
		if (isErrorResponse(resp)) {
			return;
		}

		// TODO: check that the roomId was actually added

		// verify that the resource's responseStatus needsAction
		// resp.attendees[0].email = the room id
		// resp.attendees[0].resource = true
		// resp.attendees[0].responseStatus = "needsAction"

		// if no action needed: success message and return

		// make poll request for resp.id (the CalendarEvent)

		// handle poll request for CalendarEvent

		// poll room, and inspect CalendarEvent object
		// resp.attendees[0].email = the room id
		// resp.attendees[0].resource = true
		// resp.attendees[0].responseStatus = "accepted"

		// if poll response is "accepted", then message success and return

		// if poll response is fail, then delete event resp.id, msg fail, return

		// if poll response is needsAction/pending/etc, then make poll request

		Dialogs.alert("Success!", room.getSummary() + "\nis a BrightRoom.",
				null);
		// TODO: poll until the resource is confirmed booked
		// delay the check query, so the data propagates
		Timer t = new Timer() {
			@Override
			public void run() {
				refreshRoom();
			}
		};
		t.schedule(500);
	}

	private void handleCheckRoom(JSOModel resp) {
		if (isErrorResponse(resp)) {
			return;
		}

		JSOModel event = clientFactory.getModelDao().findCurrentEvent(
				resp.getArray("items"));
		boolean isBusy = resp.getArray("items") != null && event != null;

		getView().setBusy(isBusy);
		if (isBusy) {
			getView().setRoomName(
					TEMPLATES.messageWithLink(room.getSummary(), event
							.getObject("organizer").get("email"), event
							.get("htmlLink"), event.get("summary"))

			// + " until "
			// + event.getObject("end").get("dateTime")
					);
		} else {
			getView().setRoomName(
					new SafeHtmlBuilder().appendEscaped(room.getSummary())
							.appendHtmlConstant(" is available").toSafeHtml());
		}
	}

	/**
	 * check if the response contains an error, and display appropriate message
	 * if it does.
	 */
	private boolean isErrorResponse(JSOModel resp) {
		if (resp.get("error", null) == null) {
			return false;
		}
		if (resp.getInt("code") == 404) {
			// in case of 404, user doesn't have access to the data, check
			// the account they are using
			Dialogs.alert(
					"Sorry",
					"You do not appear to have access to the resource.  Please check you're using the correct user account.",
					null);
		} else {
			Dialogs.alert("Sorry",
					resp.get("code") + ": " + resp.get("message"), null);
		}

		return true;
	}

}
