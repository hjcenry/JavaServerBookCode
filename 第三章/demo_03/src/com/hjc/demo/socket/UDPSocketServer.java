package com.hjc.demo.socket;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSocketServer {
	public static void main(String[] args) {
		recive();
	}

	public static void recive() {
		System.out.println("接收端启动");
		// 接收端
		try {
			// 创建接收方的套接字 对象 并与send方法中DatagramPacket的ip地址与端口号一致
			DatagramSocket socket = new DatagramSocket(9001,
					InetAddress.getByName("localhost"));
			// 接收数据的buf数组并指定大小
			byte[] buf = new byte[1024];
			// 创建接收数据包，存储在buf中
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			// 接收操作,代码会停顿在这里，直到接收到数据包
			socket.receive(packet);
			byte data[] = packet.getData();// 接收的数据
			InetAddress address = packet.getAddress();// 接收的地址
			System.out.println("接收的文本==>" + new String(data));
			System.out.println("接收的ip地址==>" + address.toString());
			System.out.println("接收的端口==>" + packet.getPort()); // 9004
			// 告诉发送者 接收完毕了
			String temp = "接收端接收完毕了";
			byte buffer[] = temp.getBytes();
			// 创建数据报，指定发送给 发送者的socketaddress地址
			DatagramPacket packet2 = new DatagramPacket(buffer, buffer.length,
					packet.getSocketAddress());
			// 发送
			socket.send(packet2);
			// 关闭
			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
