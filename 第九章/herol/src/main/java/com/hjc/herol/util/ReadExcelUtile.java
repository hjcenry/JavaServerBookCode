package com.hjc.herol.util;

/**
 * 
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author songtao
 *
 */
public class ReadExcelUtile {
	public List<List<String[]>> readEXCl(MultipartFile file ,String fileType){
		WorkbookFactory factory = new WorkbookFactory();
		List<List<String[]>> listResult = new ArrayList<List<String[]>>();
		Map map = null;
		try {
			Workbook workBook= factory.create(file,fileType);
			
			ExcelReadUtil util = new ExcelReadUtil();
			map = util.readSheet(workBook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set set = map.keySet();
		for(Iterator it=set.iterator();it.hasNext();){
			String key = (String)it.next();
			List<String[]> list = (List<String[]>)map.get(key);
			listResult.add(list);
		}
		return listResult;
	}

}
