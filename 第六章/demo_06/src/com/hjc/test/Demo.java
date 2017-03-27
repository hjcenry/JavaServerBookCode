package com.hjc.test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjc.demo.core.GameInit;
import com.hjc.demo.net.NetFramework;

/**
 * @ClassName: Demo
 * @Description: 服务器模拟测试窗体程序
 * @author 何金成
 * @date 2016年4月27日 下午5:31:18
 * 
 */
public class Demo extends JFrame {
	private static final long serialVersionUID = 7163462189849326849L;
	private JPanel contentPane;
	private static JTextArea console;
	private JTextArea reqText;
	private static JTextArea respText;
	private JLabel stateText;
	private static JScrollPane scroll;
	private JButton shutBtn;
	private JButton startBtn;
	private JButton sendBtn;

	public static void print(String msg) {
		System.out.println(msg);
		console.setText(console.getText() + msg + "\r\n");
		// 保持滚动条位于底部
		scroll.getVerticalScrollBar().setValue(
				scroll.getVerticalScrollBar().getMaximum());
	}

	public static void resp(String msg) {
		System.out.println(msg);
		respText.setText(msg);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo frame = new Demo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Demo() {
		setResizable(false);
		setTitle("游戏服务器Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 751, 710);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		startBtn = new JButton("开启服务器");
		startBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameInit.start();
				stateText.setText("服务器状态：开启");
				startBtn.setEnabled(false);
				shutBtn.setEnabled(true);
				sendBtn.setEnabled(true);
			}
		});
		startBtn.setBounds(10, 78, 220, 34);
		contentPane.add(startBtn);

		shutBtn = new JButton("关闭服务器");
		shutBtn.setEnabled(false);
		shutBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		shutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameInit.shut();
				stateText.setText("服务器状态：关闭");
				startBtn.setEnabled(true);
				shutBtn.setEnabled(false);
				sendBtn.setEnabled(false);
			}
		});
		shutBtn.setBounds(509, 78, 220, 34);
		contentPane.add(shutBtn);

		scroll = new JScrollPane();
		scroll.setBounds(10, 447, 719, 219);
		scroll.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		contentPane.add(scroll);
		// 分别设置水平和垂直滚动条自动出现
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		console = new JTextArea();
		scroll.setViewportView(console);
		console.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		console.setMargin(new Insets(5, 5, 5, 5));
		console.setLineWrap(true);
		console.setEditable(false);
		console.setColumns(100);
		console.setBackground(Color.LIGHT_GRAY);
		JScrollPane sendScroll = new JScrollPane();
		sendScroll.setBounds(10, 165, 719, 106);
		contentPane.add(sendScroll);
		sendScroll.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		// 分别设置水平和垂直滚动条自动出现
		sendScroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sendScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		reqText = new JTextArea();
		sendScroll.setViewportView(reqText);
		reqText.setMargin(new Insets(5, 5, 5, 5));
		reqText.setLineWrap(true);
		reqText.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		reqText.setEditable(true);
		reqText.setColumns(100);
		reqText.setBackground(Color.WHITE);
		reqText.setText("{protoid:0,userid:0,data:{}}");

		stateText = new JLabel("服务器状态：关闭");
		stateText.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		stateText.setBounds(308, 77, 201, 34);
		contentPane.add(stateText);

		JLabel lbldemo = new JLabel("游戏服务器测试Demo");
		lbldemo.setFont(new Font("微软雅黑", Font.BOLD, 22));
		lbldemo.setBounds(260, 20, 308, 23);
		contentPane.add(lbldemo);

		JLabel label_1 = new JLabel("控制台：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_1.setBounds(10, 422, 95, 15);
		contentPane.add(label_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane.setBounds(10, 305, 719, 106);
		contentPane.add(scrollPane);

		respText = new JTextArea();
		scrollPane.setViewportView(respText);
		respText.setFont(new Font("微软雅黑", Font.PLAIN, 14));

		respText.setMargin(new Insets(5, 5, 5, 5));
		respText.setLineWrap(true);
		respText.setEditable(false);
		respText.setColumns(100);
		respText.setBackground(Color.LIGHT_GRAY);

		JButton clearBtn = new JButton("清空控制台");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				console.setText("");
			}
		});
		clearBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		clearBtn.setBounds(91, 419, 139, 23);
		contentPane.add(clearBtn);

		sendBtn = new JButton("发出请求");
		sendBtn.setEnabled(false);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JSONObject req = JSON.parseObject(reqText.getText());
					// 向网络模块的读取消息队添加处理消息
					NetFramework.queue.add(req);
					respText.setText("等待响应中...");
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(Demo.this, "请求参数必须是JSON格式！");
				}
			}
		});
		sendBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		sendBtn.setBounds(91, 137, 139, 23);
		contentPane.add(sendBtn);

		JLabel label = new JLabel("响应结果：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label.setBounds(10, 280, 235, 15);
		contentPane.add(label);

		JLabel label_2 = new JLabel("请求数据：");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_2.setBounds(10, 140, 163, 15);
		contentPane.add(label_2);
	}
}
