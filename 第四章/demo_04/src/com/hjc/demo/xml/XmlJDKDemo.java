package com.hjc.demo.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author hjc 使用jdk自带类Marshaller Unmarshaller 类使客户端应用程序能够将 XML 数据转换为 Java
 *         内容对象树。 Marshaller 类使客户端应用程序能够将 Java 内容树转换回 XML 数据。
 *
 */
public class XmlJDKDemo {

	public static void main(String[] args) {
		XmlJDKDemo demo = new XmlJDKDemo();
		try {
			demo.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() throws IOException {
		StringBuffer xmlBuffer = new StringBuffer();
		// Javabean to Xml
		try {
			RootElement root = new RootElement();
			root.setVal1(1000000000000000000l);
			root.setVal2(1.00000000000000001d);
			SubElement sub = new SubElement();
			sub.setSubval1(1000);
			sub.setSubval2("aaaaaaaaaaa");
			root.setVal3(sub);
			JAXBContext context = JAXBContext.newInstance(RootElement.class);
			Marshaller marshaller = context.createMarshaller();
			File xmlFile = new File("D://marshaller.xml");
			marshaller.marshal(root, xmlFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(xmlFile)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				xmlBuffer.append(line);
			}
			reader.close();
			System.out.println(xmlBuffer.toString());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		System.out.println("=================================");
		// Xml to Javabean
		try {
			JAXBContext context = JAXBContext.newInstance(RootElement.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			RootElement root = (RootElement) unmarshaller
					.unmarshal(new StringReader(xmlBuffer.toString()));
			System.out.println(root.getVal1());
			System.out.println(root.getVal2());
			System.out.println(root.getVal3().getSubval1());
			System.out.println(root.getVal3().getSubval2());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
