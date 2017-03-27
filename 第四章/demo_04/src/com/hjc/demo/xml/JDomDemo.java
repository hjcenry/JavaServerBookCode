package com.hjc.demo.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * 
 * JDOM 生成与解析XML文档
 * 
 */
public class JDomDemo {

	public static void main(String[] args) {
		JDomDemo demo = new JDomDemo();
		demo.createXml("D://jdom.xml");
		demo.parserXml("D://jdom.xml");
	}

	public void createXml(String fileName) {
		Element root = new Element("rootElements");
		Document document = new Document(root);
		Element val1 = new Element("val1");
		val1.setText("10000000000000");
		root.addContent(val1);
		Element val2 = new Element("val2");
		val2.setText("1.00000000000001");
		root.addContent(val2);
		Element subElement = new Element("subElement");
		Element subval1 = new Element("subval1");
		subval1.setText("1000");
		subElement.addContent(subval1);
		Element subval2 = new Element("subval2");
		subval2.setText("aaaaaaaaaa");
		subElement.addContent(subval2);
		root.addContent(subElement);
		XMLOutputter XMLOut = new XMLOutputter();
		try {
			XMLOut.output(document, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parserXml(String fileName) {
		SAXBuilder builder = new SAXBuilder(false);
		try {
			Document document = builder.build(fileName);
			Element root = document.getRootElement();
			List vals = root.getChildren();
			System.out.println((root).getName());
			for (int i = 0; i < vals.size(); i++) {
				Element val = (Element) vals.get(i);
				List subval = val.getChildren();
				if (subval.size() == 0) {
					System.out.println((val).getName() + ":" + val.getValue());
				}
				for (int j = 0; j < subval.size(); j++) {
					System.out.println(((Element) subval.get(j)).getName()
							+ ":" + ((Element) subval.get(j)).getValue());
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}