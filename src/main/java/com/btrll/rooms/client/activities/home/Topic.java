package com.btrll.rooms.client.activities.home;

import java.io.Serializable;

public class Topic implements Serializable {
	private static final long serialVersionUID = -1;

	private String name;

	private String key;

	public Topic() {

	}

	public Topic(String name, String key) {
		this.name = name;
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

}
