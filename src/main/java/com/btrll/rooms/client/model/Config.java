package com.btrll.rooms.client.model;

import com.btrll.rooms.client.util.JSOModel;
import com.google.gwt.core.client.JsArray;

public class Config extends JSOModel {

	protected Config() {
	}

	public final native JsArray<Office> getOffices()/*-{
		return this.offices;
	}-*/;

	public final native JsArray<CalendarListResource> getRooms()/*-{
		return this.rooms;
	}-*/;

}
