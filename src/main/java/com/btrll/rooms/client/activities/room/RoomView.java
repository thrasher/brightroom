package com.btrll.rooms.client.activities.room;

import com.btrll.rooms.client.DetailView;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.googlecode.mgwt.ui.client.widget.ProgressBar;

public class RoomView extends DetailView implements RoomActivity.View {
	// private static final Binder binder = GWT.create(Binder.class);
	//
	// interface Binder extends UiBinder<Widget, RoomView> {
	// }
	//
	// @UiField
	// FlowPanel flow;

	public RoomView() {

		// initWidget(binder.createAndBindUi(this));

		FlowPanel content = new FlowPanel();
		content.getElement().getStyle().setMarginTop(20, Unit.PX);

		content.add(new ProgressBar());

		HTML html = new HTML("animation is purely done with css");
		html.getElement().setAttribute("style", "text-align: center; padding: 20px;");
		content.add(html);

		scrollPanel.setWidget(content);
	}

	@Override
	public void setRoom(String svg) {
//		panel.removeFromParent();
//
//		panel = new LayoutPanel();
//		panel.getElement().setId("map");
//		pullToRefresh.add(panel);
//
//		inject(svg);
	}
}