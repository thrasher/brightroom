package com.btrll.rooms.client.util;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Window;

public class Gapi {
	String URL = "https://apis.google.com/js/client.js";
	String clientId = "706281234659.apps.googleusercontent.com";
	String apiKey = "AIzaSyCs-pmSKuF0S9HPGzOVKLhFO9pzm4_Lp8Q";
	String scopes = "https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/userinfo.email";

	private void bind() {
		ScriptInjector.fromUrl(URL)
				.setCallback(new Callback<Void, Exception>() {
					public void onFailure(Exception reason) {
						Window.alert("Script load failed.");
					}

					public void onSuccess(Void result) {
						Window.alert("Script load success.");
					}
				}).inject();
	}

	private static native void init() /*-{
        wnd.gapi.client.setApiKey(apiKey);
        wnd.setTimeout(checkAuth, 1);
	}-*/;
}
