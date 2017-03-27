package com.hjc.herol.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	// 数组转字符串
	public static String ArrayToString(String[] arr) {
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			bf.append(arr[i]);
		}
		return bf.toString();
	}

	// sha1加密
	public static String SHA1Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}

	public static final String transitionString(String str) {
		if (str == null)
			return null;
		StringBuilder sb = new StringBuilder("");
		try {
			for (int i = 0; i < str.length(); i++) {
				String temp = str.charAt(i) + "";
				byte[] b = temp.getBytes("UTF-8");
				if (null != b && b[0] != 63) {
					sb.append(temp);
				} else if (null != b && b[0] == 63) {
					sb.append("_");
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static List<String> getDiffrent(List<String> list1, List<String> list2) {
		LinkedList<String> c = new LinkedList<String>(list1);// 大集合用LinkedList
		HashSet<String> s = new HashSet<String>(list2);// 小集合用HashSet
		Iterator<String> iter = c.iterator();
		while (iter.hasNext()) {
			if (s.contains(iter.next())) {
				iter.remove();
			}
		}
		return c;

	}
	
	public static String checkString (String str){
		if(str!=null){
			if(str.indexOf(".")!=-1){
				//判断是否为科学计数法
				Pattern pattern = Pattern.compile("^((\\d+.?\\d+)[Ee]{1}(\\d+))$");
				Matcher matcher = pattern.matcher(str);
				if(matcher.matches()){
					//if(str.indexOf("E") != -1){
					//String regex = "^((\\d+.?\\d+)[Ee]{1}(\\d+))$";
					//Pattern pattern = Pattern.compile(regex);
					DecimalFormat df = new DecimalFormat("#.##");
					str = df.format(Double.parseDouble(str));
				}else{
					str=((Double)Double.parseDouble(str)).intValue()+"";
				}
			}
		}else{
			str="";
		}
		return str;
	}
	
	public static void main(String[] args){
		String str = StringUtil.SHA1Encode("何金成啊电视卡决定sadsadsa凯撒很多借口送大213213213四");
		System.out.println(str);
	}
	
}
