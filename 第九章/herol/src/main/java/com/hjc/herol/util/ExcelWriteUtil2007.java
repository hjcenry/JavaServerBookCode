package com.hjc.herol.util;

/**
 * 
 */

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author songhn
 *
 */
public class ExcelWriteUtil2007 implements ExceWriteInter {

	//@Override
	public void createExcel(Workbook workBook,List<String[]> list)throws Exception {
		
		XSSFSheet sheet = (XSSFSheet) workBook.createSheet();
		RichTextString richString = null;
		Font font = null;
		CellStyle style = null;
		for (int i = 0; i < list.size(); i++) {
			//设置表头
			if(i == 0){
				font = workBook.createFont();
				font.setFontHeightInPoints((short)12); //字体大小
			    font.setFontName("微软雅黑");
			    font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
			    font.setColor(HSSFColor.GREEN.index);    //绿字
				String[] values = (String[])list.get(i);
				XSSFRow row = (XSSFRow) sheet.createRow(i+1);
				style = workBook.createCellStyle();
				style.setFont(font);
				style.setBorderBottom(CellStyle.BORDER_DOUBLE);
				for (int j = 0; j < values.length; j++) {
					String string = values[j];
					XSSFCell cell = row.createCell(j+1);
					richString = new XSSFRichTextString(string);
					cell.setCellValue(richString);
					cell.setCellStyle(style);
				}
			}else{
				font = workBook.createFont();
				font.setFontHeightInPoints((short)12); //字体大小
			    font.setFontName("微软雅黑");
			    style = workBook.createCellStyle();
				style.setFont(font);
				style.setBorderBottom(CellStyle.BORDER_THIN);
				String[] values = (String[])list.get(i);
				XSSFRow row = (XSSFRow) sheet.createRow(i+1);
				for (int j = 0; j < values.length; j++) {
					String string = values[j];
					XSSFCell cell = row.createCell(j+1);
					richString = new XSSFRichTextString(string);
					cell.setCellValue(richString);
					cell.setCellStyle(style);
				}
			}
			
			
		}
		
	}

	

}

