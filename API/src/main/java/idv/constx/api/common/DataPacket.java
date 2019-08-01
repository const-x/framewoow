package idv.constx.api.common;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import com.alibaba.fastjson.JSON;

import idv.constx.api.base.dao.PageResult;
import idv.constx.api.base.mvc.interceptor.ErrorCode;
import idv.constx.api.common.exception.BussinessException;
public class DataPacket {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);
	public static final String APP_RETURN_SUCCESS_CODE = ErrorCode.SUCCED.getCode();// 请求处理成功
	public static final String APP_RETURN_FAIL_CODE = ErrorCode.BUSINESS_EX.getCode();// 请求处理失败


	/**
	 * 封装app接口返回json结构
	 * 
	 * @param code 返回代码[0000:正常,9999:错误,... 其它自定义]
	 * @param msg 提示信息
	 * @param data 响应数据
	 * @return
	 */
	public static Map<String, Object> jsonResult(String code, String msg, Object data) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", code);
		result.put("message", msg);
		if(data != null){
			result.put("data", data);
		}
		return result;
	}

	/**
	 * 封装app接口返回json结构
	 * 
	 * @param code 返回代码[0000:正常,9999:错误,... 其它自定义]
	 * @param msg 提示信息
	 * @param data 分页响应数据
	 * @return
	 */
	public static Map<String, Object> jsonResultByPage(String code, String msg, PageResult data) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", code);
		result.put("message", msg);
		result.put("page", data.getPage());
		result.put("pagesize", data.getPageSize());
		result.put("total", data.getTotal());
		result.put("data", data.getDatas());
		return result;
	}

	public static Map<String, Object> errorjsonResult(String msg) {
		return jsonResult(APP_RETURN_FAIL_CODE, msg, null);
	}

	public static Map<String, Object> errorJsonResult(String msg, Throwable e) {
		return errorJsonResult(APP_RETURN_FAIL_CODE, msg, e);
	}

	public static Map<String, Object> errorJsonResult(String code, String msg, Throwable e) {
		// EmptyResultDataAccessException
		// 数据库查询出的数据为空，导致RowMapper进行转换时抛出此错误，这个不应该作为一个错误进行处理（数据库查询出的数据为空是正常的情况）
		if (e instanceof EmptyResultDataAccessException) {
			return jsonResult(APP_RETURN_SUCCESS_CODE, "未找到相应数据", null);
		}
		// 如果是业务异常信息，则将原始的业务提示信息返回到前台
		if (e instanceof BussinessException) {
			msg = e.getMessage();
		} 
		Map<String, Object> result = new HashMap<>();
		result.put("code", code);
		if (StringUtils.isEmpty(msg)) {
			msg = e.getMessage();
		}
		result.put("message", msg);
		
		StringBuilder sb = new StringBuilder();
		int count = 0;
		boolean added = false;
		for (StackTraceElement stack : e.getStackTrace()) {
			String trace = filterStackTrace(stack);
			if (trace != null) {
				if (added) {
					sb.append(".....<br>");
				} else {
					added = true;
				}
				sb.append(trace).append("<br>");
				count++;
				if (count == 50) {
					sb.append(".....");
					break;
				}
			}
		}
		
		result.put("error", sb.toString());
		return result;
	}
	
	
	private static String filterStackTrace(StackTraceElement stack){
    	String msg = stack.toString();
    	if(msg.startsWith("com.sun.proxy.") || msg.startsWith("org.springframework.") 
    			|| msg.startsWith("sun.reflect.") || msg.startsWith("java.lang.reflect.")
    			|| msg.startsWith("org.mybatis.spring.") || msg.startsWith("org.apache.")
    			){
    		return null;
    	}else{
    		return msg;
    	}
    }

	/**
	 * 返回数据，传入数据对象，默认为正常处理
	 * 
	 * @param data
	 * @return
	 * 
	 * @author const.x
	 * @createDate 2014年9月7日
	 */
	public static Map<String, Object> jsonResult(Object data) {
		if (data instanceof PageResult) {
			return jsonResultByPage(APP_RETURN_SUCCESS_CODE, null, (PageResult) data);
		}
		return jsonResult(APP_RETURN_SUCCESS_CODE, null, data);
	}

	/**
	 * 返回数据，传入数据对象，默认为正常处理
	 * 
	 * @param data
	 * @return
	 * 
	 * @author const.x
	 * @createDate 2014年9月7日
	 */
	public static Map<String, Object> jsonResult(Object data, String msg) {
		if (data instanceof PageResult) {
			return jsonResultByPage(APP_RETURN_SUCCESS_CODE, msg, (PageResult) data);
		}
		return jsonResult(APP_RETURN_SUCCESS_CODE, msg, data);
	}

	public static Map<String, Object> jsonResult(String msg) {
		return jsonResult(APP_RETURN_SUCCESS_CODE, msg, null);
	}

	public static Map<String, Object> jsonKeyValueResult(String key, Object value) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put(key, value);
		return jsonResult(map);
	}


	public static Map<String, Object> jsonResult(String code, Object data) {
		return jsonResult(code, null, data);
	}

	public static Map<String, Object> jsonResult(String code, String msg) {
		return jsonResult(code, msg, null);
	}

	
	public static String toJson(Map<String, Object> data){
		return JSON.toJSONString(data);
	}
}
