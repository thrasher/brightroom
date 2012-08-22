package com.btrll.rooms.client.activities.map;

import com.btrll.rooms.client.ChromeWorkaround;
import com.btrll.rooms.client.DetailViewGwtImpl;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowFooter;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel.Pullhandler;

public class MapView extends DetailViewGwtImpl implements MapActivity.View {
	// private static final Binder binder = GWT.create(Binder.class);
	//
	// interface Binder extends UiBinder<Widget, MapView> {
	// }
	//
	// @UiField
	// FlowPanel flow;

	private PullPanel pullToRefresh;

	private PullArrowHeader pullArrowHeader;
	private PullArrowFooter pullArrowFooter;
	LayoutPanel panel;

	public MapView() {

		// initWidget(binder.createAndBindUi(this));

		main.remove(scrollPanel);

		pullToRefresh = new PullPanel();

		pullArrowHeader = new PullArrowHeader();

		pullToRefresh.setHeader(pullArrowHeader);

		pullArrowFooter = new PullArrowFooter();
		pullToRefresh.setFooter(pullArrowFooter);

		panel = new LayoutPanel();
		panel.getElement().setId("map");
		pullToRefresh.add(panel);

		main.add(pullToRefresh);

		ChromeWorkaround.maybeUpdateScroller(scrollPanel);
	}

	@Override
	public void setHeaderPullHandler(Pullhandler pullHandler) {
		pullToRefresh.setHeaderPullhandler(pullHandler);

	}

	@Override
	public PullArrowWidget getPullHeader() {
		return pullArrowHeader;
	}

	@Override
	public void refresh() {
		pullToRefresh.refresh();

	}

	@Override
	public void setFooterPullHandler(Pullhandler pullHandler) {
		pullToRefresh.setFooterPullHandler(pullHandler);

	}

	@Override
	public PullArrowWidget getPullFooter() {
		return pullArrowFooter;
	}

	@Override
	public HasRefresh getPullPanel() {
		return pullToRefresh;
	}

	@Override
	public void setMap(String svg) {
		panel.removeFromParent();

		panel = new LayoutPanel();
		panel.getElement().setId("map");
		pullToRefresh.add(panel);

		inject(svg);
	}

	private native void inject(String map) /*-{
		$wnd.showOffice(map);
	}-*/;

}