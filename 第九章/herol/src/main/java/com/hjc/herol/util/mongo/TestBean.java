package com.hjc.herol.util.mongo;

import java.util.Map;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class TestBean {
	@Id
	private Long _id;
	private String msg;
	private Double score;
	private SubBean subBean;
	private Map<Long, SubBean> subBeans;

	public Long getId() {
		return _id;
	}

	public void setId(Long _id) {
		this._id = _id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public SubBean getSubBean() {
		return subBean;
	}

	public void setSubBean(SubBean subBean) {
		this.subBean = subBean;
	}

	public Map<Long, SubBean> getSubBeans() {
		return subBeans;
	}

	public void setSubBeans(Map<Long, SubBean> subBeans) {
		this.subBeans = subBeans;
	}

}
