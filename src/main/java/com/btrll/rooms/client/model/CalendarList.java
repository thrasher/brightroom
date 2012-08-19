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

	public final native String getKind()/*-{
		return this.kind;
	}-*/;

	public final native String getEtag()/*-{
		return this.etag;
	}-*/;

	public final native String getNextPageToken()/*-{
		return this.nextPageToken;
	}-*/;

	public final native JsArray<CalendarListResource> getItems()/*-{
		return this.items;
	}-*/;

}
