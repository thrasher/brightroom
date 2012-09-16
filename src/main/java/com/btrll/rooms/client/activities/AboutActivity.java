package com.btrll.rooms.client.activities;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.Messages;
import com.btrll.rooms.client.event.ActionEvent;
import com.btrll.rooms.client.event.ActionNames;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class AboutActivity extends DetailActivity {
	public interface View extends DetailActivity.View {
	}

	private final ClientFactory clientFactory;

	public AboutActivity(ClientFactory clientFactory) {
		super(clientFactory.getAboutView(), "nav");
		this.clientFactory = clientFactory;

	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		super.start(panel, eventBus);
		View aboutView = clientFactory.getAboutView();

		aboutView.getBackbuttonText().setText(Messages.INSTANCE.home());

		aboutView.getHeader().setText(Messages.INSTANCE.about());

		aboutView.getMainButtonText().setText(Messages.INSTANCE.nav());

		addHandlerRegistration(aboutView.getBackbutton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						ActionEvent.fire(eventBus, ActionNames.BACK);

					}
				}));

		panel.setWidget(aboutView);

	}

}
