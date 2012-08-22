package com.btrll.rooms.client.util;

import java.util.logging.Logger;

import com.btrll.rooms.client.activities.gauth.GauthEvent;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.web.bindery.event.shared.EventBus;

/**
 * <a href="http://code.google.com/p/google-api-javascript-client/wiki/Samples">
 * samples</a>
 * 
 * @author jthrasher
 * 
 */
public class Gapi {
	// https://apis.google.com/js/client.js?onload=OnLoadCallback
	static final Logger logger = Logger.getLogger("Gapi");

	private EventBus eventBus;

	private boolean isOauth2Loaded = false;
	private boolean isCalendarLoaded = false;

	public Gapi(EventBus eventBus) {
		this.eventBus = eventBus;

		exportStaticMethods(this);
	}

	/**
	 * print out json to console for all valid conference rooms based on regex.
	 */
	public void getConferenceRooms() {
		getConferenceRooms(this);
	}

	private native void getConferenceRooms(final Gapi x) /*-{
		$wnd.gapi.client.calendar.calendarList
				.list({})
				.execute(
						function(resp, raw) {
							//$wnd.console.log(raw);
							$entry(x.@com.btrll.rooms.client.util.Gapi::getConferenceRooms(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;)(resp, raw));
						});
	}-*/;

	private void getConferenceRooms(JSOModel resp, String raw) {

		// logger.fine(raw);
		// logger.fine(resp.toJson());
		// logger.fine("calendar count " + resp.getArray("items").length());

		JsArray<JSOModel> entries = JSOModel.arrayFromJson("[]");

		JsArray<JSOModel> a = resp.getArray("items");
		for (int i = 0; i < a.length(); i++) {
			if (a.get(i).get("summary").matches("SF.*|NYC.*|CHI.*|Palo Alto")) {
				// logger.fine(i + " = " + a.get(i).get("summary"));
				entries.push(a.get(i));
			}
		}
		sortCalendarListEntry(entries);
		resp.setArray("items", entries);
		// this element is not in the docs
		// https://developers.google.com/google-apps/calendar/v3/reference/calendarList/list
		resp.setArray("result", null);
		logger.fine(resp.toJson());

	}

	private native void sortCalendarListEntry(JavaScriptObject item) /*-{
		item.sort(function(a, b) {
			return (a.summary < b.summary) ? -1 : 1;
		});
	}-*/;

	public native void handleAuthClick() /*-{
		$wnd.handleAuthClick();
	}-*/;

	private void doAuthRequired() {
		logger.fine("log auth required");
		GauthEvent.fire(eventBus, true);
	}

	private void doAuthComplete() {
		if (isCalendarLoaded && isOauth2Loaded) {
			logger.fine("log auth complete");
			GauthEvent.fire(eventBus, false);
		}
	}

	private void notifyCalendarLoaded() {
		logger.fine("notifyCalendarLoaded");
		isCalendarLoaded = true;
		doAuthComplete();
	}

	private void notifyOauth2Loaded() {
		logger.fine("notifyOauth2Loaded");
		isOauth2Loaded = true;
		doAuthComplete();
	}

	native void exportStaticMethods(final Gapi x) /*-{
		$wnd.console.log('exportStaticMethods');
		$wnd.__btrll_handleAuthResult = function(authResult) {
			//$wnd.alert('__btrll_handleAuthResult');
			if (authResult && !authResult.error) {
				// Subtract five minutes from expires_in to ensure timely refresh
				var authTimeout = (authResult.expires_in - 5 * 60) * 1000;
				$wnd.setTimeout($wnd.checkAuth, authTimeout);
				$wnd.gapi.client
						.load(
								'calendar',
								'v3',
								function() {
									$entry(x.@com.btrll.rooms.client.util.Gapi::notifyCalendarLoaded()());
								});
				$wnd.gapi.client
						.load(
								'oauth2',
								'v1',
								function() {
									$entry(x.@com.btrll.rooms.client.util.Gapi::notifyOauth2Loaded()());
								});
				// $wnd.alert('Google Authorization Complete');
				//$entry(x.@com.btrll.rooms.client.util.Gapi::doAuthComplete()());
			} else {
				// $wnd.alert('Google Authorization Required');
				// must be initiated in user thread
				//$wnd.handleAuthClick();
				$entry(x.@com.btrll.rooms.client.util.Gapi::doAuthRequired()());
			}
		}
		if ($wnd.savedAuthResult != null) {
			$wnd.__btrll_handleAuthResult($wnd.savedAuthResult);
		}
		$wnd.gapi_onload();
	}-*/;

}
