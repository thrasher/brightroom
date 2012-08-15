BrightRoom
===========

This is a BrightRoll HAX project to allow for easy ad-hoc meeting room utilization.

The current version of the app is deployed here:
http://bit.ly/BrightRoom

Which points to a Google App Engine app here:
http://btrllroom.appspot.com/

) When a user loads the app, use GPS location info to determine what office they are in.
) A user should be able to set the default office location.
) When a user loads the app, rooms will be shown for their default office location.
) Let them specify the office location (config screen)
) use the office location to verify/add access to all calendars in that office
) use CalendarList: get for each calendar, to see if the user has access to it
) add any calendars the user doesn't have access to for the given location
) inform the user that all calendars for the given location have been added to their calendar
) store the preferred user location on the client
) show a list of rooms in the current location
) When a user selects a room, display the room's schedule for the current day.
) When a user views a room schedule, show if the room is available or not.
) When a user views an available room, let them create a reservation for 20 minutes.
) When a user views an available room, show them how much time they can book it for.
) let the user choose a room (same url as QR code to scan a room)
) show schedule of events for the room for the current day
   button to allow them to navigate to the next day
   button to allow them to navigate to the previous day
   button to schedule the next available meeting in the room
   button to schedule the next available room to meet in (need to specify number of people?)

User can easily add all rooms to their calendar.
- select office location
- add all rooms
- view confirmation dialog that rooms are added
- set default location cookie for user to the target office

User can easily remove all rooms from their calendar.
- select office location (defaults to cookied location)
- remove all rooms button
- view confirmation dialog that all rooms were removed

User can see all rooms by navigating by office
- show friendly brightroll logo
- San Francisco <- click
- New York

SF Room List
- Golden Gate <- click room to view avaiability
- Chinatown
- ...etc
- navigate back to previous screen

Golden Gate Schedule
- Show Golden Gate schedule for the day
- navigate back to the previous screen

Is there an easy way to navigate a tree structure in javascript?
Should the UI just handle an array of nodes to display, and provide navigation between array lists?



