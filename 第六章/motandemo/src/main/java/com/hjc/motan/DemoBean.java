package com.hjc.motan;

import java.io.Serializable;

public class DemoBean implements Serializable {
	private static final long serialVersionUID = -3822931722874826165L;
	private long id;
	private String name;
	private double score;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
