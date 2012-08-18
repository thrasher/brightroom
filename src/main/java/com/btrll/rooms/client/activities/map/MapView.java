package com.btrll.rooms.client.activities.map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;

public class MapView extends Composite implements MapActivity.View {
	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, MapView> {
	}

	@UiField
	LayoutPanel panel;

	public MapView() {
		initWidget(binder.createAndBindUi(this));

		// add the floorplan per Jessie's JS
		panel.getElement().setId("map");
		inject();
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	private native void inject() /*-{
		$wnd.showOffice();
	}-*/;

}