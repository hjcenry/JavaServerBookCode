package pvpclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjc.herolpvp.util.HttpClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JPasswordField pwdText;
	private JTextField nameText;

	private static final String LOGIN_URL = "http://127.0.0.1:81/herolrouter/route/login";
	private static final String REGIST_URL = "http://127.0.0.1:81/herolrouter/route/regist";

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
		frame.setBounds(100, 100, 429, 280);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lbldemo = new JLabel("英雄对战Demo");
		lbldemo.setFont(new Font("微软雅黑", Font.PLAIN, 18));
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
		pwdText.setBounds(154, 119, 160, 21);
		frame.getContentPane().add(pwdText);

		nameText = new JTextField();
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
					System.out.println(ret);
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
					String ret = HttpClient.post(LOGIN_URL, "name=" + name
							+ "&pwd=" + pwd + "");
					JSONArray jsonRet = JSONArray.parseArray(ret);
					int code = jsonRet.getIntValue(0);
					String msg = jsonRet.getString(1);
					if (code == 0) {
						// 登录成功
						frame.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(frame, msg);
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "服务器繁忙，请稍后再试");
				}
			}
		});
		loginBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		loginBtn.setBounds(221, 173, 93, 23);
		frame.getContentPane().add(loginBtn);
	}
}
