package com.hjc.demo.socket;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPSocketClient {
	public static void main(String[] args) {
		String host = "localhost";
		int port = 4700;
		String sendMsg = "send data from client";
		connect(host, port, sendMsg.getBytes());
	}

	public static void connect(String server, int servPort, byte[] data) {
		// 创建socket对象用于连接服务端socket
		Socket socket = null;
		try {
			socket = new Socket(server, servPort);
			System.out.println("连接服务器并发送数据...");
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write(data);
			// 接收数据
			// 目前收到的总字节长度
			int totalBytesRcvd = 0;
			// 最后一次读取的字节长度
			int bytesRcvd;
			// 将服务器返回消息读到data字节数组中
			while (totalBytesRcvd < data.length) {
				bytesRcvd = in.read(data, totalBytesRcvd, data.length
						- totalBytesRcvd);
				if (bytesRcvd == -1) {
					throw new SocketException("连接中断...");
				}
				totalBytesRcvd += bytesRcvd;
			}
			System.out.println("接收的数据:" + new String(data));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// 关闭socket资源
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
