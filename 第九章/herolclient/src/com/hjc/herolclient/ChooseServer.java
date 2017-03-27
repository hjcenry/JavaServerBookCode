package com.hjc.herolclient;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.alibaba.fastjson.JSONArray;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChooseServer {

	public JFrame frame;
	private static JSONArray serversArray;
	private JList<String> list;
	private static long userid;
	public static Map<Integer, JSONArray> serverConfigMap = new HashMap<Integer, JSONArray>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			serversArray = JSONArray.parseArray(args[0]);
			userid = Long.valueOf(args[1]);
		}
		for (int i = 0; i < serversArray.size(); i++) {
			serverConfigMap.put(i, serversArray.getJSONArray(i));
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseServer window = new ChooseServer();
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
	public ChooseServer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 430, 290);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - frame
				.getWidth()) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - frame
				.getHeight()) / 2;
		frame.setLocation(w, h);

		JLabel label = new JLabel("选择服务器");
		label.setFont(new Font("微软雅黑", Font.BOLD, 18));
		label.setBounds(161, 10, 193, 48);
		frame.getContentPane().add(label);

		JButton button = new JButton("进入游戏");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterGame();
			}
		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		button.setBounds(286, 223, 122, 23);
		frame.getContentPane().add(button);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 74, 398, 139);
		frame.getContentPane().add(scrollPane);

		Vector<String> servers = new Vector<String>();
		for (Integer serverId : serverConfigMap.keySet()) {
			JSONArray server = serverConfigMap.get(serverId);
			String serverName = server.getString(3);
			String state = null;
			/* 务器状态 0-新服 ，1-空闲，2-繁忙，3-爆满，4-维护 */
			switch (server.getIntValue(2)) {
			case 0:
				state = "新服";
				break;
			case 1:
				state = "空闲";
				break;
			case 2:
				state = "繁忙";
				break;
			case 3:
				state = "爆满";
				break;
			case 4:
				state = "维护";
				break;
			}
			servers.add((serverId + 1) + "     " + state + "     "
					+ "                                " + serverName);
		}
		list = new JList(servers);
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					enterGame();
				}
			}
		});
		scrollPane.setViewportView(list);

		JButton button_1 = new JButton("返回");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Login.main(null);
			}
		});
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		button_1.setBounds(154, 223, 122, 23);
		frame.getContentPane().add(button_1);
	}

	private void enterGame() {
		int index = list.getSelectedIndex();
		if (index == -1) {
			JOptionPane.showMessageDialog(frame, "请选择服务器");
			return;
		}
		Logical.main(new String[] { serverConfigMap.get(index).getString(0),
				serverConfigMap.get(index).getString(1),
				(userid * 1000 + (index + 1)) + "" });
		frame.dispose();
	}
}
