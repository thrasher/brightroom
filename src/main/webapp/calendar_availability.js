var is_between = function (object, before, after) { return object > before && object < after };

var currently_happening = function (event) {
	var event_start = new Date(event.start.dateTime);
	var event_end =	new Date(event.end.dateTime);
	var now = new Date();
	return is_between(now, event_start, event_end);
};

var busy = function (calendar) {
	for(var i = 0; i < calendar.result.items.length; i++) {
		if(currently_happening(calendar.result.items[i]))
			return true;
	}

	return false;
};

var update_page_availability = function (calendar, description_id, image_id) {
	console.log(document);
	description = document.getElementById(description_id);
	console.log(description_id);
	console.log(description);
	image = document.getElementById(image_id);
	console.log(image);
	if(busy(calendar)) {
		description.textContent = "Busy";
		image.setAttribute("src", "busy.png");
	} else {
		description.textContent = "Available";
		image.setAttribute("src", "available.png");
	}
}
