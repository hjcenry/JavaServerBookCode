package com.hjc.demo.net;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: NetHandler
 * @Description: 网络消息处理接口
 * @author 何金成
 * @date 2016年4月27日 下午3:31:04
 * 
 */  
public interface NetHandler {
	public void read(JSON request);
	
	public void write(JSON request);

	public void connect(String host);

	public void disconnect(String host);

	public void exceptionCaught(String host);
	
}
