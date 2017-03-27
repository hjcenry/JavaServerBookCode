package com.hjc.herol.util.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

//JAVA 操作 excel 中的 .csv文件格式
public class CsvParser {
	private BufferedReader bufferedreader = null;
	private List list = new ArrayList();

	public CsvParser() {
	}

	public CsvParser(InputStream inStream) throws IOException {
		InputStreamReader isr = new InputStreamReader(inStream, "UTF-8");
		bufferedreader = new BufferedReader(isr);
		String stemp;
		while ((stemp = bufferedreader.readLine()) != null) {
			list.add(stemp);
		}
	}

	public List getList() throws IOException {
		return list;
	}
	
	public List getListWithNoHeader() throws IOException {
		return list.subList(2, list.size());
	}

	// 得到csv文件的行数
	public int getRowNum() {
		return list.size();
	}

	// 得到csv文件的列数
	public int getColNum() {
		if (!list.toString().equals("[]")) {
			if (list.get(0).toString().contains(",")) { // csv文件中，每列之间的是用','来分隔的
				return list.get(0).toString().split(",").length;
			} else if (list.get(0).toString().trim().length() != 0) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 取得指定行的值
	public String getRow(int index) {
		if (this.list.size() != 0)
			return (String) list.get(index);
		else
			return null;
	}

	// 取得指定列的值
	public String getCol(int index) {
		if (this.getColNum() == 0) {
			return null;
		}
		StringBuffer scol = new StringBuffer();
		String temp = null;
		int colnum = this.getColNum();
		if (colnum > 1) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp.split(",")[index] + ",");
			}
		} else {
			for (Iterator it = list.iterator(); it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp + ",");
			}
		}
		String str = new String(scol.toString());
		str = str.substring(0, str.length() - 1);
		return str;
	}

	// 取得指定行，指定列的值
	public String getString(int row, int col) {
		String temp = null;
		int colnum = this.getColNum();
		if (colnum > 1) {
			temp = list.get(row).toString().split(",")[col];
		} else if (colnum == 1) {
			temp = list.get(row).toString();
		} else {
			temp = null;
		}
		return temp;
	}

	public void CsvClose() throws IOException {
		this.bufferedreader.close();
	}

	public List readCvs(String filename) throws IOException {
		CsvParser cu = new CsvParser(new FileInputStream(new File(filename)));
		List list = cu.getList();

		return list;
	}

	public void createCsv(String biao, List list, String path)
			throws IOException {
		List tt = list;
		String data = "";
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String dateToday = dataFormat.format(today);
		File file = new File(path + "resource/expert/" + dateToday
				+ "importerrorinfo.csv");
		if (!file.exists())
			file.createNewFile();
		else
			file.delete();
		String str[];
		StringBuilder sb = new StringBuilder("");
		sb.append(biao);
		FileOutputStream writerStream = new FileOutputStream(file, true);
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
				writerStream, "UTF-8"));
		for (Iterator itt = tt.iterator(); itt.hasNext();) {
			String fileStr = itt.next().toString();
			// str = fileStr.split(",");
			// for (int i = 0; i <= str.length - 1; i++) { // 拆分成数组 用于插入数据库中
			// System.out.print("str[" + i + "]=" + str[i] + " ");
			// }
			// System.out.println("");
			sb.append(fileStr + "\r\n");
		}
		output.write(sb.toString());
		output.flush();
		output.close();
	}

	public static void main(String[] args) throws IOException {
		CsvParser test = new CsvParser();
		List aaa = test.readCvs("D:/abc.csv");
		for (int i = 0; i < aaa.size(); i++) {
			System.out.println(i + "---" + aaa.get(i));
			String a = (String) aaa.get(i).toString().split(",")[0];
		}
		System.out.println(aaa.size());
		//String biao = "用户名,姓名,排序,专家类型,业务专长,错误信息\n";
		//test.createCsv(biao, aaa, "D:/");
		// test.run("D:/abc.csv");
		// HttpServletResponse response = null;
		// test.createCsv(response);
//		CSVReader reader = new CSVReader(new FileReader("D:/abc.csv"));
//		ColumnPositionMappingStrategy<Test> strat = new ColumnPositionMappingStrategy<Test>();
//		strat.setType(Test.class);
//		String[] columns = new String[] {"EquipLv","Lv1","Lv2","Lv3","Lv4","Lv5","Lv6","Lv7","Lv8","Lv9","Lv10","Lv11","Lv12","Lv13","Lv14","Lv15"}; // the fields to bind do in your JavaBean
//		strat.setColumnMapping(columns);
//		CsvToBean<Test> csv = new CsvToBean<Test>();
//		List<Test> list = csv.parse(strat, reader);
//		System.out.println(list.size());
//		for (Test test : list) {
//			System.out.println(test.getEquipLv());
//			System.out.print(test.getLv1());
//			System.out.print(test.getLv2());
//			System.out.print(test.getLv3());
//			System.out.print(test.getLv4());
//			System.out.print(test.getLv5());
//			System.out.print(test.getLv6());
//			System.out.print(test.getLv7());
//			System.out.print(test.getLv8());
//			System.out.print(test.getLv9());
//			System.out.print(test.getLv10());
//			System.out.print(test.getLv11());
//			System.out.print(test.getLv12());
//			System.out.print(test.getLv13());
//			System.out.print(test.getLv14());
//			System.out.print(test.getLv15());
//		}
		
	}
}