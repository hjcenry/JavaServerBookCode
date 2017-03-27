package com.hjc.demo.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hjc Unmarshaller的文档根元素
 * 
 */
@XmlRootElement
public class RootElement {
	private long val1;
	private double val2;
	private SubElement val3;

	public RootElement() {
		super();
	}

	public long getVal1() {
		return val1;
	}

	public void setVal1(long val1) {
		this.val1 = val1;
	}

	public double getVal2() {
		return val2;
	}

	public void setVal2(double val2) {
		this.val2 = val2;
	}

	public SubElement getVal3() {
		return val3;
	}

	public void setVal3(SubElement val3) {
		this.val3 = val3;
	}
}
