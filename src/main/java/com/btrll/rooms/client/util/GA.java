package com.btrll.rooms.client.util;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;

public class GA {

	private final String trackingId;

	public GA(String trackingId) {
		this.trackingId = trackingId;
		setAccount(trackingId);
		inject();
	}

	private void inject() {
		String url = "https:" == Window.Location.getProtocol() ? "https://ssl"
				: "http://www" + ".google-analytics.com/ga.js";
		ScriptInjector.fromUrl(url)
				.setCallback(new Callback<Void, Exception>() {

					@Override
					public void onFailure(Exception reason) {
						// boo
					}

					@Override
					public void onSuccess(Void result) {
					}
				}).setWindow(ScriptInjector.TOP_WINDOW).inject();

	}

	private native void setAccount(String trackingId) /*-{
		$wnd._gaq = $wnd._gaq || [];
		$wnd._gaq.push([ '_setAccount', trackingId ]);
	}-*/;

	public native void setDisabled(boolean isDisabled) /*-{
		var token = 'ga-disable-'
				+ this.@com.btrll.rooms.client.util.GA::trackingId;
		$wnd[token] = isDisabled;
	}-*/;

	public native void trackEvent(String category, String action, String label) /*-{
		$wnd._gaq.push([ '_trackEvent', category, action, label ]);
	}-*/;

	public native void trackEvent(String category, String action, String label,
			int intArg) /*-{
		$wnd._gaq.push([ '_trackEvent', category, action, label, intArg ]);
	}-*/;

	public void trackPageview(Place place) {
		trackPageview(place.toString());
	}

	public native void trackPageview(String url) /*-{
		$wnd._gaq.push([ '_trackPageview', url ]);
	}-*/;
}
