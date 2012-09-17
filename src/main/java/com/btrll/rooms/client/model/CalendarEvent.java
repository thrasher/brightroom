package com.btrll.rooms.client.model;

import com.btrll.rooms.client.util.JSOModel;
import com.google.gwt.core.client.JsArray;

/**
 * <pre>
 * Object
 * __gwt_ObjectId: undefined
 * attendees: Array[1]
 * created: "2012-09-16T23:49:14.000Z"
 * creator: Object
 * end: Object
 * etag: ""VPgt7A32MKJjP8Bq493zO-MXswA/Q0pESjhZdWRKeEVBQUFBQUFBQUFBQT09""
 * htmlLink: "https://www.google.com/calendar/event?eid=NjFlMXJ1MG52azk0ZGZwY2RvYTcyZThlMzAganRocmFzaGVyQGJyaWdodHJvbGwuY29t"
 * iCalUID: "61e1ru0nvk94dfpcdoa72e8e30@google.com"
 * id: "61e1ru0nvk94dfpcdoa72e8e30"
 * kind: "calendar#event"
 * organizer: Object
 * reminders: Object
 * result: Object
 * sequence: 0
 * start: Object
 * status: "confirmed"
 * summary: "BrightRoom"
 * updated: "2012-09-16T23:49:14.000Z"
 * __proto__: Object
 * </pre>
 * 
 * @author jthrasher
 * 
 */
public class CalendarEvent extends JSOModel {
	public static final String kind = "calendar#event";

	protected CalendarEvent() {
	}

	public final native JsArray<JSOModel> getAttendees()/*-{
		return this.attendees;
	}-*/;

	public final native String getCreated()/*-{
		return this.created;
	}-*/;

	public final native JSOModel getCreator()/*-{
		return this.creator;
	}-*/;

	public final native JSOModel getEnd()/*-{
		return this.end;
	}-*/;

	public final native String getEtag()/*-{
		return this.etag;
	}-*/;

	public final native String getHtmlLink()/*-{
		return this.htmlLink;
	}-*/;

	public final native String getICalUID()/*-{
		return this.iCalUID;
	}-*/;

	public final native String getId()/*-{
		return this.id;
	}-*/;

	public final native String getKind()/*-{
		return this.kind;
	}-*/;

	public final native JSOModel getOrganizer()/*-{
		return this.organizer;
	}-*/;

	public final native JSOModel getReminders()/*-{
		return this.reminders;
	}-*/;

	public final native JSOModel getResult()/*-{
		return this.result;
	}-*/;

	public final native int getSequence()/*-{
		return this.sequence;
	}-*/;

	public final native JSOModel getStart()/*-{
		return this.start;
	}-*/;

	public final native String getStatus()/*-{
		return this.status;
	}-*/;

	public final native String getSummary()/*-{
		return this.summary;
	}-*/;

	public final native String getUpdated()/*-{
		return this.updated;
	}-*/;

}
