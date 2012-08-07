package com.btrll.rooms.client.activities.home;

import java.io.Serializable;

public class Topic implements Serializable {
	private static final long serialVersionUID = -1;

	private String name;

	private int count;

	public Topic() {

	}

	public Topic(String name, int count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
