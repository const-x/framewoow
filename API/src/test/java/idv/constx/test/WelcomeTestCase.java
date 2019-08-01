package idv.constx.test;

import java.util.HashMap;
import java.util.Map;

public class WelcomeTestCase extends AbstractTestCase{
	
	public static void main(String[] args) {
		try {
			welcome();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> welcome() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("method", "idv.constx.welcome.get");
		 params.put("keywords", "田心");
		 params.put("ts", System.currentTimeMillis());
		 
		 Map<String, String> headers = new HashMap<String, String>();
		 headers.put("platform", "android");
		 headers.put("platformVersion", "6.0");
		 headers.put("ip", "127.0.0.1");
		 headers.put("version", "1.0");
		 headers.put("appId", "demo");
		 headers.put("deviceId", "12345678");
		 

			
		 
		return post(params,headers);
	}

}
