package com.btrll.rooms.client.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.btrll.rooms.client.activities.gauth.GauthEvent;
import com.google.gwt.user.client.Window;
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

	private static int requestCount = 0;

	// TODO: add credentials here?
	public Gapi(EventBus eventBus) {
		this.eventBus = eventBus;

		exportStaticMethods(this);
	}

	/**
	 * Use this function to create call ID values based on current time.
	 * 
	 * @return
	 */
	public static int getCallId() {
		return requestCount++;
	}

	public native void reserveRoom(String roomId, int minutes, int callId) /*-{
		var x = this;
		var start = new Date();
		var end = new Date(start);
		end.setMinutes(start.getMinutes() + minutes);
		console.log("this is it: " + roomId);
		// NOTE: not documented, the resource is added as an attendee
		var resource = {
			"summary" : "BrightRoom",
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
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	public native void checkRoom(String roomId, int callId) /*-{
		var x = this;
		var d1 = new Date();
		var d2 = new Date(d1.getTime());
		d2.setHours(24, 0, 0, 0);
		var request = $wnd.gapi.client.calendar.events.list({
			'calendarId' : roomId,
			'orderBy' : 'startTime',
			'singleEvents' : true,
			'timeMax' : d2,
			'timeMin' : d1
		});
		request
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	/**
	 * Get all calendars.
	 * 
	 * @param callId
	 */
	public native void listCalendars(int callId) /*-{
		var x = this;
		var request = $wnd.gapi.client.calendar.calendarList.list({});
		request
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	private void handleCallback(JSOModel resp, String raw, int callId) {
		// NOTE: having UI in the API tier is bad bad bad
		if (resp.get("error", null) != null) {
			logger.warning(raw);
			Window.alert("Sorry " + resp.get("code") + ": "
					+ resp.get("message"));
			return;
		}

		if (logger.isLoggable(Level.FINE)) {
			resp.logToConsole();
		}
		GapiResponseEvent.fire(eventBus, resp, raw, callId);
	}

	// TODO: things below here are hacky, cleanup

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

	private native void exportStaticMethods(final Gapi x) /*-{
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
