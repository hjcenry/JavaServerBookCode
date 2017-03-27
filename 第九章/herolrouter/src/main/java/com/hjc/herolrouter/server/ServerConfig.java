package com.hjc.herolrouter.server;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hjc.herolrouter.util.cache.MCSupport;

@Table(name = "ServerConfig")
@Entity
public class ServerConfig implements MCSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6801127664661372619L;
	@Id
	private int id;
	private String name;
	private String ip;
	private int port;
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private int max;
	/**
	 * @Fields state : 服务器状态 0-新服 ，1-空闲，2-繁忙，3-爆满，4-维护
	 */
	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public long getIdentifier() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
