package com.hjc.herol.util;


import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author songhn
 *
 * 创建文档
 *
 */
public class WorkbookFactory {

	private Workbook workBook = null;
	private String excelFilePath = null;
	
	/**
	 * 创建文档WorkBook(读文档)
	 * @param excelFilePath
	 * @return
	 * @throws Exception
	 */
	public Workbook create(MultipartFile file,String fileType) throws Exception{
		InputStream fis = file.getInputStream();
		
		//2003版的Excel
		if(("xls").equals(fileType)){
			
			workBook = new HSSFWorkbook(fis);
			
		//2007版以后的Excel	
		}else if(("xlsx").equals(fileType)){
			
			workBook = new XSSFWorkbook(fis);
			
		}
		
		return workBook;
		
	}
	
	/**
	 * 创建文档WorkBook(写文档)
	 * @param excelFilePath
	 * @return
	 * @throws Exception
	 */
	public Workbook create(String fileName , boolean flag) throws Exception{
		
		this.excelFilePath = fileName;
		
		//2003版的Excel
		if(excelFilePath.endsWith(".xls")){
			
			workBook = new HSSFWorkbook();
			
		//2007版以后的Excel	
		}else if(excelFilePath.endsWith(".xlsx")){
			
			workBook = new XSSFWorkbook();
			
		}
		
		return workBook;
		
	}
	
}

