package com.btrll.rooms.client.model;

import com.btrll.rooms.client.util.JSOModel;

public class Office extends JSOModel {

	protected Office() {
	}

	public final native String getId()/*-{
		return this.id;
	}-*/;

	public final native String getName()/*-{
		return this.name;
	}-*/;

	public final native String getSummaryPrefix()/*-{
		return this.summaryPrefix;
	}-*/;

	public final native String getMap()/*-{
		return this.map;
	}-*/;

	public final native String getLatitude()/*-{
		return this.latitude;
	}-*/;

	public final native String getLongitude()/*-{
		return this.longitude;
	}-*/;

	public final native String getVideo()/*-{
		return this.video;
	}-*/;

}
