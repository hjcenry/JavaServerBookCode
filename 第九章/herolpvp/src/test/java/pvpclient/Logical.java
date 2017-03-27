package pvpclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;

import com.hjc.herolpvp.util.HttpClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Logical {

	private JFrame frame;
	private static JTextArea reqText;
	private static JTextArea consoleArea;
	private static String LOGIC_URL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String ip = args[0];
		String port = args[1];
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

		JLabel label = new JLabel("逻辑场景");
		label.setFont(new Font("微软雅黑", Font.BOLD, 16));
		label.setBounds(240, 10, 187, 46);
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
				try {
					HttpClient.post(LOGIC_URL, "data=" + reqText.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "服务器繁忙，请稍后再试");
				}
			}
		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		scrollPane.setColumnHeaderView(button);

		reqText = new JTextArea();
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
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		scrollPane_1.setColumnHeaderView(button_1);
	}
}
