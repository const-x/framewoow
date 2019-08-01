/**
 * @author const.x
 * @createDate 2014年9月2日
 */
package idv.constx.api.common.client;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @author const.x
 */
@Service
public class HttpsClient extends HttpClient {
	public void build() {
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);

			// 只允许使用TLSv1协议
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			logger.error("创建httpClient 失败！");
			e.printStackTrace();
		}
	}

	/**
	 * 重写验证方法，取消检测ssl
	 */
	private static TrustManager truseAllManager = new X509TrustManager() {
		public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	};

	public static void main(String[] args) {
		HttpsClient httpClient = new HttpsClient();
		httpClient.build();

		try {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("token", "e3ed7d0273a146439628e7bbec9639a3");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("appId", "1");
			params.put("catId", "0");
			params.put("method", "bubugao.tuangou.list.get");
			params.put("pageNum", "1");
			params.put("pk", "com.bubugao.mobile");
			params.put("pkSign", "12345678901234567890123456789012");

			Map<String, Object> goods = httpClient.post("https://api.bubugao.com/v0.10.0/bubugao-open-api/rest",
					params, headers);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
