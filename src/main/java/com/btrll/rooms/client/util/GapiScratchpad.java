package com.btrll.rooms.client.util;

import com.btrll.rooms.client.ModelDao;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Random functions testing the gapi.
 */
public class GapiScratchpad {
	int doListCalendarsId;
	int doBatch;

	public void doBatch(Gapi gapi, final EventBus eventBus) {
		// NOTE: handler is not de-registered
		eventBus.addHandler(GapiResponseEvent.getType(),
				new GapiResponseEvent.Handler() {

					@Override
					public void onGapiResponse(GapiResponseEvent event) {
						// handle success
						int callId = event.getCallId();
						if (callId == doBatch) {
							Window.alert("check for doBatch json in console "
									+ doBatch);
						}
					}
				});
		ModelDao dao = new ModelDao();

		gapi.batchCalEventsList(doBatch = Gapi.getCallId(),
				dao.getRooms(dao.getOfficeById("1")));
	}

	public void doListCalendars(Gapi gapi, final EventBus eventBus) {
		// NOTE: handler is not de-registered
		eventBus.addHandler(GapiResponseEvent.getType(),
				new GapiResponseEvent.Handler() {

					@Override
					public void onGapiResponse(GapiResponseEvent event) {
						// handle success
						int callId = event.getCallId();
						if (callId == doListCalendarsId) {
							handleListCalendars(event.getResp(), event.getRaw());
							Window.alert("check for calendar list json in console");
						}
					}
				});
		gapi.calCalendarListList(doListCalendarsId = Gapi.getCallId());
	}

	private void handleListCalendars(JSOModel resp, String raw) {
		JsArray<JSOModel> entries = JSOModel.arrayFromJson("[]");

		JsArray<JSOModel> a = resp.getArray("items");
		for (int i = 0; i < a.length(); i++) {
			// TODO: build this regex from config.jsonp data
			if (a.get(i).get("summary").matches("SF.*|NYC.*|CHI.*|Palo Alto")) {
				entries.push(a.get(i));
			}
		}
		sortCalendarListEntry(entries);
		resp.setArray("items", entries);
		resp.setArray("result", null); // handy, but not needed
		resp.logToConsole();
	}

	private native void sortCalendarListEntry(JavaScriptObject items) /*-{
		items.sort(function(a, b) {
			return (a.summary < b.summary) ? -1 : 1;
		});
	}-*/;
}
