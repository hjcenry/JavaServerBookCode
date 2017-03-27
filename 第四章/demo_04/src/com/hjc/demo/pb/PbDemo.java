package com.hjc.demo.pb;

import com.hjc.demo.pb.DemoProto.Obj1;
import com.hjc.demo.pb.DemoProto.Obj2;

public class PbDemo {

	public static void main(String[] args) {
		PbDemo demo = new PbDemo();
		demo.run();
	}

	public void run() {
		// 构建Protocol Javabean
		Obj1.Builder obj1 = Obj1.newBuilder();
		obj1.setVal1(1);
		Obj2.Builder obj21 = Obj2.newBuilder();
		obj21.setSubval1("protocol buffer demo 1");
		obj21.setSubval2(1.9999d);
		obj1.addVal2(obj21);
		Obj2.Builder obj22 = Obj2.newBuilder();
		obj22.setSubval1("protocol buffer demo 2");
		obj22.setSubval2(1.9999d);
		obj1.addVal2(obj22);
		System.out.println(obj1);
		System.out.println(obj1.build());
		// 接收到的proto直接对应Protocol Javabean
		System.out.println("接收到的proto直接对应Protocol Javabean");
		System.out.println("val1:" + obj1.getVal1());
		System.out.println("val2:" + obj1.getVal2List());
	}

}
