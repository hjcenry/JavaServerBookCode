package com.hjc.herol.util.mongo;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class SubBean {
	private Long id;
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
