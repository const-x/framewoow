/**
 * @author const.x
 * @createDate 2014年8月23日
 */
package idv.constx.api.common.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bubugao.framework.mvc.ApiError;
import com.bubugao.framework.mvc.ApiException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import idv.constx.api.common.ExceptionUtils;

/**
 * 内部调用httpClient
 * 
 * @author const.x
 */
@Service
public class HttpClient {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/** 内部调用httpClient */
	protected CloseableHttpClient httpClient;

	public static final ObjectMapper JSON_MAPPER = newObjectMapper();
	
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	
	private static ObjectMapper newObjectMapper() {
		ObjectMapper result = new ObjectMapper();
		result.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		result.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		result.setSerializationInclusion(Include.NON_NULL);
		result.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);	//不输出value=null的属性
		result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return result;
	}
	
	@PostConstruct
	public void init() {
		HttpClientBuilder builder = HttpClientBuilder.create();
		httpClient = builder.build();
	}

	@PreDestroy
	public void destroy() {
		try{
			httpClient.close();
		} catch (Exception e) {
			logger.warn("忽略异常 {}", httpClient, e);
		}
	}

	public String executeForResponseStr(HttpUriRequest request, Map<String, Object> params, Map<String, Object> headers)
			throws IOException {
		return executeOld(request, params, headers);
	}

	public Map<String, Object> execute(HttpUriRequest request, Map<String, Object> params, Map<String, Object> headers)
			throws IOException {
		String responseStr = executeOld(request, params, headers);
		Map<String, Object> json = toMap(responseStr);
		return json;
	}

	protected Map<String, Object> toMap(String value){
		if(StringUtils.isBlank(value)){
			return null;
		}
		try {
			return JSON_MAPPER.convertValue(JSON_MAPPER.readTree(value), Map.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	protected static String toString(Object value, String defaultValue) {
		return value == null ? defaultValue : value.toString();
	}
	
	protected  String toString(Object value) {
		return value == null ? null : value.toString();
	}
	
	public String executeOld(HttpUriRequest request, Map<String, Object> params, Map<String, Object> headers)
			throws IOException {
		// Map<String, Object> json = null;
		String html = null;
		try {
			if (headers != null) {
				for (Entry<String, Object> entry : headers.entrySet()) {
					request.setHeader(entry.getKey(),toString(entry.getValue(), ""));
				}

			}

			StringBuilder buidler = new StringBuilder(request.getURI().toString());
			if (params != null && params.size() > 0 && !request.getURI().toString().contains("?")) {
				buidler.append("?");
				for (Entry<String, Object> entry : params.entrySet()) {
					buidler.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
				buidler.deleteCharAt(buidler.length() - 1);
			}
			logger.debug("[HTTPCLIENT]:" + buidler.toString());
			Long beginTime = System.currentTimeMillis();

			CloseableHttpResponse response = httpClient.execute(request);

			Long endTime = System.currentTimeMillis();
			html = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			if (html != null && html.length() > 4096) {
//				String path = "d://Users//bbgds//Desktop//HTTPCLIENT.txt";
//				File file = this.createFile(path);
//				FileOutputStream out = new FileOutputStream(file, false);
//				out.write(html.getBytes());
//				out.flush();
//				out.close();
//				logger.debug("[HTTPCLIENT]:(" + (endTime - beginTime) + "ms):请求返回：已写入 " + path);
				logger.debug("[HTTPCLIENT](" + (endTime - beginTime) + "ms):" + html);
			} else {
				logger.debug("[HTTPCLIENT](" + (endTime - beginTime) + "ms):" + html);
			}

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				request.abort();
				ApiError apiError = new ApiError();
				apiError.setHttpCode(404);
				apiError.setMsg("后台服务不可用：" + html);

				throw new ApiException(apiError);
			}

		} catch (Exception e) {
			logger.error(html);
				ExceptionUtils.wapperBussinessException("操作失败", e);

		} finally {
			httpClient.close();
		}

		return html;
	}

	private File createFile(String filePath) {
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

	private String executeHtml(HttpUriRequest request, Map<String, Object> params, Map<String, Object> headers)
			throws IOException {
		String html = null;
		try {
			if (headers != null) {
				for (Entry<String, Object> entry : headers.entrySet()) {
					request.setHeader(entry.getKey(), toString(entry.getValue(), ""));
				}
			}
			CloseableHttpResponse response = httpClient.execute(request);
			html = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() == 404) {
				ApiError apiError = new ApiError();
				apiError.setHttpCode(404);
				apiError.setMsg("后台服务不可用：" + html);
				throw new ApiException(apiError);
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
			throw e;
		} finally {
			httpClient.close();
		}
		return html;
	}

	/**
	 * 通过get方式获取数据，自动签名params
	 * 
	 * @param url
	 * @param params key1, value1, ... , keyN, valueN
	 * @return 执行结果
	 */
	public Map<String, Object> get(String url, Object... params) throws IOException {
		Map<String, Object> data = new HashMap<String, Object>();
		for (int i = 0, len = params.length; i < len;) {
			data.put(toString(params[i++]), params[i++]);
		}
		return get(url, data);
	}

	/**
	 * 通过get方式获取数据，自动签名params
	 * 
	 * @param url
	 * @param params
	 * @return 执行结果
	 */
	public Map<String, Object> get(String url, Map<String, Object> params) throws IOException {
		HttpGet request = params == null || params.isEmpty() ? new HttpGet(url) : new HttpGet(toURI(url, params));
		return execute(request, params, null);
	}

	/**
	 * 通过get方式获取数据，自动签名params
	 * 
	 * @param url
	 * @param params
	 * @return 执行结果
	 */
	public Map<String, Object> get(String url, Map<String, Object> params, Map<String, Object> headers)
			throws IOException {
		HttpGet request = params == null || params.isEmpty() ? new HttpGet(url) : new HttpGet(toURI(url, params));
		return execute(request, params, headers);
	}

	/**
	 * 通过get方式获取数据，自动签名params
	 * 
	 * @param url
	 * @param params
	 * @return 执行结果
	 */
	public String getHtml(String url, Map<String, Object> params, Map<String, Object> headers) throws IOException {
		HttpGet request = params == null || params.isEmpty() ? new HttpGet(url) : new HttpGet(toURI(url, params));
		return executeHtml(request, params, headers);
	}

	/**
	 * 通过post方式获取数据，自动签名params
	 * 
	 * @param url
	 * @param params
	 * @return 执行结果
	 */
	public Map<String, Object> post(String url, Map<String, Object> params) throws IOException {
		HttpPost request = new HttpPost(url);
		
		List<NameValuePair> list = new ArrayList<>(params.size());
		for (Entry<String, Object> entry : params.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), toString(entry.getValue(), "")));
		}
		request.setEntity(new UrlEncodedFormEntity(list, UTF_8));
		
		return execute(request, params, null);
	}

	/**
	 * 通过post方式获取数据，自动签名params
	 * 
	 * @param url
	 * @param params
	 * @return 执行结果
	 */
	public Map<String, Object> post(String url, Map<String, Object> params, Map<String, Object> headers)
			throws IOException {
		HttpPost request = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<>(params.size());
		for (Entry<String, Object> entry : params.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), toString(entry.getValue(), "")));
		}
		request.setEntity(new UrlEncodedFormEntity(list, UTF_8));
		return execute(request, params, headers);
	}

	/**
	 * 通过post方式获取数据，自动签名params
	 * 
	 * @param url
	 * @param params
	 * @return 执行结果
	 */
	public String postHtml(String url, Map<String, Object> params, Map<String, Object> headers) throws IOException {
		HttpPost request = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<>(params.size());
		for (Entry<String, Object> entry : params.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), toString(entry.getValue(), "")));
		}
		request.setEntity(new UrlEncodedFormEntity(list, UTF_8));
		return executeHtml(request, params, headers);
	}

	/**
	 * 通过get方式获取数据
	 * 
	 * @param url
	 * @param params
	 * @return 执行结果
	 */
	public Map<String, Object> pureGet(String url, Map<String, Object> params) throws IOException {
		HttpGet request = params == null || params.isEmpty() ? new HttpGet(url) : new HttpGet(toURI(url, params));
		return execute(request, params, null);
	}

	public static URI toURI(String url, Map<String, Object> params) throws IllegalArgumentException {
		try {
			URIBuilder builder = new URIBuilder(url);

			String key;
			Object value;
			String[] values;
			for (Entry<String, Object> entry : params.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (value.getClass().isArray()) {
					values = (String[]) value;
					for (String v : values) {
						builder.addParameter(key, toString(v, ""));
					}
				} else {
					builder.addParameter(key, toString(value, ""));
				}
			}
			return builder.build();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
