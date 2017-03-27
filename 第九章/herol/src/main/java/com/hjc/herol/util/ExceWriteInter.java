package com.hjc.herol.util;


import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 写Excel文档
 * @author songhn
 *
 */
public interface ExceWriteInter {

	/**
	 * 创建Excel文档
	 * @param excelFilePath
	 */
	public void createExcel(Workbook workBook,List<String[]> list)throws Exception;
}