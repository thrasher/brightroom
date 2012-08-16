package com.btrll.rooms.client.activities;

import java.util.List;

import com.btrll.rooms.client.activities.home.Topic;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.celllist.HasCellSelectedHandler;

public interface OfficeListView extends IsWidget {

	public void setTitle(String text);

	public void setRightButtonText(String text);

	public HasTapHandlers getAboutButton();

	public HasCellSelectedHandler getCellSelectedHandler();

	public void setTopics(List<Topic> createTopicsList);

	public HasText getFirstHeader();

}
