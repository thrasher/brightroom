package com.btrll.rooms.client.activities.gauth;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;

public class GauthEvent extends Event<GauthEvent.Handler> {

	public interface Handler {
		void onGauth(GauthEvent event);
	}

	private static final Type<GauthEvent.Handler> TYPE = new Type<GauthEvent.Handler>();
	private final boolean isAuthNeeded;

	public static void fire(EventBus eventBus, boolean isAuthNeeded) {
		eventBus.fireEvent(new GauthEvent(isAuthNeeded));
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	protected GauthEvent(boolean isAuthNeeded) {
		this.isAuthNeeded = isAuthNeeded;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onGauth(this);
	}

	public static Type<GauthEvent.Handler> getType() {
		return TYPE;
	}

	public boolean isAuthNeeded() {
		return isAuthNeeded;
	}

}
