package com.hjc.herol.util.sensitive;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 敏感词过滤
 */
public class SensitiveFilter {

	private Map sensitiveWordMap = null;

	// 最小匹配规则
	public static int minMatchTYpe = 1;

	// 最大匹配规则
	public static int maxMatchType = 2;

	private List<String> sensitiveWord;
	private List<String> illegalityName;

	// 单例
	public static SensitiveFilter instance;

	/**
	 * 构造函数，初始化敏感词库
	 */
	private SensitiveFilter() {
		initKeyWord();
	}

	public static SensitiveFilter getInstance() {
		if (null == instance) {
			instance = new SensitiveFilter();
		}
		return instance;
	}

	public void initKeyWord() {
		initSensitiveWordAndIllegalityName();
		List<String> list = this.getSensitiveWord();
		// 读取敏感词库
		Set<String> wordSet = new HashSet<String>();
		wordSet.addAll(list);
		// 将敏感词库加入到HashMap中
		sensitiveWordMap = addSensitiveWordToHashMap(wordSet);
	}

	private Map addSensitiveWordToHashMap(Set<String> wordSet) {
		// 初始化敏感词容器，减少扩容操作
		Map wordMap = new HashMap(wordSet.size());
		for (String word : wordSet) {
			Map nowMap = wordMap;
			for (int i = 0; i < word.length(); i++) {

				// 转换成char型
				char keyChar = word.charAt(i);

				// 获取
				Object tempMap = nowMap.get(keyChar);

				// 如果存在该key，直接赋值
				if (tempMap != null) {
					nowMap = (Map) tempMap;
				}

				// 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
				else {

					// 设置标志位
					Map<String, String> newMap = new HashMap<String, String>();
					newMap.put("isEnd", "0");

					// 添加到集合
					nowMap.put(keyChar, newMap);
					nowMap = newMap;
				}

				// 最后一个
				if (i == word.length() - 1) {
					nowMap.put("isEnd", "1");
				}
			}
		}

		return wordMap;
	}

	/**
	 * 判断文字是否包含敏感字符
	 * 
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public boolean isContaintSensitiveWord(String txt, int matchType) {
		boolean flag = false;
		for (int i = 0; i < txt.length(); i++) {

			// 判断是否包含敏感字符
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType);

			// 大于0存在，返回true
			if (matchFlag > 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取文字中的敏感词
	 * 
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public Set<String> getSensitiveWord(String txt, int matchType) {
		Set<String> sensitiveWordList = new HashSet<String>();

		for (int i = 0; i < txt.length(); i++) {

			// 判断是否包含敏感字符
			int length = CheckSensitiveWord(txt, i, matchType);

			// 存在,加入list中
			if (length > 0) {
				sensitiveWordList.add(txt.substring(i, i + length));

				// 减1的原因，是因为for会自增
				i = i + length - 1;
			}
		}

		return sensitiveWordList;
	}

	/**
	 * 替换敏感字字符
	 * 
	 * @param txt
	 * @param matchType
	 * @param replaceChar
	 * @return
	 */
	public String replaceSensitiveWord(String txt, int matchType,
			String replaceChar) {

		String resultTxt = txt;

		// 获取所有的敏感词
		Set<String> set = getSensitiveWord(txt, matchType);
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}

		return resultTxt;
	}

	/**
	 * 获取替换字符串
	 * 
	 * @param replaceChar
	 * @param length
	 * @return
	 */
	private String getReplaceChars(String replaceChar, int length) {
		String resultReplace = replaceChar;
		for (int i = 1; i < length; i++) {
			resultReplace += replaceChar;
		}
		return resultReplace;
	}

	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 * 如果存在，则返回敏感词字符的长度，不存在返回0
	 * 
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return
	 */
	public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {

		// 敏感词结束标识位：用于敏感词只有1位的情况
		boolean flag = false;

		// 匹配标识数默认为0
		int matchFlag = 0;
		Map nowMap = sensitiveWordMap;
		for (int i = beginIndex; i < txt.length(); i++) {
			char word = txt.charAt(i);

			// 获取指定key
			nowMap = (Map) nowMap.get(word);

			// 存在，则判断是否为最后一个
			if (nowMap != null) {

				// 找到相应key，匹配标识+1
				matchFlag++;

				// 如果为最后一个匹配规则,结束循环，返回匹配标识数
				if ("1".equals(nowMap.get("isEnd"))) {

					// 结束标志位为true
					flag = true;

					// 最小规则，直接返回,最大规则还需继续查找
					if (SensitiveFilter.minMatchTYpe == matchType) {
						break;
					}
				}
			}

			// 不存在，直接返回
			else {
				break;
			}
		}

		// 长度必须大于等于1，为词
		if (matchFlag < 2 || !flag) {
			matchFlag = 0;
		}
		return matchFlag;
	}

	private void initSensitiveWordAndIllegalityName() {
		SAXReader reader = new SAXReader();
		Document document = null;
		String headPath = SensitiveFilter.class.getClassLoader()
				.getResource("/").getPath();
		try {
			document = reader.read(new File(headPath + "/syspara.xml"));
			Element rootEle = document.getRootElement();
			Iterator<Element> iterator = rootEle
					.elementIterator("IllegalityName");
			List<String> illegalityName = new ArrayList<String>();
			while (iterator.hasNext()) {
				Element illegalEle = iterator.next();
				String value = illegalEle.attributeValue("word");
				illegalityName.add(value);
			}
			this.illegalityName = illegalityName;

			List<String> sensitiveWord = new ArrayList<String>();
			iterator = rootEle.elementIterator("SensitiveWord");
			while (iterator.hasNext()) {
				Element sensitiveEle = iterator.next();
				String value = sensitiveEle.attributeValue("word");
				sensitiveWord.add(value);
			}
			this.sensitiveWord = sensitiveWord;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public List<String> getSensitiveWord() {
		return sensitiveWord;
	}

	public List<String> getIllegalityName() {
		return illegalityName;
	}
}
