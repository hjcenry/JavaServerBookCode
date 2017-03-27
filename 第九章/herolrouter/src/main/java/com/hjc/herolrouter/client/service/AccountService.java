package com.hjc.herolrouter.client.service;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;

import com.hjc.herolrouter.client.model.Account;
import com.hjc.herolrouter.util.cache.MC;
import com.hjc.herolrouter.util.hibernate.HibernateUtil;
import com.hjc.herolrouter.util.hibernate.TableIDCreator;

@Service
public class AccountService {

	public Account getAccount(String username) {
		String name = StringEscapeUtils.escapeSql(username);
		Account account = HibernateUtil.find(Account.class, "where name='"
				+ name + "'");
		return account;
	}

	/**
	 * @Title: loginOrRegist
	 * @Description: 登录或注册
	 * @param username
	 * @param password
	 * @param channel
	 * @return int 100-登录成功，101-登录密码错误，200-注册成功，201-注册密码小于6位
	 * @throws
	 */
	public int loginOrRegist(String username, String password, int channel) {
		String name = StringEscapeUtils.escapeSql(username);
		String pwd = StringEscapeUtils.escapeSql(password);
		Account account = HibernateUtil.find(Account.class, "where name='"
				+ name + "'");
		if (account != null) {// 登录
			if (HibernateUtil.find(Account.class, "where name='" + name
					+ "' and pwd='" + pwd + "'") == null) {
				return 101;
			}
			account.setLastlogintime(new Date());
			HibernateUtil.save(account);
			return 100;
		} else {// 注册
			if (pwd.length() < 6) {
				return 201;
			}
			Account newAcc = new Account();
			newAcc.setId(TableIDCreator.getTableID(Account.class, 1));
			newAcc.setName(name);
			newAcc.setPwd(pwd);
			newAcc.setChannel(channel);
			newAcc.setCreatetime(new Date());
			newAcc.setLastlogintime(new Date());
			MC.add(newAcc, newAcc.getIdentifier());
			HibernateUtil.insert(newAcc);
			return 200;
		}
	}

	/**
	 * @Title: regist
	 * @Description:
	 * @param username
	 * @param password
	 * @return int 0-注册成功，1-账号已存在，2-密码至少8位
	 * @throws
	 */
	public int regist(String username, String password, int channel) {
		String name = StringEscapeUtils.escapeSql(username);
		String pwd = StringEscapeUtils.escapeSql(password);
		if (HibernateUtil.find(Account.class, "where name='" + name + "'") != null) {
			return 1;
		}
		if (pwd.length() < 8) {
			return 2;
		}
		Account account = new Account();
		account.setId(TableIDCreator.getTableID(Account.class, 1));
		account.setName(name);
		account.setPwd(pwd);
		account.setChannel(channel);
		account.setCreatetime(new Date());
		MC.add(account, account.getIdentifier());
		HibernateUtil.insert(account);
		return 0;
	}

	/**
	 * @Title: login
	 * @Description: 登录
	 * @param username
	 * @param password
	 * @return int 0-成功，1-账号不存在，2-密码不正确
	 * @throws
	 */
	public int login(String username, String password, int channel) {
		String name = StringEscapeUtils.escapeSql(username);
		String pwd = StringEscapeUtils.escapeSql(password);
		if (HibernateUtil.find(Account.class, "where name='" + name + "'") == null) {
			return 1;
		}
		if (HibernateUtil.find(Account.class, "where name='" + name
				+ "' and pwd='" + pwd + "'") == null) {
			return 2;
		}
		return 0;
	}
}
