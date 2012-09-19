package com.btrll.rooms.client.activities.room;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.Messages;
import com.btrll.rooms.client.model.Attendee;
import com.btrll.rooms.client.model.CalendarEvent;
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

	private static final Logger logger = Logger.getLogger("RoomActivity");
	private final ClientFactory clientFactory;
	private final String roomId;
	private CalendarListResource room;
	private int checkRoomCallId;
	private int reserveCallId;
	private int calEventsGet;
	private int deleteCallId;

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
						getView().setCheck();
						reserveCallId = Gapi.getCallId();
						clientFactory.getGapi().calEventsInsertPrimary(roomId,
								15, reserveCallId);
					}
				}));
		addHandlerRegistration(eventBus.addHandler(GapiResponseEvent.getType(),
				new GapiResponseEvent.Handler() {

					@Override
					public void onGapiResponse(GapiResponseEvent event) {
						int callId = event.getCallId();
						if (isErrorResponse(event.getResp())) {
							return;
						} else if (callId == checkRoomCallId) {
							handleCheckRoom(event.getResp());
						} else if (callId == reserveCallId) {
							handleCalEventsGet((CalendarEvent) event.getResp());
						} else if (callId == calEventsGet) {
							handleCalEventsGet((CalendarEvent) event.getResp());
						} else if (callId == deleteCallId) {
							handleDelete(event.getResp());
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
		clientFactory.getGA().trackPageview(new RoomPlace(roomId));
	}

	private View getView() {
		return clientFactory.getRoomView();
	}

	private void refreshRoom() {
		getView().setCheck();
		checkRoomCallId = Gapi.getCallId();
		clientFactory.getGapi().calEventsList(room.getId(), checkRoomCallId);
	}

	private void handleDelete(JSOModel resp) {
		// delete object has no data.
		Timer t = new Timer() {
			@Override
			public void run() {
				refreshRoom();
			}
		};
		t.schedule(1000); // good enough
	}

	private void handleCalEventsGet(final CalendarEvent resp) {
		for (int i = 0; i < resp.getAttendees().length(); i++) {
			Attendee attendee = (Attendee) resp.getAttendees().get(i);
			logger.fine(attendee.getDisplayName() + ": "
					+ attendee.getResponseStatus());
			if (roomId.equals(attendee.getEmail())) {
				if (Attendee.ACCEPTED.equals(attendee.getResponseStatus())) {
					Dialogs.alert(
							"Success!", // room.getSummary()
							attendee.getDisplayName() + " is a BrightRoom.",
							null);
					refreshRoom();
				} else if (Attendee.DECLINED.equals(attendee
						.getResponseStatus())) {
					// delete event so we don't pollute calendar, notify user
					Dialogs.alert(
							"Conflict!", // room.getSummary()
							attendee.getDisplayName()
									+ " is not reservable, as it overlaps with an existing event.",
							null);
					deleteCallId = Gapi.getCallId();
					clientFactory.getGapi().calEventsDelete(null, resp.getId(),
							deleteCallId);
				} else if (Attendee.NEEDSACTION.equals(attendee
						.getResponseStatus())) {
					// poll
					Timer t = new Timer() {
						@Override
						public void run() {
							pollEvent(resp.getId());
						}
					};
					// TODO: exponential back-off
					t.schedule(200);
				} else {
					// unhandled response status, alert user
					Dialogs.alert(
							"That's weird!", // room.getSummary()
							attendee.getDisplayName() + " is "
									+ attendee.getResponseStatus()
									+ ".  Please let Jason T. know.", null);
					refreshRoom();
				}
			}
			return;
		}
		// shouldn't get here
		logger.warning("expected to find room resource, but it was missing.");
		Dialogs.alert("That's weird!", room.getSummary() + " is not found."
				+ "  Please let Jason T. know.", null);
	}

	private void pollEvent(String eventId) {
		calEventsGet = Gapi.getCallId();
		clientFactory.getGapi().calEventsGet(null, eventId, calEventsGet);
	}

	private void handleCheckRoom(JSOModel resp) {
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
