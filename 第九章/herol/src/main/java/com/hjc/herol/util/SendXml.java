package com.hjc.herol.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendXml {

	public static String sendXml(String sendXml) throws Exception{
		//测试
			//String url2 = "http://211.148.166.71:8080/stariboss-selfservice_proxy/stbSelfService";
			//生产
			String url2 = "http://211.148.166.71:8080/ccintfms/stbSelfService";
			System.out.println("========================"+url2+"============================");
			
			
	        URL getUrl = new URL(url2);

	        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-type","text/xml");
	        connection.setRequestProperty("Accept","text/xml");
	        connection.setRequestProperty("User-Agent","JAX-WS RI 2.1.3-hudson-390-");
	        //String sendxml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.stb.sms.star.com\"><soapenv:Header/><soapenv:Body><ser:checkCustomerCodeAndPassword><ser:in0>"+cusCode+"</ser:in0><ser:in1>"+cuspassword+"</ser:in1></ser:checkCustomerCodeAndPassword></soapenv:Body></soapenv:Envelope>";
	        String send = sendXml;
	        //String send="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body><ns1:getUserLink xmlns:ns1=\"http://www.hua-xia.com.cn/ZySearch\"><linkNo xmlns=\"\">"+par+"</linkNo></ns1:getUserLink></soap:Body></soap:Envelope>";
	        connection.getOutputStream().write(send.getBytes());
	        connection.getOutputStream().flush();
	        connection.getOutputStream().close();
	        connection.connect();
	        
	        BufferedReader reader = null;
	        
	        int returnCode = connection.getResponseCode();  
	        if (returnCode == 200) {  
	             //正确调用  
	        	 reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
	        } else if (returnCode == 500) {  
	        	reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(),"utf-8"));
	        } 
	        	        
	        // 取得输入流，并使用Reader读取
	       
	        String i = reader.toString();
	        System.out.println(i);
	        String lines="";
	        String results = "";
	        while ((lines = reader.readLine()) != null){
	                //lines = new String(lines.getBhqytes(), "utf-8");
	        	results+=lines.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
	        }
	        reader.close();
	        connection.disconnect();
	        return results;
	        
	}
	
	
	public static String sendXml0(String sendXml) throws Exception{
		//测试
			//String url2 = "http://211.148.166.71:8080/stariboss-selfservice_proxy/stbSelfService";
			//生产
			String url2 = "http://192.168.6.249:8080/ccintfms/BusinessOperateService";
			System.out.println("========================"+url2+"============================");
			
			
	        URL getUrl = new URL(url2);

	        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-type","text/xml");
	        connection.setRequestProperty("Accept","text/xml");
	        connection.setRequestProperty("User-Agent","JAX-WS RI 2.1.3-hudson-390-");
	        //String sendxml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.stb.sms.star.com\"><soapenv:Header/><soapenv:Body><ser:checkCustomerCodeAndPassword><ser:in0>"+cusCode+"</ser:in0><ser:in1>"+cuspassword+"</ser:in1></ser:checkCustomerCodeAndPassword></soapenv:Body></soapenv:Envelope>";
	        String send = sendXml;
	        //String send="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body><ns1:getUserLink xmlns:ns1=\"http://www.hua-xia.com.cn/ZySearch\"><linkNo xmlns=\"\">"+par+"</linkNo></ns1:getUserLink></soap:Body></soap:Envelope>";
	        connection.getOutputStream().write(send.getBytes());
	        connection.getOutputStream().flush();
	        connection.getOutputStream().close();
	        connection.connect();
	        
	        BufferedReader reader = null;
	        
	        int returnCode = connection.getResponseCode();  
	        if (returnCode == 200) {  
	             //正确调用  
	        	 reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
	        } else if (returnCode == 500) {  
	        	reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(),"utf-8"));
	        } 
	        	        
	        // 取得输入流，并使用Reader读取
	       
	        String i = reader.toString();
	        System.out.println(i);
	        String lines="";
	        String results = "";
	        while ((lines = reader.readLine()) != null){
	                //lines = new String(lines.getBhqytes(), "utf-8");
	        	results+=lines.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
	        }
	        reader.close();
	        connection.disconnect();
	        return results;
	        
	}
	
	
	public static void main(String args[]){
		
		String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.stb.sms.star.com\"><soapenv:Header/><soapenv:Body><ser:checkCustomerCodeAndPassword><ser:in0>test</ser:in0><ser:in1>111</ser:in1></ser:checkCustomerCodeAndPassword></soapenv:Body></soapenv:Envelope>";
		try {
			System.out.println(SendXml.sendXml(xml));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
