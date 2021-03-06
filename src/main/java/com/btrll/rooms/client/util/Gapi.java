package com.btrll.rooms.client.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.btrll.rooms.client.activities.gauth.GauthEvent;
import com.btrll.rooms.client.model.CalendarListResource;
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

	private static int requestCount = 0;

	// TODO: add credentials here?
	public Gapi(EventBus eventBus) {
		this.eventBus = eventBus;

		exportStaticMethods(this);
	}

	/**
	 * Create call ID values to identify which async response on the event bus
	 * is appropriate to use.
	 * 
	 * @return
	 */
	public static int getCallId() {
		return requestCount++;
	}

	/**
	 * Request the email address of the authorized user.
	 * 
	 * @param callId
	 */
	private native void oauth2UserInfoGet(int callId) /*-{
		var request = $wnd.gapi.client.oauth2.userinfo.get({
			'fields' : 'email'
		});

		request
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	private native void usersMeCalendarList(int callId) /*-{
		$wnd.gapi.client
				.request({
					'path' : '/calendar/v3/users/me/calendarList',
					'method' : 'POST',
					'headers' : {
						'Content-Type' : 'application/json' // This is the default
					},
					'body' : JSON.stringify({
						'id' : 'jthrasher@brightroll.com',
						'selected' : true
					}),
					'callback' : function(resp, raw) {
						$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
					}
				});
	}-*/;

	public native void batchCalEventsList(int callId,
			JsArray<CalendarListResource> a) /*-{
		var x = this;
		var rpcBatch = $wnd.gapi.client.newRpcBatch();
		for ( var i = 0; i < a.length; ++i) {
			var id = a[i].id;
			// for each room
			var d1 = new Date();
			var d2 = new Date(d1.getTime());
			d2.setHours(24, 0, 0, 0);
			var req = $wnd.gapi.client.calendar.events.list({
				'calendarId' : id,
				'orderBy' : 'startTime',
				'singleEvents' : true,
				'timeMax' : d2,
				'timeMin' : d1
			});
			// use the id to find the batch response object
			rpcBatch.add(req, {
				'id' : id
			});
		}
		rpcBatch
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});

	}-*/;

	public native void calEventsGet(String calendarId, String eventId,
			int callId) /*-{
		var x = this;
		var request = $wnd.gapi.client.calendar.events.get({
			'eventId' : eventId,
			'calendarId' : calendarId == null ? 'primary' : calendarId
		});
		request
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	public native void calEventsDelete(String calendarId, String eventId,
			int callId) /*-{
		var x = this;
		var request = $wnd.gapi.client.calendar.events['delete']({
			'eventId' : eventId,
			'sendNotifications' : false,
			'calendarId' : calendarId == null ? 'primary' : calendarId
		});
		request
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	/**
	 * Get a list of all event for the given calendar.
	 * 
	 * @param calendarId
	 *            or null for 'primary' per the authorized user
	 * @param callId
	 */
	private native void calListEvents(String calendarId, int callId) /*-{
		var request = $wnd.gapi.client.calendar.events.list({
			'calendarId' : calendarId == null ? 'primary' : calendarId
		});
		request
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	public void calEventsInsertPrimary(String roomId, int minutes, int callId) {
		calEventsInsert("primary", roomId, minutes, callId);
	}

	public native void calEventsInsert(String calendarId, String roomId,
			int minutes, int callId) /*-{
		var x = this;
		var start = new Date();
		var end = new Date(start);
		end.setMinutes(start.getMinutes() + minutes);
		console.log("this is it: " + roomId);
		// NOTE: not documented by google, the resource is added as an attendee
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
			'resource' : resource,
			'calendarId' : calendarId == null ? 'primary' : calendarId
		});
		request
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	public native void calEventsList(String roomId, int callId) /*-{
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
	public native void calCalendarListList(int callId) /*-{
		var x = this;
		var request = $wnd.gapi.client.calendar.calendarList.list({});
		request
				.execute(function(resp, raw) {
					$entry(x.@com.btrll.rooms.client.util.Gapi::handleCallback(Lcom/btrll/rooms/client/util/JSOModel;Ljava/lang/String;I)(resp, raw, callId));
				});
	}-*/;

	private void handleCallback(JSOModel resp, String raw, int callId) {
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
