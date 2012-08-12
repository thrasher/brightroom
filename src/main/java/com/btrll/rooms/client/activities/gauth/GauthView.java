package com.btrll.rooms.client.activities.gauth;

import com.btrll.rooms.client.DetailView;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;

public interface GauthView extends DetailView {
	public HasTapHandlers getAuthButton();

}
