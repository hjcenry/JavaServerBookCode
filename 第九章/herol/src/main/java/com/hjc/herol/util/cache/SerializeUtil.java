package com.hjc.herol.util.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 康建虎
 *
 */
public class SerializeUtil {
	private static Logger log = LoggerFactory.getLogger(SerializeUtil.class);
	public static byte[] encode(Object o) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(o);
		os.close();
		bos.close();
		byte[] arr = bos.toByteArray();
		return arr;
	}
	public static <T> T decode(byte[] data) throws IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object o = null;
		try{
			o = ois.readObject();
		}catch(ClassNotFoundException e){
			log.error("解析失败", e);
		}
		return (T)o;
	}
}
