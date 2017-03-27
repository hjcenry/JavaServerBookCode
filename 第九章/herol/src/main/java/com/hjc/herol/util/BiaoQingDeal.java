package com.hjc.herol.util;

import javax.servlet.http.HttpServletRequest;


/*
 * 用于表情处理的类
 * */

public class BiaoQingDeal {
	
	public static String dealQQBiaoqing(String context,HttpServletRequest request){
		//第一页开始
		context = context.replace("[呲牙]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/13.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[调皮]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/12.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[流汗]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/27.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[偷笑]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/20.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[再见]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/39.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[敲打]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/38.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[擦汗]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/40.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[猪头]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/62.gif' style='width:20px;height:20px;'></img>");
		
		context = context.replace("[玫瑰]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/63.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[流泪]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/5.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[大哭]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/9.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[嘘]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/33.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[酷]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/4.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[抓狂]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/18.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[委屈]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/49.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[便便]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/74.gif' style='width:20px;height:20px;'></img>");
		
		context = context.replace("[炸弹]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/70.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[菜刀]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/55.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[可爱]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/21.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[色]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/2.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[害羞]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/6.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[得意]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/4.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[吐]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/19.gif' style='width:20px;height:20px;'></img>");
		
		//第二页开始
		context = context.replace("[微笑]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/0.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[发怒]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/11.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[尴尬]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/10.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[惊恐]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/26.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[冷汗]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/17.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[爱心]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/66.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[示爱]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/65.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[白眼]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/22.gif' style='width:20px;height:20px;'></img>");
		
		context = context.replace("[傲慢]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/23.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[难过]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/50.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[惊讶]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/14.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[疑问]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/32.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[睡]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/8.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[亲亲]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/52.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[憨笑]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/28.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[爱情]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/90.gif' style='width:20px;height:20px;'></img>");
		
		context = context.replace("[衰]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/36.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[撇嘴]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/1.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[阴险]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/51.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[奋斗]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/30.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[发呆]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/53.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[右哼哼]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/46.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[拥抱]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/78.gif' style='width:20px;height:20px;'></img>");
		//第三页开始
		context = context.replace("[坏笑]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/44.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[飞吻]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/91.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[鄙视]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/48.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[晕]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/34.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[大兵]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/29.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[可怜]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/54.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[强]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/79.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[弱]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/80.gif' style='width:20px;height:20px;'></img>");
		
		context = context.replace("[握手]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/81.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[胜利]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/82.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[抱拳]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/83.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[凋谢]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/64.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[饭]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/61.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[蛋糕]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/68.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[西瓜]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/56.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[啤酒]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/57.gif' style='width:20px;height:20px;'></img>");
		
		context = context.replace("[瓢虫]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/73.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[勾引]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/84.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[OK]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/89.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[爱您]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/87.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[咖啡]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/60.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[月亮]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/75.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[刀]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/71.gif' style='width:20px;height:20px;'></img>");
		//第四页开始
		context = context.replace("[发抖]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/93.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[差劲]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/86.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[拳头]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/85.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[心碎]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/67.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[太阳]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/76.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[礼物]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/77.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[足球]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/72.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[骷髅]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/37.gif' style='width:20px;height:20px;'></img>");
		
		context = context.replace("[挥手]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/99.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[闪电]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/69.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[饥饿]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/24.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[困]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/25.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[咒骂]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/31.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[折磨]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/35.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[抠鼻]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/41.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[鼓掌]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/42.gif' style='width:20px;height:20px;'></img>");
		
		context = context.replace("[溴大了]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/43.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[左哼哼]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/45.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[哈欠]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/47.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[快哭了]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/50.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[吓]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/53.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[篮球]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/58.gif' style='width:20px;height:20px;'></img>");
		context = context.replace("[乒乓]", "<img src='"+request.getContextPath()+"/images/qqbiaoqing/59.gif' style='width:20px;height:20px;'></img>");

		return context;
	}


}
