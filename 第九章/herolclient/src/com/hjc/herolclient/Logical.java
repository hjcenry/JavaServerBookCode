package com.hjc.herolclient;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hjc.net.LogicProtoIds;
import com.hjc.net.PvpProtoIds;
import com.hjc.net.ProtoMessage;
import com.hjc.util.HttpClient;

public class Logical {

	private JFrame frame;
	private static JTextArea reqText;
	private static JTextArea consoleArea;
	private static String LOGIC_URL;
	public static String ip;
	public static int port;
	public static String pvpIp;
	public static int pvpPort;
	private static long userid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ip = args[0];
		port = Integer.parseInt(args[1]);
		userid = Long.parseLong(args[2]);
		LOGIC_URL = "http://" + ip + ":" + port + "";
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Logical window = new Logical();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Logical() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 570, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - frame
				.getWidth()) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - frame
				.getHeight()) / 2;
		frame.setLocation(w, h);

		JLabel label = new JLabel("逻辑场景");
		label.setFont(new Font("微软雅黑", Font.BOLD, 18));
		label.setBounds(230, 10, 187, 46);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("请求消息：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_1.setBounds(10, 84, 182, 15);
		frame.getContentPane().add(label_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 109, 543, 116);
		frame.getContentPane().add(scrollPane);

		JButton button = new JButton("发送");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendReq();
			}

		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		scrollPane.setColumnHeaderView(button);

		reqText = new JTextArea();
		reqText.setText("{\"typeid\":" + PvpProtoIds.TEST
				+ ",\"data\":{},\"userid\":" + userid + "}");
		scrollPane.setViewportView(reqText);

		JLabel label_2 = new JLabel("响应消息：");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_2.setBounds(10, 242, 182, 15);
		frame.getContentPane().add(label_2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 267, 543, 219);
		frame.getContentPane().add(scrollPane_1);

		consoleArea = new JTextArea();
		scrollPane_1.setViewportView(consoleArea);

		JButton button_1 = new JButton("清空");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consoleArea.setText("");
			}
		});
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		scrollPane_1.setColumnHeaderView(button_1);

		JButton button_2 = new JButton("匹配对战");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 进入匹配对战服务器
				if (pvpIp == null || pvpPort == 0) {
					JOptionPane.showMessageDialog(frame, "请先获取对战服务器信息");
					return;
				}
				Pvp.main(new String[] { pvpIp, pvpPort + "", ip, port + "",
						userid + "" });
				frame.dispose();
			}
		});
		button_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		button_2.setBounds(460, 80, 93, 23);
		frame.getContentPane().add(button_2);

		JButton button_3 = new JButton("获取对战服务器");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String ret = HttpClient.post(LOGIC_URL, "data={\"typeid\":"
							+ LogicProtoIds.GET_PVP_SERVER
							+ ",\"data\":{},\"userid\":" + userid + "}");
					// 获取Pvp服务器
					pvpIp = JSONArray.parseArray(ret).getString(0);
					pvpPort = JSONArray.parseArray(ret).getIntValue(1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		button_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		button_3.setBounds(269, 80, 176, 23);
		frame.getContentPane().add(button_3);
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
		try {
			String ret = HttpClient.post(LOGIC_URL, "data=" + sendMsg);
			consoleArea.append(ret + "\r\n");
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(frame, "服务器繁忙，请稍后再试");
		}
	}
}
