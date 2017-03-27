package com.hjc.herol.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CalendarUtil {
	
	/**
	 * 
	 * 方法描述.
	 * week从1开始，1代表星期天
	 * @param month
	 * @param year
	 * @param week
	 * @return
	 */
	
	public static Integer getDaysByWeek(Integer month,Integer year,Integer week){
		Integer num = null;
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, 1);
		int mountDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		num = mountDay / 7;
		int yu = mountDay % 7;
		for (int i = 1; i <= yu; i++) {
			c.set(Calendar.DAY_OF_MONTH, i);
			if (week == c.get(Calendar.DAY_OF_WEEK)) {
				num++;
				break;
			}
		}
		return num;
	}
	
	public static Map<Integer, Integer> getDaysByWeek(Integer month,Integer year){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Integer num = null;
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, 1);
		int mountDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		num = mountDay / 7;
		int yu = mountDay % 7;
		for (int j = 1; j <= 7; j++) {
			for (int i = 1; i <= yu; i++) {
			c.set(Calendar.DAY_OF_MONTH, i);
				map.put(j, num);
				if (j == c.get(Calendar.DAY_OF_WEEK)) {
					map.put(j, num+1);
					break;
				}
			}
		}
		return map;
	}
	
	
	
	public static Map<Integer,Integer> getDaysByWeek2(Integer month,Integer year,Integer begin){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, 1);
		for(int i = 1; i < begin; i++) {
			c.set(Calendar.DAY_OF_MONTH, i);
			if(1 == c.get(Calendar.DAY_OF_WEEK)){
				Integer value = map.get(1)==null?0:map.get(1);
				map.put(1, value+1);
			}
			if(2 == c.get(Calendar.DAY_OF_WEEK)){
				Integer value = map.get(2)==null?0:map.get(2);
				map.put(2, value+1);
			}
			if(3 == c.get(Calendar.DAY_OF_WEEK)){
				Integer value = map.get(3)==null?0:map.get(3);
				map.put(3, value+1);
			}
			if(4 == c.get(Calendar.DAY_OF_WEEK)){
				Integer value = map.get(4)==null?0:map.get(4);
				map.put(4, value+1);
			}
			if(5 == c.get(Calendar.DAY_OF_WEEK)){
				Integer value = map.get(5)==null?0:map.get(5);
				map.put(5, value+1);
			}
			if(6 == c.get(Calendar.DAY_OF_WEEK)){
				Integer value = map.get(6)==null?0:map.get(6);
				map.put(6, value+1);
			}
			if(7 == c.get(Calendar.DAY_OF_WEEK)){
				Integer value = map.get(7)==null?0:map.get(7);
				map.put(7, value+1);
			}
		}
		
		return map;
	}
	
	
	
	
	
	
	/**
	 * 得到某年某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month,Integer day) {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.MONTH, month - 1);
		if(day == null){
			cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		}else {
			cal.set(Calendar.DAY_OF_MONTH, day);
		}
		
		StringBuffer sb = new StringBuffer();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		sb.append(date).append(" 00:00");
		return sb.toString();
		
	}

	/**
	 * 得到某年某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		StringBuffer sb = new StringBuffer();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		sb.append(date).append(" 23:59");
		return sb.toString();

	}
	
	public static Map<Integer, Integer> getWorkDayNum(int year,int month,int start,int end){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		if(start != end){
			for (int i = start; i <= end; i++) {
				Calendar c = Calendar.getInstance();
				c.set(year, month-1, i);
				if(1 == c.get(Calendar.DAY_OF_WEEK)){
					Integer value = map.get(1)==null?0:map.get(1);
					map.put(1, value+1);
				}
				if(2 == c.get(Calendar.DAY_OF_WEEK)){
					Integer value = map.get(2)==null?0:map.get(2);
					map.put(2, value+1);
				}
				if(3 == c.get(Calendar.DAY_OF_WEEK)){
					Integer value = map.get(3)==null?0:map.get(3);
					map.put(3, value+1);
				}
				if(4 == c.get(Calendar.DAY_OF_WEEK)){
					Integer value = map.get(4)==null?0:map.get(4);
					map.put(4, value+1);
				}
				if(5 == c.get(Calendar.DAY_OF_WEEK)){
					Integer value = map.get(5)==null?0:map.get(5);
					map.put(5, value+1);
				}
				if(6 == c.get(Calendar.DAY_OF_WEEK)){
					Integer value = map.get(6)==null?0:map.get(6);
					map.put(6, value+1);
				}
				if(7 == c.get(Calendar.DAY_OF_WEEK)){
					Integer value = map.get(7)==null?0:map.get(7);
					map.put(7, value+1);
				}
			}
		}
		return map;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		//System.out.println(getLastDayOfMonth(2014, 12).substring(8, 10));
		System.out.println(getWorkDayNum(2014, 12, 31, 31).get("1"));
		
	}
	
	
	
}
