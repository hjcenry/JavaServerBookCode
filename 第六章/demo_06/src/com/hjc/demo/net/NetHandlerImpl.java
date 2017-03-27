package com.hjc.demo.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjc.demo.core.Router;
import com.hjc.demo.util.ExecutorPool;
import com.hjc.test.Demo;

/**
 * @ClassName: NetHandler
 * @Description: 网络消息处理实现类
 * @author 何金成
 * @date 2016年4月27日 下午3:31:04
 * 
 */
public class NetHandlerImpl implements NetHandler {

	@Override
	public void read(final JSON request) {
		// 使用线程池处理消息。尽快返回处理线程，提高并发效率
		ExecutorPool.handleThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					// TODO 请求消息接入逻辑处理
					Demo.print("read :" + request.toJSONString());
					Router.getInstance().handle(NetFramework.inst,
							(JSONObject) request);
				} catch (Exception e) {
					e.printStackTrace();
					Demo.print(e.getMessage());
				}
			}
		});
	}

	@Override
	public void write(JSON response) {
		Demo.resp(response.toJSONString());
		Demo.print("write:" + response.toJSONString());
	}

	@Override
	public void connect(String host) {

	}

	@Override
	public void disconnect(String host) {

	}

	@Override
	public void exceptionCaught(String host) {

	}

}
