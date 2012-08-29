package com.btrll.rooms.client;

import com.btrll.rooms.client.event.ActionEvent;
import com.btrll.rooms.client.event.ActionNames;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.event.ShowMasterEvent;

public class DetailActivity extends MGWTAbstractActivity {
	public interface View extends IsWidget {
		public HasText getHeader();

		public HasText getBackbuttonText();

		public HasTapHandlers getBackbutton();

		public HasText getMainButtonText();

		public HasTapHandlers getMainButton();
	}

	private final View detailView;
	private final String eventId;

	public DetailActivity(View detailView, String eventId) {
		this.detailView = detailView;
		this.eventId = eventId;

	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		addHandlerRegistration(detailView.getMainButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						eventBus.fireEvent(new ShowMasterEvent(eventId));

					}
				}));

		addHandlerRegistration(detailView.getBackbutton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						ActionEvent.fire(eventBus, ActionNames.BACK);

					}
				}));
	}

}
