package com.hjc.herol.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

	private static String patter = "yyyy-MM-dd";

	private static SimpleDateFormat sdf = new SimpleDateFormat(patter);
	
	public  static String getYesterday(){
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE,-1);
	    String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	    return yesterday;
	}
	
	public  static String geTodayTime(){
		Calendar cal = Calendar.getInstance();
	    String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	    return yesterday;
	}

	/**  
     */
	public static String getYearByString(String str) {

		Date date = convertStringToDate(str);
		Calendar cal = convertDateToCalendar(date);

		return String.valueOf(cal.get(Calendar.YEAR));
	}

	/**
	 * @param str
	 * @return
	 */
	public static String getMonthByString(String str) {

		Date date = convertStringToDate(str);
		Calendar cal = convertDateToCalendar(date);

		return String.valueOf(cal.get(Calendar.MONTH) + 1);

	}

	/**
	 * @param str
	 * @return
	 */
	public static String getDayByString(String str) {

		Date date = convertStringToDate(str);
		Calendar cal = convertDateToCalendar(date);

		return String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * @param str
	 * @return
	 */
	public static String getPartByString(String str) {

		int temp = Integer.parseInt(getMonthByString(str)) + 1;
		if (temp <= 6) {
			return "1";
		}
		return "2";

	}

	/**  
     */
	public static Calendar convertDateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;

	}

	/**  
     */
	public static String convertDateToString(Date date) {
		return sdf.format(date);
	}
	
	
	public static String convertDateToStringDetail(Date date){
		String format = "yyyy-MM-dd HH:mm:ss";
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	/**  
     *  
     *  
     */

	public static Date convertStringToDate(String sDate) {
		Date date = null;
		try {
			date = sdf.parse(sDate);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return date;
	}

	/**  
     *  
     */

	public static void changePattern(String patter) {
		DateFormatUtil.patter = patter;

		DateFormatUtil.sdf = new SimpleDateFormat(patter);
	}

	public static String getToday() {
		Calendar cl = Calendar.getInstance();
		String month = "";
		String day = "";
		String today = "";
		String today2 = "";
		int m = cl.get(Calendar.MONTH) + 1;
		if (m < 10) {
			month = "0" + String.valueOf(m);
		} else {
			month = String.valueOf(m);
		}
		int d = cl.get(Calendar.DATE);
		if (d < 10) {
			day = "0" + String.valueOf(d);
		} else {
			day = String.valueOf(d);
		}
		today = cl.get(Calendar.YEAR) + month + day + cl.get(Calendar.HOUR_OF_DAY) + cl.get(Calendar.MINUTE)
				+ cl.get(Calendar.SECOND);
		today2 = cl.get(Calendar.YEAR) + "-" + month + "-" + day + " " + cl.get(Calendar.HOUR_OF_DAY) + ":"
				+ cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);
		return today2;
	}
	
	public static String alertTimeType(Date datetime) throws Exception{
		String result="";
		String alertTime = new SimpleDateFormat("HH:mm").format(datetime);
		String yesterday = getYesterday();
		String today = geTodayTime();
		if(new Date().getTime()-datetime.getTime()<10*60*1000){
			result = "刚刚";
		}else if(new Date().getTime()-datetime.getTime()>=10*60*1000 && new Date().getTime()-datetime.getTime()<60*60*1000){
			result = new Date().getMinutes()-datetime.getMinutes()+"分钟前";
		}else if(new Date().getTime()-datetime.getTime()>=60*60*1000 && new Date().getTime()-datetime.getTime()<5*60*60*1000){
			result = new Date().getHours()-datetime.getHours()+"小时前";
		}else{
			if(datetime.getTime()-sdf.parse(today).getTime()>=0){
				result = "今天"+alertTime;
			}else if(datetime.getTime()-sdf.parse(today).getTime()<0 && datetime.getTime()-sdf.parse(yesterday).getTime()>=0 ){
				result = "昨天"+alertTime;
			}else if(datetime.getTime()-sdf.parse(yesterday).getTime()<0 && (new Date().getTime()-datetime.getTime())/(24*60*60*1000)<7){
				result = new Date().getDate()-datetime.getDate()+"天前";
			}else if((new Date().getTime()-datetime.getTime())/(24*60*60*1000)>=7 && (new Date().getTime()-datetime.getTime())/(7*24*60*60*1000)<2){
				result = "一周前";
			}else if(new Date().getYear()-datetime.getYear()<1){
				result = (datetime.getMonth()+1)+"月"+datetime.getDate()+"日";
			}else{
				result = new SimpleDateFormat("yyyy").format(datetime).substring(2,new SimpleDateFormat("yyyy").format(datetime).length())+"年"+(datetime.getMonth()+1)+"月"+datetime.getDate()+"日";
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		//changePattern("yyyy-MM-dd hh:mm:ss");
		//System.out.println(DateFormatUtil.convertDateToStringDetail(new Date(Long.valueOf("1397206735"))));
		// Calendar cal = DateFormatUtil.convertDateToCalendar(new Date());
		// System.out.println(cal.getTime());
		// cal.add(Calendar.DAY_OF_WEEK, 3);
		// System.out.println(cal.getTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(alertTimeType(format.parse("2013-1-1 11:30:12")));
	}
}