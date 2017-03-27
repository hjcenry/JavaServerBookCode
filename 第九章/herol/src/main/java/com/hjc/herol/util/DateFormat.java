package com.hjc.herol.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

public class DateFormat {
	
	public static String dateToString(Date date)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss"); 
		java.sql.Date sDate=new java.sql.Date(date.getTime());
		return df.format(sDate);
	}
	
	public static Date getNow(Date date) throws ParseException
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss"); 
		java.sql.Date sDate=new java.sql.Date(date.getTime());
		return df.parse(df.format(sDate));
	}
	
	public  static String getYesterday(){
		Calendar   cal   =   Calendar.getInstance();
	    cal.add(Calendar.DATE,   -1);
	    String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
	    return yesterday;
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
}
