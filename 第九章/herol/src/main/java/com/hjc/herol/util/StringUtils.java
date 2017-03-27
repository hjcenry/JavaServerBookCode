package com.hjc.herol.util;

public class StringUtils {

	public static String getCutString(String str){
	    return getCutString(str,10);
	}
	public static String getCutString(String str,int len){
		if(len<=1) len=1;
	    if(null ==str){
	    	return "";
	    }
	    if(str.length()<=len){
	    	return str;
	    }else{
	    	return str.subSequence(0, len-1)+"...";
	    }
	}
}
