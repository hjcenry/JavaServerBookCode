package com.hjc.demo.socket;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSocketClient {

	public static void main(String[] args) {
		send();
	}

	// 发送者发给接收端数据,接收端返回数据给发送者
	public static void send() {
		System.out.println("发送端发送数据");
		// 发送端
		try {
			// 创建发送方的套接字 对象 采用9004默认端口号
			DatagramSocket socket = new DatagramSocket(9004);
			// 发送的内容
			String text = "hello from sender!";
			byte[] buf = text.getBytes();
			// 构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号。
			DatagramPacket packet = new DatagramPacket(buf, buf.length,
					InetAddress.getByName("localhost"), 9001);
			// 从此套接字发送数据报包
			socket.send(packet);
			// 接收，接收者返回的数据
			displayReciveInfo(socket);
			// 关闭此数据报套接字。
			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接收数据并打印出来
	 * 
	 * @param socket
	 * @throws IOException
	 */
	public static void displayReciveInfo(DatagramSocket socket)
			throws IOException {
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);

		byte data[] = packet.getData();// 接收的数据
		InetAddress address = packet.getAddress();// 接收的地址
		System.out.println("接收的文本==>" + new String(data));
		System.out.println("接收的ip地址==>" + address.toString());
		System.out.println("接收的端口==>" + packet.getPort()); // 9004
	}
}
