package com.btrll.rooms.client.activities.map;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.model.Config;
import com.btrll.rooms.client.model.Office;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler.PullActionHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel.Pullhandler;

public class MapActivity extends DetailActivity {
	public interface View extends DetailActivity.View {
		public void setHeaderPullHandler(Pullhandler pullHandler);

		public void setFooterPullHandler(Pullhandler pullHandler);

		public PullArrowWidget getPullHeader();

		public PullArrowWidget getPullFooter();

		public void refresh();

		public HasRefresh getPullPanel();

		public void setMap(String svg);
	}

	static final Logger logger = Logger.getLogger("MapActivity");
	private final ClientFactory clientFactory;
	private final MapPlace place;

	public MapActivity(ClientFactory clientFactory, MapPlace place) {
		super(clientFactory.getMapView(), "nav");
		this.clientFactory = clientFactory;
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		logger.fine("MapActivity start");
		super.start(panel, eventBus);

		Office office = getOffice(place.getOfficeId());

		final View view = clientFactory.getMapView();
		view.getHeader().setText(office.getName());
		view.getMainButtonText().setText("Nav");
		view.getBackbuttonText().setText("UI");

		view.getPullHeader().setHTML("pull down");
		view.getPullFooter().setHTML("pull up");

		PullArrowStandardHandler headerHandler = new PullArrowStandardHandler(
				view.getPullHeader(), view.getPullPanel());
		headerHandler.setErrorText("Error");
		headerHandler.setLoadingText("Loading " + office.getName());
		headerHandler.setNormalText("pull to update " + office.getName());
		headerHandler.setPulledText("release to refresh " + office.getName());
		headerHandler.setPullActionHandler(new PullActionHandler() {
			@Override
			public void onPullAction(final AsyncCallback<Void> callback) {
				new Timer() {
					@Override
					public void run() {
						// callback.onFailure(null);
						// view.render(list);
						view.refresh();
						callback.onSuccess(null);
					}
					// TODO change to 1ms, once jsni hook reloads data
				}.schedule(1000);
			}
		});
		view.setHeaderPullHandler(headerHandler);

		PullArrowStandardHandler footerHandler = new PullArrowStandardHandler(
				view.getPullFooter(), view.getPullPanel());
		footerHandler.setErrorText("Error");
		footerHandler.setLoadingText("Loading " + office.getName());
		footerHandler.setNormalText("pull to update " + office.getName());
		footerHandler.setPulledText("release to refresh " + office.getName());
		footerHandler.setPullActionHandler(new PullActionHandler() {
			@Override
			public void onPullAction(final AsyncCallback<Void> callback) {
				new Timer() {
					@Override
					public void run() {
						// TODO: refresh display data
						// callback.onFailure(null);
						// display.render(list);
						view.refresh();
						callback.onSuccess(null);
					}
					// TODO change to 1ms, once jsni hook reloads data
				}.schedule(1000);
			}
		});
		view.setFooterPullHandler(footerHandler);

		view.setMap(office.getMap());

		panel.setWidget(view);
	}

	private static Office getOffice(long id) {
		return getConfig().getOffices().get((int) id - 1);
	}

	private static native Config getConfig() /*-{
		return $wnd.config;
	}-*/;

}
