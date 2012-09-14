package com.btrll.rooms.client.model;

import com.btrll.rooms.client.util.JSOModel;

/**
 * <a href=
 * "https://developers.google.com/google-apps/calendar/v3/reference/calendarList#resource"
 * >CalendarListResource</a>
 * 
 * @author jthrasher
 * 
 */
public class CalendarListResource extends JSOModel {

	protected CalendarListResource() {
	}

	public final native String getKind()/*-{
		return this.kind;
	}-*/;

	public final native String getId()/*-{
		return this.id;
	}-*/;

	public final native String getSummary()/*-{
		return this.summary;
	}-*/;

	public final native String getLocation()/*-{
		return this.location;
	}-*/;

	public final native String getTimeZone()/*-{
		return this.timeZone;
	}-*/;

	public final native String getD3id()/*-{
		return this.d3id;
	}-*/;

}
