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
						// handle success
						int callId = event.getCallId();
						if (callId == checkRoomCallId) {
							handleCheckRoom(event.getResp());
						} else if (callId == reserveCallId) {
							// TODO: check that the roomId was actually added
							Dialogs.alert("Success!", room.getSummary()
									+ "\nis a BrightRoom.", null);
							refreshRoom();
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
}
