package com.btrll.rooms.client;

import com.google.gwt.core.client.GWT;

/**
 * Interface to represent the messages contained in resource bundle:
 * /Users/jthrasher
 * /Documents/dev/brightroom/src/main/java/com/btrll/rooms/client
 * /Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
	public static final Messages INSTANCE = GWT.create(Messages.class);

	/**
	 * Translated "About".
	 * 
	 * @return translated "About"
	 */
	@DefaultMessage("About")
	@Key("about")
	String about();

	/**
	 * Translated "BrightRoom".
	 * 
	 * @return translated "BrightRoom"
	 */
	@DefaultMessage("BrightRoom")
	@Key("appname")
	String appname();

	/**
	 * Translated "Back".
	 * 
	 * @return translated "Back"
	 */
	@DefaultMessage("Back")
	@Key("back")
	String back();

	/**
	 * Translated "Error".
	 * 
	 * @return translated "Error"
	 */
	@DefaultMessage("Error")
	@Key("error")
	String error();

	/**
	 * Translated "Home".
	 * 
	 * @return translated "Home"
	 */
	@DefaultMessage("Home")
	@Key("home")
	String home();

	/**
	 * Translated "Nav".
	 * 
	 * @return translated "Nav"
	 */
	@DefaultMessage("Nav")
	@Key("nav")
	String nav();
}
