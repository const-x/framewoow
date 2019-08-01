package idv.constx.api.base.mvc.converter;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.bubugao.framework.util.UtilJson;
import com.bubugao.framework.util.UtilString;
import com.bubugao.framework.util.UtilSys;
import com.fasterxml.jackson.core.JsonProcessingException;

import idv.constx.api.base.mvc.interceptor.CallContext;
import idv.constx.api.base.mvc.interceptor.OpenApiSecurityInterceptor;

/**
 * 返回数据格式转换类
 * 
 */
public class OpenApiJsonConverter extends MappingJackson2HttpMessageConverter {
	
	private static final String jsonpCallback = "callback";// jsonp格式返回

	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		CallContext ctx = OpenApiSecurityInterceptor.getContext();
		if ("H5".equalsIgnoreCase(ctx.getClient().getPlatform()) ) {// 客户端为HTML5,转换返回数据格为JSONP格式
			OutputStream out = outputMessage.getBody();
			byte[] bytes = createJsonp(null, object).getBytes(UtilSys.UTF_8);
			out.write(bytes);
		} else {
			super.writeInternal(object, outputMessage);
		}
	}

	public static String createJsonp(String funName, Object data) {
		StringBuffer jsonp = new StringBuffer();
		if (UtilString.notEmpty(funName)) {
			jsonp.append(funName);
		} else {
			jsonp.append(jsonpCallback);
		}
		jsonp.append("(");

		try {
			jsonp.append(UtilJson.getObjectMapper().writeValueAsString(data));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		jsonp.append(")");

		return jsonp.toString();
	}
}
