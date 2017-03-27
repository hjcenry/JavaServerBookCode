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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alibaba.fastjson.JSONArray;
import com.hjc.util.HttpClient;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login {

	private JFrame frame;
	private JPasswordField pwdText;
	private JTextField nameText;

	private static final String LOGIN_URL = "http://127.0.0.1:81/herolrouter/route/login";
	private static final String REGIST_URL = "http://127.0.0.1:81/herolrouter/route/regist";
	private static final String GET_SERVER_URL = "http://127.0.0.1:81/herolrouter/route/getServers";

	// private static final String LOGIN_REGIST_URL =
	// "http://127.0.0.1:81/herolrouter/route/loginOrRegist";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
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
		JLabel lbldemo = new JLabel("皇室战争Demo");
		lbldemo.setFont(new Font("微软雅黑", Font.BOLD, 18));
		lbldemo.setBounds(154, 10, 193, 48);
		frame.getContentPane().add(lbldemo);

		JLabel label = new JLabel("账号：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label.setBounds(100, 81, 64, 17);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("密码：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_1.setBounds(101, 121, 64, 17);
		frame.getContentPane().add(label_1);

		pwdText = new JPasswordField();
		pwdText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					login();
				}
			}
		});
		pwdText.setBounds(154, 119, 160, 21);
		frame.getContentPane().add(pwdText);

		nameText = new JTextField();
		nameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					login();
				}
			}
		});
		nameText.setBounds(154, 79, 160, 21);
		frame.getContentPane().add(nameText);
		nameText.setColumns(10);

		JButton registBtn = new JButton("注册");
		registBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				String pwd = new String(pwdText.getPassword());
				if (name.length() == 0) {
					JOptionPane.showMessageDialog(frame, "请输入账号");
					return;
				}
				if (pwd.length() == 0) {
					JOptionPane.showMessageDialog(frame, "请输入密码");
					return;
				}
				try {
					String ret = HttpClient.post(REGIST_URL, "name=" + name
							+ "&pwd=" + pwd + "");
					JSONArray jsonRet = JSONArray.parseArray(ret);
					String msg = jsonRet.getString(1);
					JOptionPane.showMessageDialog(frame, msg);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "服务器繁忙，请稍后再试");
				}
			}
		});
		registBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		registBtn.setBounds(100, 173, 93, 23);
		frame.getContentPane().add(registBtn);

		JButton loginBtn = new JButton("登录");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		loginBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		loginBtn.setBounds(221, 173, 93, 23);
		frame.getContentPane().add(loginBtn);
	}

	private void login() {
		String name = nameText.getText();
		String pwd = new String(pwdText.getPassword());
		if (name.length() == 0) {
			JOptionPane.showMessageDialog(frame, "请输入账号");
			return;
		}
		if (pwd.length() == 0) {
			JOptionPane.showMessageDialog(frame, "请输入密码");
			return;
		}
		try {
			String ret = HttpClient.post(LOGIN_URL, "name=" + name + "&pwd="
					+ pwd + "");
			JSONArray jsonRet = JSONArray.parseArray(ret);
			int code = jsonRet.getIntValue(0);
			String msg = jsonRet.getString(1);
			if (code == 0) {
				// 登录成功
				frame.setVisible(false);
				String servers = HttpClient.post(GET_SERVER_URL, "");
				long userid = jsonRet.getLongValue(2);
				ChooseServer.main(new String[] { servers, userid + "" });
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, msg);
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(frame, "服务器繁忙，请稍后再试");
		}
	}
}
