package com.hjc.herol.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author songhn
 * 
 *         日期工具
 */
public class DateUtil {

	/**
	 * 判断是否是日期格式数据
	 * 
	 * @param cell
	 * @return
	 */
	public static boolean isCellDateFormatted(Cell cell) {

		if (HSSFDateUtil.isCellDateFormatted(cell)) {
			return true;
		}
		return false;

	}

	/**
	 * 将日期格式的数据按照预定的格式进行转换
	 * 
	 * @param date
	 * @param formatGeshi
	 * @return
	 */
	public static String formatDate(Date date, String formatGeshi) {

		SimpleDateFormat format = new SimpleDateFormat(formatGeshi);

		return format.format(date);

	}
	
	public static long getDateTimeBig24(String datetime){
		if(datetime.length()>16){
			String date = datetime.substring(0,10);
			if(!date.equals(StrToDateStr(date))){
				return -1;
			}else{
				try {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					long time = formatter.parse(datetime).getTime();
					return time;
				} catch (ParseException e) {
					return -1;
				}
			}
		}else{
			return -1;
		}
	}
	public static String StrToDateStr(String str) {
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = null;
	    try {
	     date = format.parse(str);
	    } catch (ParseException e) {
	    	return "-1";
	    }
	    return format.format(date);
	 }

	public static String formatDateTimeBig24(String datetime) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			datetime = DateUtil.formatDate(formatter.parse(datetime),
					"yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datetime;
	}

	public static int getSecond(String time) {
		String cc = "1970-01-01 00:00:00";
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf1.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long aMillionSeconds = cal.getTimeInMillis();
		try {
			cal.setTime(sdf.parse(cc));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long bMillionSeconds = cal.getTimeInMillis();
		return (int) ((aMillionSeconds - bMillionSeconds) / (1000));
	}

	public static String addTimes(String dateTime, int second) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date date1 = null;
		try {
			date1 = format.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long Time = (date1.getTime() / 1000) + second;
		date1.setTime(Time * 1000);
		String mydate1 = format.format(date1);
		return mydate1;
	}
	
	public static String geSystemDate(){
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getDlayDateTime(String playDate,String playTime){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		try {
			playDate = format1.format(format.parse(playDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			playTime = format2.format(format.parse(playTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sb.append(playDate);
		sb.append(" ");
		sb.append(playTime);
		return sb.toString();
	}
	
	public static String getDatebyLong(long dt){
		Date date = new Date(dt);
		return formatDate(date, "yyyy-MM-dd");
	}
	
	public static String getDateDetail(long dt){
		Date date = new Date(dt);
		return formatDate(date, "yyyy-MM-dd HH:mm");
	}
	
	public static long getTime(String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse("1970-01-01 " + time).getTime() - format.parse("1970-01-01 00:00:00").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getTimeStr(String date1, String date2){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			long time = format.parse(date1).getTime() - format.parse(date2).getTime();
			long time1 = format.parse("1970-01-01 00:00:00").getTime() + time;
			return formatDate(new Date(time1), "HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getDate(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date =  format.format(format.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static long getDay(long time){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(cal.get(Calendar.YEAR), 
				cal.get(Calendar.MONTH), 
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTimeInMillis();
	}
	
	public static String getNextDay(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long time = format.parse(date).getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time);
			cal.set(cal.get(Calendar.YEAR), 
					cal.get(Calendar.MONTH), 
					cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			return format.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
