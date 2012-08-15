package com.btrll.rooms.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class JsonRequest {
	private final AsyncCallback<JSOModel> callback;

	public JsonRequest(final AsyncCallback<JSOModel> callback) {
		this.callback = callback;
	}

	/**
	 * 
	 * @param relativePath
	 *            relative to "http://host/basepage.html"
	 * @param callback
	 */
	public void send(String relativePath) {
		String url = GWT.getHostPageBaseURL() + relativePath;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				URL.encode(url));
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable caught) {
					callback.onFailure(caught);
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					if (Response.SC_OK == response.getStatusCode()) {
						try {
							callback.onSuccess(JSOModel.fromJson(response
									.getText()));
						} catch (IllegalArgumentException iax) {
							callback.onFailure(iax);
						}
					} else {
						// Better to use a typed exception here to indicate the
						// specific
						// cause of the failure.
						callback.onFailure(new Exception("Bad return code: "
								+ response.getStatusCode()));
					}
				}
			});
		} catch (RequestException e) {
			callback.onFailure(e);
		}
	}
}
