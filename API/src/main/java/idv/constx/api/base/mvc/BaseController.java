package idv.constx.api.base.mvc;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idv.constx.api.base.mvc.interceptor.CallContext;
import idv.constx.api.base.mvc.interceptor.ErrorCode;
import idv.constx.api.base.mvc.interceptor.OpenApiSecurityInterceptor;
import idv.constx.api.common.DataPacket;
import idv.constx.api.common.exception.BussinessException;
import idv.constx.api.common.exception.ParamIllegalException;

/**
 * Mobile Api调用控制基础类
 * 
 */
public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected CallContext getClient(){
		return OpenApiSecurityInterceptor.getContext();
	}
	
	protected Map<String, Object> handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		if(ex instanceof ParamIllegalException){
			return this.handleException(request, response, ex,ErrorCode.PARAM_INVALID.getCode());
		}else if(ex instanceof BussinessException){
			return this.handleException(request, response, ex,ErrorCode.BUSINESS_EX.getCode());
		}else{
			return this.handleException(request, response, ex,ErrorCode.OTHERS.getCode());
		}
		
	}
	
	protected Map<String, Object> handleException(HttpServletRequest request, HttpServletResponse response, Exception ex,String code) {

		StringBuffer print = new StringBuffer();
		print.append("request:{");
		print.append("method:").append(request.getParameter("method"));
		print.append("}");

		// 打印出调用参数
		Map<String, String[]> tmp = request.getParameterMap();
		Map<String, String[]> paramsMap = tmp instanceof TreeMap ? tmp : new TreeMap<>(tmp);

		print.append(";params:{");
		boolean flag = false;
		for (Entry<String, String[]> entry : paramsMap.entrySet()) {
			if (!entry.getKey().equals("method")) {
				for (String value : entry.getValue()) {

					if (flag) {
						print.append(",");
					} else {
						flag = true;
					}
					print.append(entry.getKey()).append(":").append(value);
				}
			}
		}
		print.append("}");
		if(ex instanceof BussinessException){
			BussinessException c = (BussinessException)ex;
			logger.error(c.getLocalizedMessage()+": {}\n", print.toString(), c.getCause());
		}else{
			logger.error("api异常: {}\n", print.toString(), ex);
		}
		return DataPacket.errorJsonResult(code,ex.getMessage(), ex);
	}
	
	
	protected void assertNotNull(Object value,String errMsg){
		if(value == null){
			this.handleIllegalArgument(errMsg);
		}
		if(value instanceof Collection){
			if(((Collection)value).size() <= 0){
				this.handleIllegalArgument(errMsg);
			}
		}
		if(value.getClass().isArray()){
	        if(Array.getLength(value) <= 0){
				this.handleIllegalArgument(errMsg);
			}
		}
	}
	
	  protected void handleIllegalArgument(String msg){
	 		throw new ParamIllegalException(msg);
	 	}

}
