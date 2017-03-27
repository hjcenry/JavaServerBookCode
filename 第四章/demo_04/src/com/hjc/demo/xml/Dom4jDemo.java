package com.hjc.demo.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jDemo {

	public static void main(String[] args) {
		Dom4jDemo demo = new Dom4jDemo();
		demo.createXml("D://dom4j.xml");
		demo.parserXml("D://dom4j.xml");
	}

	public void createXml(String fileName) {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("rootElement");
		Element val1 = rootElement.addElement("val1");
		val1.setText("10000000000000");
		Element val2 = rootElement.addElement("val2");
		val2.setText("1.00000000000001");
		Element subElement = rootElement.addElement("subElement");
		Element subval1 = subElement.addElement("subval1");
		subval1.setText("1000");
		Element subval2 = subElement.addElement("subval2");
		subval2.setText("aaaaaaaaa");
		try {
			Writer fileWriter = new FileWriter(fileName);
			XMLWriter xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void parserXml(String fileName) {
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element root = document.getRootElement();
			System.out.println(root.getName() + ":" + root.getText());
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element val = (Element) i.next();
				System.out.println(val.getName() + ":" + val.getText());
				for (Iterator j = val.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					System.out.println(node.getName() + ":" + node.getText());
				}
			}
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("dom4j parserXml");
	}
}
