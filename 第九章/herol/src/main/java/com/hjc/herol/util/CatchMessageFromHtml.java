package com.hjc.herol.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class CatchMessageFromHtml {

	
	//获取标题
	public static String getTitle(String url) throws IOException{
		String title= "";
		Document doc = Jsoup.connect(url).timeout(1000).get();
		Element body = doc.body();
		Elements el=body.select("h1");
		Iterator it = el.iterator();
		
		while(it.hasNext()){
	    	 
	    	 Element e = (Element) it.next();
	    	 title = e.html();
	    	 System.out.println(title);
	     }
		return title;
	}
	
	// 获取内容
	public static String getContext(String url) throws IOException {

		String context = "";
		Document doc = Jsoup.connect(url).timeout(1000).get();
		Element body = doc.body();
		Elements el = body.select("p");
		Iterator it = el.iterator();
		String[] s = new String[100];
		int i = 0;
		while (it.hasNext()) {
			Element e = (Element) it.next();
			s[i] = e.text();
			System.out.println(s[i]);
			context = context+s[i]+"\r\n";
			i++;
		}
		
		context = context.substring(0, context.length()-18);
		System.out.println(context);
		return context;
	}
	
	
	// 获取图片
	public static List<String> getImg(String url) throws IOException {

		List<String> imgUrl = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(1000).get();
		Element body = doc.body();
		Elements el = body.select("img");
		Iterator it = el.iterator();
		String[] s = new String[100];
		int i = 0;
		while (it.hasNext()) {
			Element e = (Element) it.next();
			s[i]=e.attr("src").toString();
			System.out.println(s[i]);
			imgUrl.add(s[i]);
			i++;

		}
		return imgUrl;
	}
	
	
}
