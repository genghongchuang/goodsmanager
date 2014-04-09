package com.geng.goodsmanage.utils;

import java.sql.Time;

import org.apache.log4j.Logger;

public class DoubleUtil {
	private static Logger logger=Logger.getLogger(DoubleUtil.class);
	/**
	 * 
	* @Title: tayste 
	* @Description: TODO(把数字转化为两位小数) 
	* @param number
	* @return     
	* @throws
	 */
	public static double tayste(double number){
		 java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");
		    String num=df.format(number);
		    double n=Double.valueOf(num);
		    return n;
		    
	}

}
