package com.hjc.demo;

import java.io.Serializable;
import java.util.Date;

public class DemoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2753477901931164396L;

	public DemoBean() {

	}

	private long id;
	private String name;
	private String sex;
	private Date createtime;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
