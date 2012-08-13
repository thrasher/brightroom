package com.btrll.rooms.client.util;

import java.util.logging.Logger;

import com.btrll.rooms.client.activities.gauth.GauthEvent;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.googlecode.mgwt.mvp.client.history.HistoryHandler;

/**
 * <a href="http://code.google.com/p/google-api-javascript-client/wiki/Samples">
 * samples</a>
 * 
 * @author jthrasher
 * 
 */
public class Gapi {
	private static final String URL = "https://apis.google.com/js/client.js";
	private String clientId = "706281234659.apps.googleusercontent.com";
	private String apiKey = "AIzaSyCs-pmSKuF0S9HPGzOVKLhFO9pzm4_Lp8Q";
	private String scopes = "https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/userinfo.email";

	static final Logger logger = Logger.getLogger("Gapi");

	private EventBus eventBus;

	public Gapi(EventBus eventBus) {
		exportStaticMethods(this);
		this.eventBus = eventBus;
	}

	public void load() {
		Timer t = new Timer() {
			@Override
			public void run() {
				init();
			}
		};
		t.schedule(1);
	}

	native void init() /*-{
		$wnd.__btrll_init();
	}-*/;

	public static native void handleAuthClick() /*-{
		$wnd.__btrll_handleAuthClick();
	}-*/;

	void doAuthRequired() {
		logger.fine("log auth required");
		GauthEvent.fire(eventBus, true);
	}

	void doAuthComplete() {
		logger.fine("log auth complete");
		GauthEvent.fire(eventBus, false);
	}

	native void exportStaticMethods(final Gapi x) /*-{
		//$wnd.__btrll_checkAuth = $entry(@com.btrll.rooms.client.util.Gapi::checkAuth());

		$wnd.__btrll_init = function() {
			if ($wnd.gapi === undefined || $wnd.gapi.client === undefined) {
				$wnd.alert('Unable to load Google API');
				return;
			}
			$wnd.gapi.client
					.setApiKey(x.@com.btrll.rooms.client.util.Gapi::apiKey);
			$wnd.setTimeout($wnd.__btrll_checkAuth, 1);
		}
		$wnd.__btrll_checkAuth = function() {
			$wnd.gapi.auth.authorize({
				client_id : x.@com.btrll.rooms.client.util.Gapi::clientId,
				scope : x.@com.btrll.rooms.client.util.Gapi::scopes,
				immediate : true
			}, $wnd.__btrll_handleAuthResult);
		}
		$wnd.__btrll_handleAuthClick = function(event) {
			$wnd.gapi.auth.authorize({
				client_id : x.@com.btrll.rooms.client.util.Gapi::clientId,
				scope : x.@com.btrll.rooms.client.util.Gapi::scopes,
				immediate : false
			}, $wnd.__btrll_handleAuthResult);
		}
		$wnd.__btrll_handleAuthResult = function(authResult) {
			//$wnd.alert('handleAuthResult');
			if (authResult && !authResult.error) {
				// Subtract five minutes from expires_in to ensure timely refresh
				var authTimeout = (authResult.expires_in - 5 * 60) * 1000;
				$wnd.setTimeout($wnd.__btrll_checkAuth, authTimeout);
				$wnd.gapi.client.load('calendar', 'v3');
				$wnd.gapi.client.load('oauth2', 'v1');
				//				$wnd.alert('Google Authorization Complete');
				$entry(x.@com.btrll.rooms.client.util.Gapi::doAuthComplete()());
			} else {
				// $wnd.alert('Google Authorization Required');
				// must be initiated in user thread
				//$wnd.__btrll_handleAuthClick();
				$entry(x.@com.btrll.rooms.client.util.Gapi::doAuthRequired()());
			}
		}
	}-*/;

}
