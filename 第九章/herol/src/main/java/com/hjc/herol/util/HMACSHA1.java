package com.hjc.herol.util;

import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;  
  





import javax.crypto.Mac;  
import javax.crypto.spec.SecretKeySpec; 

import com.hjc.herol.util.encrypt.Md5Coder;

public class HMACSHA1 {
	private static final String HMAC_SHA1 = "HmacSHA1";  
	  
    /** 
     * 生成签名数据 
     *  
     * @param data 待加密的数据 
     * @param key  加密使用的key 
     * @return 生成MD5编码的字符串  
     * @throws InvalidKeyException 
     * @throws NoSuchAlgorithmException 
     */  
    public static String getSignature(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {  
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);  
        Mac mac = Mac.getInstance(HMAC_SHA1);  
        mac.init(signingKey);  
        byte[] rawHmac = mac.doFinal(data);  
        return Md5Coder.encodeByMD5(new String(rawHmac));  
    } 
}
