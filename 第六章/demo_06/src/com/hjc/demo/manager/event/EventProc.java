package com.hjc.demo.manager.event;

/** 
* @ClassName: EventProc 
* @Description: 事件处理器
* @author 何金成
* @date 2016年5月2日 下午8:58:01 
*  
*/
public abstract class EventProc {
	public boolean disable;

	public EventProc() {
		doReg();
	}

	public abstract void proc(Event param);

	protected abstract void doReg();
}
