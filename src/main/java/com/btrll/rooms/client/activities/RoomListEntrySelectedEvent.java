package com.btrll.rooms.client.activities;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class RoomListEntrySelectedEvent extends Event<RoomListEntrySelectedEvent.Handler> {

	public enum UIEntry {
		BUTTON_BAR, BUTTONS, ELEMENTS, POPUPS, PROGRESS_BAR, PROGRESS_INDICATOR, PULL_TO_REFRESH, SCROLL_WIDGET, SEARCH_BOX, SLIDER, TABBAR, FORMS, CAROUSEL, CAROUSEL_VERT, GROUP_LIST
	}

	public interface Handler {
		void onAnimationSelected(RoomListEntrySelectedEvent event);
	}

	private static final Type<RoomListEntrySelectedEvent.Handler> TYPE = new Type<RoomListEntrySelectedEvent.Handler>();
	private final UIEntry entry;

	public static void fire(EventBus eventBus, UIEntry entry) {
		eventBus.fireEvent(new RoomListEntrySelectedEvent(entry));
	}

	public static HandlerRegistration register(EventBus eventBus, Handler handler) {
		return eventBus.addHandler(TYPE, handler);
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	protected RoomListEntrySelectedEvent(UIEntry entry) {
		this.entry = entry;

	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onAnimationSelected(this);

	}

	public static Type<RoomListEntrySelectedEvent.Handler> getType() {
		return TYPE;
	}

	public UIEntry getEntry() {
		return entry;
	}
}
