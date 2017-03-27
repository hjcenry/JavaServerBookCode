package com.hjc.herolrouter.client.rpc;

public class TestServiceImply implements TestService {
	public void testMethod(String msg, int code) {
		System.out.println("msg is " + msg + " ,code is " + code);
	}
}
