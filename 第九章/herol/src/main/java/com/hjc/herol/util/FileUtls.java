package com.hjc.herol.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.util.FileCopyUtils;

public class FileUtls {
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
       boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    public static boolean backupFile(byte[] fileByte,String fileName,String fileType,String username, String realPath){
    	String sysdate = DateFormat.formatDate(new Date(), "yyyy年MM月dd日HH时mm分ss秒");
    	StringBuffer sb = new StringBuffer();
    	sb.append(sysdate);
    	sb.append("_");
    	sb.append(username);
    	sb.append("_");
    	sb.append(fileType);
    	sb.append("_");
    	sb.append(fileName);
		String uploadFileName = sb.toString();
		String localServerExcelPath = realPath+File.separator+uploadFileName;
		File uploadFile	 = new File(localServerExcelPath); 
				try {
					FileCopyUtils.copy(fileByte, uploadFile);
				} catch (IOException e) {
					return false;
				}
		return true;
    }
}
