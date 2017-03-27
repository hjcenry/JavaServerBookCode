package sockettest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjc.herol.net.ProtoIds;
import com.hjc.herol.net.ProtoMessage;
import com.hjc.herol.net.message.TestReq;
import com.hjc.herol.util.JsonUtils;

public class SocketTest {

	public static void main(String[] args) {
		int N = 1;
		//
		// for (int i = 0; i < N; i++) {
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		try {
			Socket socket = new Socket("127.0.0.1", 8900);
			System.out.println(socket.getRemoteSocketAddress());
			OutputStream outputStream = socket.getOutputStream();
			JSONObject param = new JSONObject();
			param.put("billno", 123456);
			System.out.println(param);
			long start = System.currentTimeMillis();
			outputStream.write(param.toString().getBytes(
					Charset.forName("UTF-8")));
			outputStream.flush();
			while (true) {
				InputStream is = socket.getInputStream();
				byte[] bytes = new byte[1024];
				int n = is.read(bytes);
				System.out.println(new String(bytes, 0, n) + ",during"
						+ (System.currentTimeMillis() - start));
				if (new String(bytes, 0, n).equals("{\"result\":0}")) {
					is.close();
					socket.close();
				}
			}
			// System.out.println("closed");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// }
		// }).start();
		// }
	}
}
