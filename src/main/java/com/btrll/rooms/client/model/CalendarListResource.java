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

	public native String getKind()/*-{
		return this.kind;
	}-*/;

	public native String getId()/*-{
		return this.id;
	}-*/;

	public native String getSummary()/*-{
		return this.summary;
	}-*/;

	public native String getLocation()/*-{
		return this.location;
	}-*/;

	public native String getTimeZone()/*-{
		return this.timeZone;
	}-*/;

}
