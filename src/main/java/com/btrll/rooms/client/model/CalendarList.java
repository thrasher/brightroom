package com.btrll.rooms.client.model;

import com.btrll.rooms.client.util.JSOModel;
import com.google.gwt.core.client.JsArray;

/**
 * <a href=
 * "https://developers.google.com/google-apps/calendar/v3/reference/calendarList/list"
 * >CalendarList</a>
 * 
 * @author jthrasher
 * 
 */
public class CalendarList extends JSOModel {
	protected CalendarList() {
	}

	public native String getKind()/*-{
		return this.kind;
	}-*/;

	public native String getEtag()/*-{
		return this.etag;
	}-*/;

	public native String getNextPageToken()/*-{
		return this.nextPageToken;
	}-*/;

	public native JsArray<CalendarListResource> getItems()/*-{
		return this.items;
	}-*/;

}
