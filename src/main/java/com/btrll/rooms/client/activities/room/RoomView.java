package com.btrll.rooms.client.activities.room;

import com.btrll.rooms.client.DetailView;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
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

//		HTML html = new HTML("animation is purely done with css");
//		html.getElement().setAttribute("style", "text-align: center; padding: 20px;");
//		content.add(html);
		
		Label status = new Label("status");
		Image image = new Image("images/busy.png");
		
		content.add(status);
		content.add(image);
		status.getElement().setId("availability-description");
		image.getElement().setId("availability-image");


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