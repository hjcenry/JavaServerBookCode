package pvpclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import nettytest.NettyClient;
import nettytest.SocketInHandler;

import com.alibaba.fastjson.JSONObject;
import com.hjc.herolpvp.net.ProtoIds;
import com.hjc.herolpvp.net.socket.SocketHandler;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import java.awt.Font;

public class Pvp {

	private JFrame frame;
	public static JTextArea consoleArea;
	public static JLabel netDelayLabel;
	public static JTextArea reqText;

	public static long userid = 1004;

	public static long netDelayStart = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pvp window = new Pvp();
					window.frame.setVisible(true);
					window.netDelayTest();
					new NettyClient().connect(8700, "localhost");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void netDelayTest() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// 每个1s检查网络延迟
				JSONObject req = new JSONObject();
				req.put("typeid", ProtoIds.TEST);
				netDelayStart = System.currentTimeMillis();
				if (SocketInHandler.ctx != null) {
					SocketInHandler.write(SocketInHandler.ctx,
							req.toJSONString());
				}
			}
		}, new Date(), 1000);
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
			}
		});
		frame.setBounds(100, 100, 570, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

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
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SocketInHandler.write(SocketInHandler.ctx, reqText.getText());
					}
				});

		JLabel label_3 = new JLabel("请求消息：");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_3.setBounds(10, 84, 182, 15);
		frame.getContentPane().add(label_3);
	}
}
