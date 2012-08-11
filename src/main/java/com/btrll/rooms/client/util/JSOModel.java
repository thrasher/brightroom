package com.btrll.rooms.client.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;

/**
 * Java overlay of a JavaScriptObject.
 * 
 * @author Matt Raible, Jason Thrasher
 */
public abstract class JSOModel extends JavaScriptObject {

	// Overlay types always have protected, zero-arg constructors
	protected JSOModel() {
	}

	/**
	 * Create an empty instance.
	 * 
	 * @return new Object
	 */
	public static native JSOModel create() /*-{
		return new Object();
	}-*/;

	/**
	 * Convert a JSON encoded string into a JSOModel instance.
	 * <p/>
	 * Expects a JSON string structured like '{"foo":"bar","number":123}'
	 * 
	 * @return a populated JSOModel object
	 */
	public static native JSOModel fromJson(String jsonString) /*-{
		return eval('(' + jsonString + ')');
	}-*/;

	/**
	 * Create a well-formed JSON string representing this object.
	 * 
	 * @return
	 */
	public final String toJson() {
		return new JSONObject(this).toString();
	}

	/**
	 * Convert a JSON encoded string into an array of JSOModel instance.
	 * <p/>
	 * Expects a JSON string structured like '[{"foo":"bar","number":123},
	 * {...}]'
	 * 
	 * @return a populated JsArray
	 */
	public static native JsArray<JSOModel> arrayFromJson(String jsonString) /*-{
		return eval('(' + jsonString + ')');
	}-*/;

	public final native boolean hasKey(String key) /*-{
		return this[key] != undefined;
	}-*/;

	public final native JsArrayString keys() /*-{
		var a = new Array();
		for ( var p in this) {
			a.push(p);
		}
		return a;
	}-*/;

	@Deprecated
	public final Set<String> keySet() {
		JsArrayString array = keys();
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < array.length(); i++) {
			set.add(array.get(i));
		}
		return set;
	}

	public final native String get(String key) /*-{
		return "" + this[key];
	}-*/;

	public final native String get(String key, String defaultValue) /*-{
		return this[key] ? ("" + this[key]) : defaultValue;
	}-*/;

	public final native void set(String key, String value) /*-{
		this[key] = value;
	}-*/;

	public final int getInt(String key) {
		return Integer.parseInt(get(key));
	}

	public final boolean getBoolean(String key) {
		return Boolean.parseBoolean(get(key));
	}

	public final native JSOModel getObject(String key) /*-{
		return this[key];
	}-*/;

	public final native JsArray<JSOModel> getArray(String key) /*-{
		return this[key] ? this[key] : new Array();
	}-*/;

	/**
	 * To allow setting Lists.
	 * 
	 * @param key
	 * @param values
	 */
	public final void set(String key, List<JSOModel> values) {
		JsArray<JSOModel> array = JavaScriptObject.createArray().cast();
		for (int i = 0; i < values.size(); i++) {
			array.set(i, values.get(i));
		}
		setArray(key, array);
	}

	protected final native void setArray(String key, JsArray<JSOModel> values) /*-{
		this[key] = values;
	}-*/;

}
