package com.btrll.rooms.client.activities.gauth;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;

public class GauthView extends Composite implements GauthActivity.View {
	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, GauthView> {
	}

	interface Resources extends ClientBundle {
		public static final Resources INSTANCE = GWT.create(Resources.class);

		@Source("btrll_logo_small.png")
		public ImageResource btrll_logo_small();

	}

	@UiField
	ProgressIndicator pi;
	// @UiField
	// LayoutPanel panel;

	//
	PopupPanel pp;
	Button button;

	public GauthView() {
		initWidget(binder.createAndBindUi(this));
		// popupPanel.center();
		button = new Button("Grant via Google");

		ImageResource ir = Resources.INSTANCE.btrll_logo_small();
		Image img = new Image(ir);

		VerticalPanel v = new VerticalPanel();
		// v.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		v.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		// v.add(new Image("/images/btrll_logo_small.png"));
		v.add(img);
		v.add(new Label("Please grant access to your calendar via Google Apps"));
		v.add(button);

		pp = new PopupPanel();
		// pp.setGlassEnabled(true);
		pp.setModal(true);
		pp.add(v);

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasTapHandlers getAuthButton() {
		return button;
	}

	@Override
	public void doPopup() {
		pi.setVisible(false);
		pp.center();
		pp.show();
	}

	@Override
	public void hidePopup() {
		pi.setVisible(false);
		pp.hide();
	}

}