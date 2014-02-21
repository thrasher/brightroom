BrightRoom
===========

BrightRoom is a mobile app that allows for easy ad-hoc meeting room utilization.  Most scheduling apps show a calendar-like view of resource availability over time, but BrightRoom does it differently.  BrightRoom shows what rooms are available right now, in real time from a mobile device.  It allows you to schedule an ad-hoc private discussion in a room, based on current availability.  A map is included to show all rooms in the San Francisco office that are free or busy, and allow you to schedule any of the available rooms with two clicks.  (Other office maps are coming soon!)

BrightRoom is designed for use from a mobile device, but works fine on a desktop too.  Android and iOS devices have a native-app look to them, which is familiar to many people.  Once you've logged into BrightRoom, bookmark the app (Android) or "Add to Home Screen" (iOS) so you have quick access to it in the future.

The app needs permission to your calendar before showing views.  This allows the app to schedule room resources in your name.  It also asserts your authorization, which allows the app to check rooms on your behalf.

## Current Version

The current version of the app is deployed to:
http://btrllroom.appspot.com/

## Basic Features

- All office locations with bookable resources (rooms) in Google Calendar are displayed, grouped by office location.
- A list of meeting rooms in each location is displayed alphabetically.
- When a user views a room's details, they can see free/busy information.
- If a room is currently in use by someone, their email and subject of their use is shown.
- If a room is free, it can be reserved as a BrightRoom for 15 minutes.
- QRCodes posted on each room (in the SF office), allow you to scan directly to the room's availability view, and book it if free.
- A map view (SF only) shows all of the rooms, where they are, and their availability.
- Clicking on a room in the map allows you to reserve the room.  Green == available, Red == busy.

## Useage Notes

To cancel a booking, use a desktop browser to delete the event.

Rooms are booked for 15 minute increments only, starting from when you press "Book It".  If the 15 minute interval overlaps with an upcoming reservation, you should get a message saying the booking was denied.  If the room is "green" in the map, then it's available at least until the upcoming reservation, so you can run to the room and grab it until then.

### Offices with bookable rooms currently include:

- San Francisco
- New York
- Chicago
- Palo Alto

## Backlog

The backlog lists ideas contributed by users, that are not currently implemented in the app.  BrightRoom may be re-haxed in the future, so keep the ideas coming (email thrasher@btrll)!

- When a user loads the app, use GPS location info to determine what office they are in.
- A user should be able to set the default office location.
- When a user loads the app, rooms will be shown for their default office location.
- Let them specify the office location (config screen)
- use the office location to verify/add access to all calendars in that office
- store the preferred user location on the client
- When a user selects a room, display the room's schedule for the current day.
- When a user views a room schedule, show if the room is available or not.
- When a user views an available room, show them how much time they can book it for.
- show schedule of events for the room for the current day
- button to schedule the next available meeting in the room
- button to schedule the next available room to meet in (need to specify number of people?)
