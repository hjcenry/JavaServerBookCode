package com.hjc.herolclient;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjc.net.NettyClient;
import com.hjc.net.PvpProtoIds;
import com.hjc.net.ProtoMessage;
import com.hjc.net.SocketInHandler;

public class Pvp {

	private JFrame frame;
	public static JTextArea consoleArea;
	public static JLabel netDelayLabel;
	public static JTextArea reqText;
	private static String ip;
	private static int port;

	private Timer timer = new Timer();

	public static long userid;

	public static long netDelayStart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final String pvpIp = args[0];
		final int pvpPort = Integer.parseInt(args[1]);
		ip = args[2];
		port = Integer.parseInt(args[3]);
		userid = Long.parseLong(args[4]);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pvp window = new Pvp();
					window.frame.setVisible(true);
					new NettyClient().connect(pvpPort, pvpIp);
					window.netDelayTest();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void netDelayTest() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// 每个1s检查网络延迟
				JSONObject req = new JSONObject();
				req.put("typeid", PvpProtoIds.TEST);
				netDelayStart = System.currentTimeMillis();
				if (SocketInHandler.ctx != null) {
					SocketInHandler.write(SocketInHandler.ctx,
							req.toJSONString());
				} else {
					JOptionPane.showMessageDialog(frame, "对战服务器繁忙，请稍后再试");
					Logical.main(new String[] { ip, port + "", userid + "" });
					frame.dispose();
				}
			}
		}, new Date(System.currentTimeMillis() + 1000), 1000);
	}

	/**
	 * Create the application.
	 */
	public Pvp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				SocketInHandler.ctx.channel().close();
				// 优雅退出，释放NIO线程组
				NettyClient.workGroup.shutdownGracefully();
				NettyClient.shutdown();
			}
		});
		frame.setBounds(100, 100, 570, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - frame
				.getWidth()) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - frame
				.getHeight()) / 2;
		frame.setLocation(w, h);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 267, 543, 219);
		frame.getContentPane().add(scrollPane);

		JButton button_1 = new JButton("清空");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consoleArea.setText("");
			}
		});
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		scrollPane.setColumnHeaderView(button_1);

		consoleArea = new JTextArea();
		scrollPane.setViewportView(consoleArea);

		JLabel label = new JLabel("延迟：");
		label.setBounds(446, 14, 54, 15);
		frame.getContentPane().add(label);

		netDelayLabel = new JLabel("0ms");
		netDelayLabel.setBounds(499, 14, 54, 15);
		frame.getContentPane().add(netDelayLabel);

		JLabel label_1 = new JLabel("响应消息：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_1.setBounds(10, 242, 182, 15);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("实时对战场景");
		label_2.setFont(new Font("微软雅黑", Font.BOLD, 16));
		label_2.setBounds(207, 14, 187, 46);
		frame.getContentPane().add(label_2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 109, 543, 116);
		frame.getContentPane().add(scrollPane_1);

		JButton button = new JButton("发送");
		scrollPane_1.setColumnHeaderView(button);
		button.setFont(new Font("微软雅黑", Font.PLAIN, 12));

		reqText = new JTextArea();
		scrollPane_1.setViewportView(reqText);

		reqText.setText("{\"typeid\":" + PvpProtoIds.TEST
				+ ",\"data\":{},\"userid\":" + userid + "}");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendReq();
			}
		});

		JLabel label_3 = new JLabel("请求消息：");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_3.setBounds(10, 84, 182, 15);
		frame.getContentPane().add(label_3);

		JButton button_2 = new JButton("退出对战");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logical.main(new String[] { ip, port + "", userid + "" });
				SocketInHandler.write(SocketInHandler.ctx, "{\"typeid\":"
						+ PvpProtoIds.EXIT_SCENE + ",\"data\":{},\"userid\":"
						+ userid + "}");
				NettyClient.workGroup.shutdownGracefully();
				timer.cancel();
				frame.dispose();
			}
		});
		button_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		button_2.setBounds(460, 76, 93, 23);
		frame.getContentPane().add(button_2);
	}

	private void sendReq() {
		String sendMsg = reqText.getText();
		try {
			JSON.parseObject(sendMsg, ProtoMessage.class);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "请输入规定协议格式的数据");
			return;
		}
		if (reqText.getText().length() == 0) {
			JOptionPane.showMessageDialog(frame, "请输入请求信息");
			return;
		}
		SocketInHandler.write(SocketInHandler.ctx, reqText.getText());
	}
}
