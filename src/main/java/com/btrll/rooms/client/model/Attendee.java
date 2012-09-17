package com.btrll.rooms.client.model;

import com.btrll.rooms.client.util.JSOModel;

/**
 * <pre>
 * displayName: "SF: Castro (4)"
 * email: "brightroll.com_34313939373537382d343838@resource.calendar.google.com"
 * resource: true
 * responseStatus: "accepted"
 * self: true
 * </pre>
 * 
 * @author jthrasher
 * 
 */
public class Attendee extends JSOModel {
	public static final String ACCEPTED = "accepted";
	public static final String NEEDSACTION = "needsAction";
	public static final String DECLINED = "declined";

	protected Attendee() {
	}

	public final native String getDisplayName()/*-{
		return this.displayName;
	}-*/;

	public final native String getEmail()/*-{
		return this.email;
	}-*/;

	public final native boolean getResource()/*-{
		return this.resource;
	}-*/;

	public final native String getResponseStatus()/*-{
		return this.responseStatus;
	}-*/;

	public final native boolean getSelf()/*-{
		return this.self;
	}-*/;

}
