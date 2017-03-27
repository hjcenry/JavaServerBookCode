package com.hjc.demo.mongo;

public class TestBean {
	private Long id;
	private String msg;
	private Double score;
	private SubBean subBean;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
