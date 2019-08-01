package idv.constx.api.common.client;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.MissingServletRequestParameterException;

import com.bubugao.framework.util.MapWrap;
import com.bubugao.framework.util.UtilString;

/**
 * api错误信息 <pre>{
 * 	httpCode: '',
 * 	code: '',
 * 	msg: '',
 * 	data: {},
 * 	errors: [{
 * 	}]
 * }</pre>
 * @author <a href="mailto:takeseem@gmail.com">杨浩</a>
 * @since 0.1.0
 */
public class ApiError implements Serializable {
	private static final long serialVersionUID = 1L;
	private final MapWrap values = new MapWrap();

	public MapWrap getValues() {
		return values;
	}
	public Integer getHttpCode() {
		return values.getInteger("httpCode");
	}
	public void setHttpCode(int httpCode) {
		values.put("httpCode", httpCode);
	}
	public String getCode() {
		return values.get("code");
	}
	public void setCode(String code) {
		values.put("code", code);
	}
	public String getMsg() {
		return values.get("msg");
	}
	public void setMsg(String msg) {
		values.put("msg", msg);
	}
	public Map<String, Object> getData() {
		Map<String, Object> data = values.get("data");
		if (data == null) {
			data = new HashMap<String, Object>();
			values.put("data", data);
		}
		return data;
	}
	public List<Map<String, Object>> getErrors() {
		List<Map<String, Object>> errors = values.get("errors");
		if (errors == null) {
			errors = new ArrayList();
			values.put("errors", errors);
		}
		return errors;
	}
	
	public ApiError() {}
	/** @deprecated */
	public ApiError(Integer code, String msg) {
		this((String) (code == null ? null : code.toString()), msg);
	}
	public ApiError(String code, String msg) {
		setCode(code);
		setMsg(msg);
	}
	public ApiError(Exception ex) {
		String msg = ex.getMessage();
		if (UtilString.isEmpty(msg)) msg = ex.toString();
		setMsg(msg);
		if (ex instanceof IllegalArgumentException || ex instanceof MissingServletRequestParameterException) {
			setHttpCode(400);
		} else if (ex instanceof IllegalStateException) {
			setHttpCode(503);
		}
	}
	@SuppressWarnings("unchecked")
	public ApiError(Map<String, Object> error) {
		values.putAll(error);
		
		Object errors = values.getDatas().remove("errors");
		if (errors != null) {
			List<Map<String, Object>> list = new ArrayList();
			if (errors instanceof List) {
				list = (List<Map<String, Object>>) errors;
			}  else if (errors instanceof Collection) {
				for (Map<String, Object> tmp : (Collection<Map<String, Object>>) errors) {
					if (tmp != null) list.add(tmp);
				}
			} else if (errors.getClass().isArray()) {
				for (int i = 0, len = Array.getLength(errors); i < len; i++) {
					Map<String, Object> tmp = (Map<String, Object>) Array.get(errors, i);
					if (tmp != null) list.add(tmp);
				}
			} else {
				throw new IllegalArgumentException("error.errors转换失败：" + error);
			}
			values.put("errors", list);
		}
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> result = new HashMap<String, Object>(values.getDatas());
		if (result.get("httpCode") == null) result.remove("httpCode");
		if (result.get("code") == null || result.get("code").equals("0")) result.remove("code");
		if (result.get("msg") == null) result.remove("msg");
		
		result.remove("data");
		Map<String, Object> data = getData();
		if (!data.isEmpty()) result.put("data", data);
		
		result.remove("errors");
		List<Map<String, Object>> errors = getErrors();
		if (!errors.isEmpty()) result.put("errors", errors);
		
		return result;
	}
}
