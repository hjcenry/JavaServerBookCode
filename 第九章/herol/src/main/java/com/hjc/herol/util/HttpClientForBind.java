package com.hjc.herol.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpClientForBind {

	public synchronized static String post(String urlstr, String data) {
		try {
			org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
			HttpPost method = new HttpPost(urlstr);
			method.setHeader("Content-Type","application/x-www-form-urlencoded");
			ByteArrayEntity reqEntity = new ByteArrayEntity(data.getBytes("UTF-8"));
			reqEntity.setContentEncoding("UTF-8");
			method.setEntity(reqEntity);
			HttpResponse response = null;

			response = httpclient.execute(method);
			if (response != null) {
				// HttpEntity resEntity = response.getEntity();
				return EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized static String postOfHttps(String urlstr, String data) {
		try {
			org.apache.http.client.HttpClient httpclient = wrapClient(new DefaultHttpClient());
			HttpPost method = new HttpPost(urlstr);
			method.setHeader("Content-Type","application/x-www-form-urlencoded");
			ByteArrayEntity reqEntity = new ByteArrayEntity(data.getBytes("UTF-8"));
			reqEntity.setContentEncoding("UTF-8");
			method.setEntity(reqEntity);
			HttpResponse response = null;

			response = httpclient.execute(method);
			if (response != null) {
				// HttpEntity resEntity = response.getEntity();
				return EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized static String get(String urlstr) {
		org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
		HttpGet method = new HttpGet(urlstr);
		method.setHeader("Content-type" , "text/html; charset=utf-8");
		HttpResponse response = null;
		try {
			response = httpclient.execute(method);
			if (response != null) {
				// HttpEntity resEntity = response.getEntity();
				return EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	public synchronized static String getOfHttps(String urlstr) {
		org.apache.http.client.HttpClient httpclient = wrapClient(new DefaultHttpClient());
		HttpGet method = new HttpGet(urlstr);
		method.setHeader("Content-type" , "text/html; charset=utf-8");
		HttpResponse response = null;
		try {
			response = httpclient.execute(method);
			if (response != null) {
				// HttpEntity resEntity = response.getEntity();
				return EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public synchronized static void download(String url, String path) throws HttpException {
		org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
		HttpGet method = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpclient.execute(method);
			if (response != null) {
				File storeFile = new File(path);
				FileOutputStream fos = new FileOutputStream(storeFile);
				HttpEntity resEntity = response.getEntity();
				InputStream is = resEntity.getContent();
				byte[] byteArr = new byte[1024];
				// 读取的字节数
				int readCount = is.read(byteArr);
				// 如果已到达文件末尾，则返回-1
				while (readCount != -1) {
					fos.write(byteArr, 0, readCount);
					readCount = is.read(byteArr);
				}
				is.close();
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static String downloadAmr(String url, String path) throws HttpException {
		org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
		HttpGet method = new HttpGet(url);
		HttpResponse response = null;
		Header[] headers = null;
		try {
			response = httpclient.execute(method);
			headers = response.getHeaders("Content-Type");
			System.out.println(headers[0]);
			if (response != null) {
				File storeFile = new File(path);
				FileOutputStream fos = new FileOutputStream(storeFile);
				HttpEntity resEntity = response.getEntity();
				InputStream is = resEntity.getContent();
				byte[] byteArr = new byte[1024];
				// 读取的字节数
				int readCount = is.read(byteArr);
				// 如果已到达文件末尾，则返回-1
				while (readCount != -1) {
					fos.write(byteArr, 0, readCount);
					readCount = is.read(byteArr);
				}
				is.close();
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return headers[0].toString();
	}
	
	
	
	public static String httpGet(String url){
		org.apache.http.client.HttpClient httpclient = wrapClient(new DefaultHttpClient());
		String result = "";
		try {
			// 连接超时
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			// 读取超时
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
			HttpGet hg = new HttpGet(url);
			// 模拟浏览器
			hg.addHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
			String charset = "UTF-8";
			hg.setURI(new java.net.URI(url));
			HttpResponse response = httpclient.execute(hg);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				charset = getContentCharSet(entity);
				// 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1
				result = EntityUtils.toString(entity, charset);
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	public static String getContentCharSet(final HttpEntity entity) throws ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		String charset = null;
		if (entity.getContentType() != null) {
			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}

		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		return charset;
	}
	
	
	/**
     * 避免HttpClient的”SSLPeerUnverifiedException: peer not authenticated”异常
     * 不用导入SSL证书
     * @author shipengzhi(shipengzhi@sogou-inc.com)
     *
     */
    public static org.apache.http.client.HttpClient wrapClient(org.apache.http.client.HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
            ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
            return new DefaultHttpClient(mgr, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

	
}
