package com.hjc.herol.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractExcelView;


@Service
public class ExcelOutUtil extends AbstractExcelView {
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> map,
			HSSFWorkbook workbook, HttpServletRequest arg2,
			HttpServletResponse response) throws Exception {
		List<String[]> list = null;
		HSSFSheet sheet = null;
		String fileName = null;
		sheet = workbook.createSheet((String) map.get("fileName"));
		fileName = (String) map.get("fileName")+".xls";
		
		sheet.setDefaultColumnWidth((int) 12);
		sheet.createFreezePane(1, 1);
		list = new ArrayList<String[]>();
		list = (ArrayList<String[]>) map.get("list");
		String contentType = "application nd.ms-excel;charset=UTF-8";
        response.setContentType(contentType);
        response.setHeader( "Content-Disposition", "attachment;filename="+new String(fileName.getBytes("gb2312"),"ISO8859-1" ));
        int iRow = 1;
        int count = 50001;
        int name = 1;
        int row = 0;
        for(int i = 0; i < list.size(); i++){
        	if(iRow%(count)==0){
        		iRow = 1;
				sheet  = workbook.createSheet((String) map.get("fileName")+"_"+name);
				sheet.setDefaultColumnWidth((int) 12);
				sheet.createFreezePane(1, 1);
				name++;
				row = 0;
        	}
        	HSSFRow sheetRow = sheet.createRow(row);
	    	for(int j = 0; j < list.get(i).length; j++){
	    		HSSFCell cell = sheetRow.createCell(j);
	    		if(iRow == 1){
	    			cell.setCellValue(list.get(0)[j]);
	    		}else{
	    			cell.setCellValue(list.get(i)[j]);
	    		}
	    	}
	    	row++;
        	iRow++;
	    }
        
	}	

}