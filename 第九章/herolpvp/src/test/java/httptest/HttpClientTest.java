package httptest;

import com.hjc.herolpvp.util.HttpClient;

public class HttpClientTest {

	public volatile long time = 0;

	public static void main(String[] args) throws Exception {
		HttpClientTest test = new HttpClientTest();
		test.test();
	}

	public void test() {
		String ret = null;
		try {
			ret = HttpClient.post(
					"http://127.0.0.1:81/herolrouter/route/loginOrRegist",
					"name=hjc&pwd=123456");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ret);
	}

}
