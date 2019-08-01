package idv.constx.api.common.client;

import java.io.Serializable;

/**
 * 错误码对象
 * @author <a href="mailto:takeseem@gmail.com">杨浩</a>
 * @since 0.1.0
 */
public class ErrorCode implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	
	@Override
	public String toString() {
		return "ErrorCode" + "[code=" + code + ", "
				+ (msg != null ? "msg=" + msg : "")
				+ "]";
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	public ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
