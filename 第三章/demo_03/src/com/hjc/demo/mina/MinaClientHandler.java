package com.hjc.demo.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler extends IoHandlerAdapter {
	int index = 0;

	public MinaClientHandler(int index) {
		this.index = index;
	}

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {

	}

	/**
	 * 当客户端接受到消息时
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// 我们已设定了服务器的消息规则是一行一行读取，这里就可以转为String:
		String s = (String) message;
		System.out.println("客户端" + index + "收到的消息：" + s);
		if (s.equals("quit")) {
			session.close(true);
		}
		// 测试将消息回送给客户端
		session.write("客户端" + index + "收到了" + s);
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		System.out.println("客户端" + index + "发送的消息：" + arg1.toString());
	}

	/**
	 * 当一个客户端被关闭时
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("客户端 " + session.getRemoteAddress() + " 断开");

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {

	}

	/**
	 * 当一个客户端连接进入时
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		session.write("客户端 " + session.getRemoteAddress() + " 连接打开！");

	}

}