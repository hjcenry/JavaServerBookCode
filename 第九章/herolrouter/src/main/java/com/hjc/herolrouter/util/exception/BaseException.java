/**
 * 
 */
package com.hjc.herolrouter.util.exception;


/**
 * copy from ManuAppException 异常处理类
 * 
 * @description:
 * @copyright:
 * @author: superwang
 * @date:
 */
public class BaseException extends RuntimeException {

	private int errCode = -10001;// 异常代号
	private String errMsg = null;
	public BaseException(){
		super();
	}
	public BaseException(String msg) {
		super(msg);
		this.errMsg = msg;
	}

	public BaseException(Throwable cause) {
		super(null, cause);
	}

	public BaseException(int errCode, String msg) {
		super(msg);
		this.errCode = errCode;
		this.errMsg = msg;		
	}

	public BaseException(int errCode, Throwable cause) {
		super(null, cause);
		this.errCode = errCode;
	}

	public void setMessage(String message){
		this.errMsg = message;
	}
	public int getErrCode() {
		return errCode;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getMessage(){
		String tmp = this.errMsg;
		if (tmp==null && this.getCause()!=null){
			tmp = this.getCause().getMessage();
		}
		return tmp;
	}
	public String toString(){
		if (errMsg!=null){
			return errMsg;
		}else{
			return super.toString();
		}
	}

}
