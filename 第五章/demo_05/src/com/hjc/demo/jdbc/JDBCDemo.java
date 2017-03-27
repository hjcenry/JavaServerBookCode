package com.hjc.demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hjc.demo.DemoBean;

public class JDBCDemo {
	public static void main(String[] args) {
		JDBCDemo demo = new JDBCDemo();
		demo.run();
	}

	public void run() {
		// JDBC七步走
		Connection conn = null;// 数据库连接对象
		Statement stmt = null;// 数据库操作对象
		try {
			// 1.加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("数据库驱动加载成功");
			// 2.指定连接字符串及用户名密码（你的密码不能这么简单哦！）
			String url = "jdbc:mysql://127.0.0.1:3306/demo";
			String user = "root";
			String pwd = "123456";
			// 3.建立数据库连接
			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("数据库连接成功");
			// 4.获取数据库操作对象statement
			stmt = conn.createStatement();
			// 5.执行sql语句
			// 创建表
			String sql = "create table t_demo1(id int(10),name varchar(50),sex varchar(4),createtime datetime)";
			int ret = stmt.executeUpdate(sql);// 执行SQL语句
			// 插入3条数据
			String insertSql = "insert into t_demo1 (id,name,sex,createtime) values (1,'何金成1','男','2016-4-7')";
			String insertSql2 = "insert into t_demo1 (id,name,sex,createtime) values (2,'何金成2','男','2016-4-7')";
			String insertSql3 = "insert into t_demo1 (id,name,sex,createtime) values (3,'何金成3','男','2016-4-7')";
			int ret21 = stmt.executeUpdate(insertSql);
			int ret22 = stmt.executeUpdate(insertSql2);
			int ret23 = stmt.executeUpdate(insertSql3);
			// 查询数据
			String querySql = "select * from t_demo1";
			ResultSet rs = stmt.executeQuery(querySql);
			// 6.处理结果
			if (ret == 0) {
				System.out.println("建表demo1成功!");
			}
			if (ret21 != 0) {
				System.out.println("插入数据1成功!");
			}
			if (ret22 != 0) {
				System.out.println("插入数据2成功!");
			}
			if (ret23 != 0) {
				System.out.println("插入数据3成功!");
			}
			List<DemoBean> beans = new ArrayList<DemoBean>();
			System.out.println("查询数据库表数据");
			while (rs.next()) {
				// ResultSet的遍历从下标1开始
				DemoBean bean = new DemoBean();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String sex = rs.getString(3);
				Date createtime = rs.getDate(4);
				bean.setId(id);
				bean.setName(name);
				bean.setSex(sex);
				bean.setCreatetime(createtime);
				beans.add(bean);
			}
			// print list
			for (DemoBean demoBean : beans) {
				System.out.println("===============================");
				System.out.println(demoBean.getId());
				System.out.println(demoBean.getName());
				System.out.println(demoBean.getSex());
				System.out.println(demoBean.getCreatetime().toLocaleString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 7.关闭对象，释放资源
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					if (!conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
