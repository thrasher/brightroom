package com.btrll.rooms.client.util;

/**
 * Wow this JSNI stuff is verbose, you may want to use the JSON object directly
 * from JavaScript instead.
 * 
 * <a href="http://dev.w3.org/geo/api/spec-source.html#position_interface">
 * Position</a>
 * 
 * @author jthrasher
 * 
 */
public final class Position extends JSOModel {

	protected Position() {
	}

	public native double getTimestamp()/*-{
		return this.timestamp;
	}-*/;

	public native Coords getCoords()/*-{
		return this.coords;
	}-*/;

	public static final class Coords extends JSOModel {
		protected Coords() {
		}

		public native double getLatitude()/*-{
			return this.latitude;
		}-*/;

		public native double getLongitude()/*-{
			return this.longitude;
		}-*/;

		public native double getAccuracy()/*-{
			return this.accuracy;
		}-*/;

		public native double getAltitudeAccuracy()/*-{
			return this.altitudeAccuracy;
		}-*/;

		public native double getAltitude()/*-{
			return this.altitude;
		}-*/;

		public native double getSpeed()/*-{
			return this.speed;
		}-*/;

		public native double getHeading()/*-{
			return this.heading;
		}-*/;

	}
}