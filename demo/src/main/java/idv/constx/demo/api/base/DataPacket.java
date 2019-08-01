/**
 * @createDate 2014年9月7日
 */
package idv.constx.demo.api.base;

import idv.constx.demo.common.exception.BussinessException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;

import com.alibaba.fastjson.JSON;


public class DataPacket {


	/**
	 * 封装app接口返回json结构
	 * 
	 * @param code 返回代码[0000:正常,9999:错误,... 其它自定义]
	 * @param msg 提示信息
	 * @param data 响应数据
	 * @return
	 */
	public static String jsonResult(String code, String msg, Object data) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", code);
		result.put("message", msg);
		result.put("data", data);
		return 	toJson(result);

	}

	/**
	 * 封装app接口返回json结构
	 * 
	 * @param code 返回代码[0000:正常,9999:错误,... 其它自定义]
	 * @param msg 提示信息
	 * @param data 分页响应数据
	 * @return
	 */
	public static String jsonResultByPage(String code, String msg, PageResult data) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", code);
		result.put("message", msg);
		result.put("page", data.getPage());
		result.put("pagesize", data.getPageSize());
		result.put("total", data.getTotal());
		result.put("data", data.getDatas());
		return 	toJson(result);
	}

	public static String exceptionJsonResult(String msg, Throwable e) {
		return errorJsonResult(ErrorCode.BUSINESS_EX.getCode(), msg, e);
	}

	public static String errorJsonResult(String msg, Throwable e) {
		return errorJsonResult(ErrorCode.BUSINESS_EX.getCode(), msg, e);
	}

	public static String errorJsonResult(String code, String msg, Throwable e) {
		// EmptyResultDataAccessException
		// 数据库查询出的数据为空，导致RowMapper进行转换时抛出此错误，这个不应该作为一个错误进行处理（数据库查询出的数据为空是正常的情况）
		if (e instanceof EmptyResultDataAccessException) {
			return jsonNoDataResult("未找到相应数据");
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
		// result.put("error", e);
		return 	toJson(result);
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
	public static String jsonResult(Object data) {
		if (data instanceof PageResult) {
			return jsonResultByPage(ErrorCode.SUCCED.getCode(), null, (PageResult) data);
		}
		return jsonResult(ErrorCode.SUCCED.getCode(), null, data);
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
	public static String jsonResult(Object data, String msg) {
		if (data instanceof PageResult) {
			return jsonResultByPage(ErrorCode.SUCCED.getCode(), msg, (PageResult) data);
		}
		return jsonResult(ErrorCode.SUCCED.getCode(), msg, data);
	}

	public static String jsonResult(String msg) {
		return jsonResult(ErrorCode.SUCCED.getCode(), msg, null);
	}

	public static String jsonKeyValueResult(String key, Object value) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put(key, value);
		return jsonResult(map);
	}

	public static String jsonNoDataResult(String msg) {
		return jsonResult(ErrorCode.SUCCED.getCode(), msg, null);
	}

	public static String jsonResult(String code, Object data) {
		return jsonResult(code, null, data);
	}

	public static String jsonResult(String code, String msg) {
		return jsonResult(code, msg, null);
	}

	public static String jsonResultError(String msg) {
		return jsonResult(ErrorCode.BUSINESS_EX.getCode(), msg, null);
	}
	
	private static String toJson(Map<String, Object> data){
		return JSON.toJSONString(data);
	}
}
