package com.hjc.herolpvp.util.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * @类名: XmlParser
 * @类功能描述: 解析xml
 * @时间 ：2011-4-13 上午08:33:50
 */
public class XmlParser {
	/**
	 * 
	 * @方法功能描述：生成空的xml文件头
	 * @方法名:createEmptyXmlFile
	 * @param xmlPath
	 * @返回类型：Document
	 * @时间：2011-4-14下午12:44:20
	 */
	public static Document createEmptyXmlFile(String xmlPath) {
		if (xmlPath == null || xmlPath.equals(""))
			return null;

		XMLWriter output;
		Document document = DocumentHelper.createDocument();

		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			output = new XMLWriter(new FileWriter(xmlPath), format);
			output.write(document);
			output.close();
		} catch (IOException e) {
			return null;
		}
		return document;
	}

	/**
	 * 根据xml文件路径取得document对象
	 * 
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 */
	public static Document getDocument(String xmlPath) {
		if (xmlPath == null || xmlPath.equals(""))
			return null;

		File file = new File(xmlPath);
		if (file.exists() == false) {
			return createEmptyXmlFile(xmlPath);
		}

		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(xmlPath);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 
	 * @方法功能描述：得到根节点
	 * @方法名:getRootEleme
	 * @param DOC对象
	 * @返回类型：Element
	 * @时间：2011-4-8下午12:54:02
	 */
	public static Element getRootNode(Document document) {
		if (document == null)
			return null;

		Element root = document.getRootElement();
		return root;
	}

	/**
	 * 
	 * @方法功能描述: 根据路径直接拿到根节点
	 * @方法名:getRootElement
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 *             @参数描述 :
	 * @返回类型：Element
	 * @时间：2011-4-14下午03:01:14
	 */
	public static Element getRootNode(String xmlPath) {
		if (xmlPath == null || (xmlPath.trim()).equals(""))
			return null;
		Document document = getDocument(xmlPath);
		if (document == null)
			return null;
		return getRootNode(document);
	}

	/**
	 * 
	 * @方法功能描述:得到指定元素的迭代器
	 * @方法名:getIterator
	 * @param parent
	 * @返回类型：Iterator<Element>
	 * @时间：2011-4-14上午11:29:18
	 */
	@SuppressWarnings("unchecked")
	public static Iterator<Element> getIterator(Element parent) {
		if (parent == null)
			return null;
		Iterator<Element> iterator = parent.elementIterator();
		return iterator;
	}

	/**
	 * 
	 * @方法功能描述: 根据子节点名称得到指定的子节点
	 * @方法名:getChildElement
	 * @param parent
	 * @param childName
	 * @返回类型：Element
	 * @时间：2011-4-14上午11:18:03
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getChildElements(Element parent,
			String childName) {
		childName = childName.trim();
		if (parent == null)
			return null;
		childName += "//";
		List<Element> childElements = parent.selectNodes(childName);
		return childElements;
	}

	/**
	 * 
	 * @方法功能描述：TODO
	 * @方法名:getChildList
	 * @param node
	 * @return @参数描述 :
	 * @返回类型：List<Element>
	 * @时间：2011-4-14下午12:21:52
	 */
	public static List<Element> getChildList(Element node) {
		if (node == null)
			return null;
		Iterator<Element> itr = getIterator(node);
		if (itr == null)
			return null;
		List<Element> childList = new ArrayList<Element>();
		while (itr.hasNext()) {
			Element kidElement = itr.next();
			if (kidElement != null) {
				childList.add(kidElement);
			}
		}
		return childList;
	}

	/**
	 * 
	 * @方法功能描述 : 查询没有子节点的节点，使用xpath方式
	 * @方法名:getSingleNode
	 * @param parent
	 * @param nodeNodeName
	 * @return @参数描述 : 父节点，子节点名称
	 * @返回类型：Node
	 * @时间：2011-4-14下午12:38:25
	 */
	public static Node getSingleNode(Element parent, String nodeNodeName) {
		nodeNodeName = nodeNodeName.trim();
		String xpath = "//";
		if (parent == null)
			return null;
		if (nodeNodeName == null || nodeNodeName.equals(""))
			return null;
		xpath += nodeNodeName;
		Node kid = parent.selectSingleNode(xpath);
		return kid;
	}

	/**
	 * 
	 * @方法功能描述：得到子节点，不使用xpath
	 * @方法名:getChild
	 * @param parent
	 * @param childName
	 * @return @参数描述 :
	 * @返回类型：Element
	 * @时间：2011-4-14下午12:53:22
	 */
	@SuppressWarnings("rawtypes")
	public static Element getChild(Element parent, String childName) {
		childName = childName.trim();
		if (parent == null)
			return null;
		if (childName == null || childName.equals(""))
			return null;
		Element e = null;
		Iterator it = getIterator(parent);
		while (it != null && it.hasNext()) {
			Element k = (Element) it.next();
			if (k == null)
				continue;
			if (k.getName().equalsIgnoreCase(childName)) {
				e = k;
				break;
			}
		}
		return e;
	}

	/**
	 * 
	 * @方法功能描述：判断节点是否还有子节点
	 * @方法名:hasChild
	 * @param e
	 * @返回类型：boolean
	 * @时间：2011-4-14下午01:43:48
	 */
	public static boolean hasChild(Element e) {
		if (e == null)
			return false;
		return e.hasContent();
	}

	/**
	 * 
	 * @方法功能描述：得到指定节点的属性的迭代器
	 * @方法名:getAttrIterator
	 * @param e
	 * @返回类型：Iterator<Attribute>
	 * @时间：2011-4-14下午01:42:38
	 */
	@SuppressWarnings("unchecked")
	public static Iterator<Attribute> getAttrIterator(Element e) {
		if (e == null)
			return null;
		Iterator<Attribute> attrIterator = e.attributeIterator();
		return attrIterator;
	}

	/**
	 * 
	 * @方法功能描述：遍历指定节点的所有属性
	 * @方法名:getAttributeList
	 * @param e
	 * @return 节点属性的list集合
	 * @返回类型：List<Attribute>
	 * @时间：2011-4-14下午01:41:38
	 */
	public static List<Attribute> getAttributeList(Element e) {
		if (e == null)
			return null;
		List<Attribute> attributeList = new ArrayList<Attribute>();
		Iterator<Attribute> atrIterator = getAttrIterator(e);
		if (atrIterator == null)
			return null;
		while (atrIterator.hasNext()) {
			Attribute attribute = atrIterator.next();
			attributeList.add(attribute);
		}
		return attributeList;
	}

	/**
	 * 
	 * @方法功能描述： 得到指定节点的指定属性
	 * @方法名:getAttribute
	 * @param element
	 *            指定的元素
	 * @param attrName
	 *            属性名称
	 * @return Attribute
	 * @返回类型：Attribute
	 * @时间：2011-4-14下午01:45:27
	 */
	public static Attribute getAttribute(Element element, String attrName) {
		attrName = attrName.trim();
		if (element == null)
			return null;
		if (attrName == null || attrName.equals(""))
			return null;
		Attribute attribute = element.attribute(attrName);
		return attribute;
	}

	/**
	 * 
	 * @方法功能描述:获取指定节点指定属性的值
	 * @方法名:attrValue
	 * @param e
	 * @param attrName
	 * @返回类型：String
	 * @时间：2011-4-14下午02:36:48
	 */
	public static String attrValue(Element e, String attrName) {
		attrName = attrName.trim();
		if (e == null)
			return null;
		if (attrName == null || attrName.equals(""))
			return null;
		return e.attributeValue(attrName);
	}

	/**
	 * 
	 * @方法功能描述：得到指定节点的所有属性及属性值
	 * @方法名:getNodeAttrMap
	 * @return 属性集合
	 * @返回类型：Map<String,String>
	 * @时间：2011-4-15上午10:00:26
	 */
	public static Map<String, String> getNodeAttrMap(Element e) {
		Map<String, String> attrMap = new HashMap<String, String>();
		if (e == null) {
			return null;
		}
		List<Attribute> attributes = getAttributeList(e);
		if (attributes == null) {
			return null;
		}
		for (Attribute attribute : attributes) {
			String attrValueString = attrValue(e, attribute.getName());
			attrMap.put(attribute.getName(), attrValueString);
		}
		return attrMap;
	}

	/**
	 * 
	 * @方法功能描述: 遍历指定节点的下没有子节点的元素的text值
	 * @方法名:getSingleNodeText
	 * @param e
	 * @return @参数描述 :
	 * @返回类型：Map<String,String>
	 * @时间：2011-4-15下午12:24:38
	 */
	public static Map<String, String> getSingleNodeText(Element e) {
		Map<String, String> map = new HashMap<String, String>();
		if (e == null)
			return null;
		List<Element> kids = getChildList(e);
		for (Element e2 : kids) {
			if (e2.getTextTrim() != null) {
				map.put(e2.getName(), e2.getTextTrim());
			}
		}
		return map;
	}

	/**
	 * 
	 * @方法功能描述：遍历根节点下，没有子节点的元素节点，并将此节点的text值放入map中返回
	 * @方法名:getSingleNodeText
	 * @param xmlFilePath
	 * @return @参数描述 :
	 * @返回类型：Map<String,String>
	 * @时间：2011-4-15下午12:23:30
	 */
	public static Map<String, String> getSingleNodeText(String xmlFilePath) {
		xmlFilePath = xmlFilePath.trim();
		if (xmlFilePath == null || xmlFilePath.equals("")) {
			return null;
		}
		Element rootElement = getRootNode(xmlFilePath);
		if (rootElement == null || !hasChild(rootElement)) {
			return null;
		}
		return getSingleNodeText(rootElement);
	}

	/**
	 * 
	 * @方法功能描述:根据xml路径和指定的节点的名称，得到指定节点,从根节点开始找
	 * @方法名:getNameNode
	 * @param xmlFilePath
	 * @param tagName
	 * @param flag
	 *            : 指定元素的个数
	 * @返回类型：Element 指定的节点
	 * @时间：2011-4-15下午12:22:35
	 * 
	 */

	public enum Flag {
		one, more
	}

	@SuppressWarnings("unchecked")
	public static <T> T getNameNode(String xmlFilePath, String tagName,
			Flag flag) {
		xmlFilePath = xmlFilePath.trim();
		tagName = tagName.trim();
		if (xmlFilePath == null || tagName == null || xmlFilePath.equals("")
				|| tagName.equals(""))
			return null;
		Element rootElement = getRootNode(xmlFilePath);
		if (rootElement == null)
			return null;
		List<Element> tagElementList = getNameElement(rootElement, tagName);
		if (tagElementList == null)
			return null;
		switch (flag) {
		case one:
			return (T) tagElementList.get(0);
		}
		return (T) tagElementList;
	}

	/**
	 * 
	 * @方法功能描述:得到指定节点下所有子节点的属性集合
	 * @方法名:getNameNodeAllAttributeMap
	 * @param e
	 * @return @参数描述 :
	 * @返回类型：Map<Integer,Object>
	 * @时间：2011-4-18下午04:40:14
	 */
	public static Map<Integer, Object> getNameNodeAllKidsAttributeMap(
			Element parent) {
		Map<Integer, Object> allAttrMap = new HashMap<Integer, Object>();
		if (parent == null)
			return null;
		List<Element> childlElements = getChildList(parent);
		if (childlElements == null)
			return null;
		for (int i = 0; i < childlElements.size(); i++) {
			Element childElement = childlElements.get(i);
			Map<String, String> attrMap = getNodeAttrMap(childElement);
			allAttrMap.put(i, attrMap);
		}
		return allAttrMap;
	}

	/**
	 * 
	 * @方法功能描述:根据xml文件名路径和指定的节点名称得到指定节点所有子节点的所有属性集合
	 * @方法名:getNameNodeAllAttributeMap
	 * @param xmlFileName
	 * @param nodeName
	 * @return @参数描述 :
	 * @返回类型：Map<Integer,Object>
	 * @时间：2011-4-18下午04:51:46
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getNameNodeAllAttributeMap(String xmlFilePath,
			String nodeName, Flag flag) {
		nodeName = nodeName.trim();
		Map<String, String> allAttrMap = null;
		Map<Integer, Map<String, String>> mostKidsAllAttriMap = new HashMap<Integer, Map<String, String>>();
		if (xmlFilePath == null || nodeName == null || xmlFilePath.equals("")
				|| nodeName.equals(""))
			return null;
		switch (flag) {
		case one:
			Element nameNode = getNameNode(xmlFilePath, nodeName, Flag.one);
			allAttrMap = getNodeAttrMap(nameNode);
			return (T) allAttrMap;
		case more:
			List<Element> nameKidsElements = getNameNode(xmlFilePath, nodeName,
					Flag.more);
			for (int i = 0; i < nameKidsElements.size(); i++) {
				Element kid = nameKidsElements.get(i);
				allAttrMap = getNodeAttrMap(kid);
				mostKidsAllAttriMap.put(i, allAttrMap);
			}
			return (T) mostKidsAllAttriMap;
		}
		return null;
	}

	/**
	 * 
	 * @方法功能描述:遍历指定的节点下所有的节点
	 * @方法名:ransack
	 * @param element
	 *            @参数描述 :
	 * @返回类型：void
	 * @时间：2011-4-18下午05:25:41
	 */
	public static List<Element> ransack(Element element,
			List<Element> allkidsList) {
		if (element == null)
			return null;
		if (hasChild(element)) {
			List<Element> kids = getChildList(element);
			for (Element e : kids) {
				allkidsList.add(e);
				ransack(e, allkidsList);
			}
		}
		return allkidsList;
	}

	/**
	 * 
	 * @方法功能描述:得到指定节点下的指定节点集合
	 * @方法名:getNameElement
	 * @param element
	 * @param nodeName
	 * @return @参数描述 :
	 * @返回类型：Element
	 * @时间：2011-4-18下午06:18:56
	 */
	public static List<Element> getNameElement(Element element, String nodeName) {
		nodeName = nodeName.trim();
		List<Element> kidsElements = new ArrayList<Element>();
		if (element == null)
			return null;
		if (nodeName == null || nodeName.equals(""))
			return null;
		List<Element> allKids = ransack(element, new ArrayList<Element>());
		if (allKids == null)
			return null;
		for (int i = 0; i < allKids.size(); i++) {
			Element kid = allKids.get(i);
			if (nodeName.equals(kid.getName()))
				kidsElements.add(kid);
		}
		return kidsElements;
	}

	/**
	 * 
	 * @方法功能描述:验证节点是否唯一
	 * @方法名:validateSingle
	 * @param element
	 * @返回类型：int 节点唯一返回1,节点不唯一返回大于一的整型数据
	 * @时间：2011-4-20下午04:36:22
	 */
	public static int validateSingle(Element element) {
		int j = 1;
		if (element == null)
			return j;
		Element parent = element.getParent();
		List<Element> kids = getChildList(parent);
		for (Element kid : kids) {
			if (element.equals(kid))
				j++;
		}
		return j;
	}
}