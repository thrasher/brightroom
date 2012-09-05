package com.btrll.rooms.client.util;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class GapiResponseEvent extends Event<GapiResponseEvent.Handler> {

	public interface Handler {
		void onGapiResponse(GapiResponseEvent event);
	}

	private static final Type<GapiResponseEvent.Handler> TYPE = new Type<GapiResponseEvent.Handler>();
	private final JSOModel resp;
	private final String raw;
	private final int callId; // marker so we know who made the request

	public static void fire(EventBus eventBus, JSOModel resp, String raw,
			int callId) {
		eventBus.fireEvent(new GapiResponseEvent(resp, raw, callId));
	}

	public static HandlerRegistration register(EventBus eventBus,
			Handler handler) {
		return eventBus.addHandler(TYPE, handler);
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	protected GapiResponseEvent(JSOModel resp, String raw, int callId) {
		this.resp = resp;
		this.raw = raw;
		this.callId = callId;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onGapiResponse(this);
	}

	public static Type<GapiResponseEvent.Handler> getType() {
		return TYPE;
	}

	public JSOModel getResp() {
		return resp;
	}

	public String getRaw() {
		return raw;
	}

	public int getCallId() {
		return callId;
	}

}
