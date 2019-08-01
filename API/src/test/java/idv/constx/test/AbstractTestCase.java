package idv.constx.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bubugao.framework.mvc.ApiError;
import com.bubugao.framework.mvc.ApiException;
import com.bubugao.framework.util.UtilHttp;
import com.bubugao.framework.util.UtilString;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;




public abstract class AbstractTestCase {
	
	protected final static Logger logger = LoggerFactory.getLogger(AbstractTestCase.class);
	
	protected static String basePath = "http://127.0.0.1:8080/bubugao-b2b-tms/api/order";
//	protected static String basePath = "http://192.168.7.62:8080/bubugao-b2b-tms/api/order";
	
	
	/** 内部调用httpClient */
	protected static CloseableHttpClient httpClient;

	public static final ObjectMapper JSON_MAPPER = newObjectMapper();
	
	public static final String encoding = "UTF-8";
	
	public static void addParam(Map<String,Object> map,String key,Object value){
//		if(value instanceof String){
//			try {
//				value = URLEncoder.encode((String)value,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
		map.put(key, value);
	}
	
	
	private static ObjectMapper newObjectMapper() {
		ObjectMapper result = new ObjectMapper();
		result.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		result.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		result.setSerializationInclusion(Include.NON_NULL);
		result.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);	//不输出value=null的属性
		result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return result;
	}

	public static void init() {
		if(httpClient == null){
			HttpClientBuilder builder = HttpClientBuilder.create();
			httpClient = builder.build();
		}
	}

	@PreDestroy
	public void destroy() {
		try{
			httpClient.close();
		} catch (Exception e) {
			logger.warn("忽略异常 {}", httpClient, e);
		}
	}

	public static Map<String, Object> post(Map<String, Object> params,Map<String, String> headers) throws Exception {
		try {
			List<NameValuePair> list = new ArrayList<NameValuePair>(params.size());
			for (Entry<String, Object> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), UtilString.toString(entry.getValue(), "")));
			}
			
			HttpPost request = new HttpPost(basePath);
			UtilHttp.setParams(request, list);

			
			StringBuilder buidler = new StringBuilder(request.getURI().toString());
			if (params != null && params.size() > 0 && !request.getURI().toString().contains("?")) {
				buidler.append("?");
				for (Entry<String, Object> entry : params.entrySet()) {
					buidler.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
				buidler.deleteCharAt(buidler.length() - 1);
			}
			
			if(headers != null){
				for (Entry<String, String> entry : headers.entrySet()) {
					request.setHeader(entry.getKey(), entry.getValue());
				}
			}
			
			System.out.println("[HTTPCLIENT]:" + buidler.toString() + "  POST");
			
			init();
			
			Long beginTime = System.currentTimeMillis();
			CloseableHttpResponse response = httpClient.execute(request);
			Long endTime = System.currentTimeMillis();
			
			String html = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			if (html != null && html.length() > 4096) {
				String path = "d://Users//bbgds//Desktop//HTTPCLIENT.txt";
				File file = createFile(path);
				FileOutputStream out = new FileOutputStream(file, false);
				out.write(html.getBytes());
				out.flush();
				out.close();
				System.out.println("[HTTPCLIENT]:(" + (endTime - beginTime) + "ms):请求返回：已写入 " + path);
			} else {
				System.out.println("[HTTPCLIENT](" + (endTime - beginTime) + "ms):" + html);
			}
			
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				request.abort();
				ApiError apiError = new ApiError();
				apiError.setHttpCode(statusCode);
				apiError.setMsg("后台服务不可用：" + statusCode);
				throw new ApiException(apiError);
			}
			
			Map<String, Object> json = toMap(html);
			
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static File createFile(String filePath) {
		  File file = new File(filePath);
	      if (!file.exists()) {
	    	  file.getParentFile().mkdirs();
	          try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	      }
	      return file;
	  }
	
	public static Map<String, Object> get(Map<String, Object> params,Map<String, String> headers) throws Exception {
		try {
			StringBuilder buidler = new StringBuilder(basePath);
			
			
			if (params != null && params.size() > 0 && !basePath.contains("?")) {
				if(!basePath.contains("?")){
					buidler.append("?");
				}
				for (Entry<String, Object> entry : params.entrySet()) {
					buidler.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), encoding)).append("&");
				}
				buidler.deleteCharAt(buidler.length() - 1);
			}
			System.out.println("[HTTPCLIENT]:" + buidler.toString()+" GET");
			HttpGet request = new HttpGet(buidler.toString());
			
			if(headers != null){
				for (Entry<String, String> entry : headers.entrySet()) {
					request.setHeader(entry.getKey(), entry.getValue());
				}
			}
			
			init();
			
			Long beginTime = System.currentTimeMillis();
			CloseableHttpResponse response = httpClient.execute(request);
			Long endTime = System.currentTimeMillis();
			
			String html = EntityUtils.toString(response.getEntity(), Consts.UTF_8);

			
			System.out.println("[HTTPCLIENT](" + (endTime - beginTime) + "ms):" + html);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				request.abort();
				ApiError apiError = new ApiError();
				apiError.setHttpCode(statusCode);
				apiError.setMsg("后台服务不可用：" + statusCode);
				throw new ApiException(apiError);
			}
			
			Map<String, Object> json = toMap(html);
			
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	protected static Map<String, Object> toMap(String value){
		if(StringUtils.isBlank(value)){
			return null;
		}
		try {
			return JSON_MAPPER.convertValue(JSON_MAPPER.readTree(value), Map.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
}
