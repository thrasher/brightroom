package com.btrll.rooms.client;

import com.btrll.rooms.client.model.CalendarListResource;
import com.btrll.rooms.client.model.Office;
import com.google.gwt.core.client.JsArray;

/**
 * This would be a RequestContext interface, if server-side data was used.
 * 
 * @author jthrasher
 */
public class ModelDao {

	public native JsArray<Office> getOffices() /*-{
		return $wnd.config.offices;
	}-*/;

	public Office getOfficeById(String id) {
		JsArray<Office> offices = getOffices();
		for (int i = 0; i < offices.length(); i++) {
			Office o = offices.get(i);
			if (o.getId().equals(id)) {
				return o;
			}
		}
		return null;
	}

	public Office getOfficeByName(String name) {
		JsArray<Office> offices = getOffices();
		for (int i = 0; i < offices.length(); i++) {
			Office o = offices.get(i);
			if (o.getName().equals(name)) {
				return o;
			}
		}
		return null;
	}

	public Office getOfficeByRoomId(String id) {
		CalendarListResource room = getRoomById(id);
		if (room == null)
			return null;
		JsArray<Office> offices = getOffices();
		for (int i = 0; i < offices.length(); i++) {
			Office o = offices.get(i);
			if (room.getSummary().startsWith(o.getSummaryPrefix())) {
				return o;
			}
		}
		return null;
	}

	public native JsArray<CalendarListResource> getRooms() /*-{
		return $wnd.config.rooms;
		F
	}-*/;

	public native JsArray<CalendarListResource> getRooms(Office office) /*-{
		var rooms = $wnd.config.rooms;
		var or = new Array();
		for ( var i = 0; i < rooms.length; i++) {
			if (rooms[i].summary.match(office.summaryPrefix)) {
				console.log(rooms[i].summary);
				or[or.length] = rooms[i];
			}
		}

		// sort it
		or.sort(function(a, b) {
			if (a.summary < b.summary)
				return -1;
			if (a.summary > b.summary)
				return 1;
			return 0;
		});

		console.log(or);

		return or;
	}-*/;

	public CalendarListResource getRoomById(String id) {
		JsArray<CalendarListResource> rooms = getRooms();
		for (int i = 0; i < rooms.length(); i++) {
			CalendarListResource r = rooms.get(i);
			if (r.getId().equals(id)) {
				return r;
			}
		}
		return null;
	}

	public CalendarListResource getRoomBySummary(String summary) {
		JsArray<CalendarListResource> list = getRooms();
		for (int i = 0; i < list.length(); i++) {
			CalendarListResource room = list.get(i);
			if (room.getSummary().equals(summary)) {
				return room;
			}
		}
		return null;
	}

}
