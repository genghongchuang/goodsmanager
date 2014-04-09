package com.geng.goodsmanage.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 获取系统当前时间的工具类
 * @author genghongchuang
 * @date 2013-5-15
 */
public class TimeUtil {
	private static Logger logger=Logger.getLogger(Time.class);
	/**
	 * 时间格式为 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String  simpleDate(){
		Date date = new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String simpleDate= dateFormat.format(date);
		return simpleDate;
	}
/**
 * 时间格式为 yyMMddHHmms
 * @param date
 * @return
 */
	public static String idDate(Date date){
		DateFormat dateFormat=new SimpleDateFormat("yyMMddHHmms");
		String idDate= dateFormat.format(date);
		return idDate;
	}
	/**
	 * 时间格式为 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String simpleDate(Date date){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String simpleDate="时间转换失败";
		if(date!=null){
			try {
				simpleDate = dateFormat.format(date);
			} catch (Exception e) {
				logger.error("时间转换出错！", e);
			}
		}
		
		
		return simpleDate;
	}
	/**
	 * 将时间格式转换为yyyy-MM-ddd 字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToStr(Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将时间格式转换为yyyy-MM 字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToMonthStr(Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			return sdf.format(date);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将时间格式转换为yyyy 字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToYearStr(Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			return sdf.format(date);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将格式为yyyy-MM-dd HH:mm:ss类型的字符串转为 date
	 * @param simplDate
	 * @return
	 */
	public static Date stringToDate(String simpleDate){
		try {
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date date= dateFormat.parse(simpleDate);
			return date;
		}catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将时间格式为yyyy-MM-dd的字符串转换为yyyy-MM-dd 23:59:59
	 * @param simpleDate
	 * @return
	 */
	public static Date stringToMaxDate(String simpleDate){
		
		try {
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuffer sb=new StringBuffer(simpleDate);
			sb.append(" 23:59:59");
			Date date= dateFormat.parse(sb.toString());
			return date;
		} catch (ParseException e) {
			logger.error("转换时间"+simpleDate+"出错！", e);
			return null;
		}
	}
	/**
	 * 将毫秒转换为特定格式的时间
	 * @param millis 毫秒
	 * @param pattern 时间格式
	 * @return
	 */
	public static String millisToDate(long millis){
		long day = 0;
		long hour = 0;
		long minute = 0;
		long second = 0;
		if (millis > 0) {
			day = millis / 86400000L;
			long unDay = millis % 86400000L;
			hour = unDay / 3600000L;
			long unHour = unDay % 3600000L;
			minute = unHour / 60000L;
			long unMinute = unHour % 60000L;
			second = unMinute / 1000L;
		}
		String expendTime = day + "日" + hour + "时" + minute + "分" ;
	
		return expendTime;
	}

}
