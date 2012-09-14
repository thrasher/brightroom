package com.btrll.rooms.client;

import java.util.Date;

import com.btrll.rooms.client.model.CalendarListResource;
import com.btrll.rooms.client.model.Office;
import com.btrll.rooms.client.util.JSOModel;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;

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
				//console.log(rooms[i].summary);
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

		//console.log(or);

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

	public CalendarListResource getRoomByD3id(String id) {
		JsArray<CalendarListResource> rooms = getRooms();
		for (int i = 0; i < rooms.length(); i++) {
			CalendarListResource r = rooms.get(i);
			if (r.getD3id().equals(id)) {
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

	public JSOModel findCurrentEvent(JsArray<JSOModel> items) {
		for (int i = 0; i < items.length(); i++) {
			JSOModel event = items.get(i);
			if (happeningNow(event)) {
				return event;
			}
		}
		return null;
	}

	private boolean happeningNow(JSOModel event) {
		// sample: 2012-09-02T23:00:00-07:00
		// DateTimeFormat format = DateTimeFormat
		// .getFormat(DateTimeFormat.PredefinedFormat.ISO_8601); // broke
		DateTimeFormat format = DateTimeFormat
				.getFormat("yyyy-MM-ddTHH:mm:ssZ");

		String startTimeS = event.getObject("start").get("dateTime");
		Date startTime = format.parse(startTimeS);
		String endTimeS = event.getObject("end").get("dateTime");
		Date endTime = format.parse(endTimeS);
		Date now = new Date();

		return startTime.getTime() < now.getTime()
				&& now.getTime() < endTime.getTime();
	}

}
