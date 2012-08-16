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
		panel.getElement().setId("map");

		inject();
		// panel.add(new Label("hello world"));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	private native void inject() /*-{
		$wnd.d3.xml("/brightrollSF.svg", "image/svg+xml", function(xml) {
			var importedNode = $doc.importNode(xml.documentElement, true);
			$wnd.d3.select("#map").node().appendChild(importedNode);
			var rect = $wnd.d3.select("#map").selectAll("#rooms").selectAll(
					"*:not(g)").attr('fill', $wnd.availableColor) //to make sure all colors in form rgb()
			.on("click", function(d) {
				$wnd.toggleStatus($wnd.d3.select(this).attr('id'))
			});

		});

	}-*/;

}