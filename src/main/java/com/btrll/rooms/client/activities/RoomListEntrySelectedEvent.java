package com.btrll.rooms.client.activities;

import com.btrll.rooms.client.model.CalendarListResource;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class RoomListEntrySelectedEvent extends
		Event<RoomListEntrySelectedEvent.Handler> {

	public interface Handler {
		void onRoomSelected(RoomListEntrySelectedEvent event);
	}

	private static final Type<RoomListEntrySelectedEvent.Handler> TYPE = new Type<RoomListEntrySelectedEvent.Handler>();
	private final CalendarListResource resource;

	public static void fire(EventBus eventBus, CalendarListResource resource) {
		eventBus.fireEvent(new RoomListEntrySelectedEvent(resource));
	}

	public static HandlerRegistration register(EventBus eventBus,
			Handler handler) {
		return eventBus.addHandler(TYPE, handler);
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	protected RoomListEntrySelectedEvent(CalendarListResource resource) {
		this.resource = resource;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onRoomSelected(this);
	}

	public static Type<RoomListEntrySelectedEvent.Handler> getType() {
		return TYPE;
	}

	public CalendarListResource getResourceId() {
		return resource;
	}

}
