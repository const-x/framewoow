package idv.constx.api.common.exception;

import com.bubugao.framework.mvc.ErrorCode;

/**
 * 
 * 继承自RuntimeException, 可将异常信息快速传递到异常边界.
 * 
 */
public class TransportException extends RuntimeException {
	private static final long serialVersionUID = 3583566093089790852L;
	private ErrorCode code;// 错误代码

	public TransportException() {
		super();
	}

	public TransportException(String message) {
		super(message);
	}

	public TransportException(ErrorCode code) {
		super(code.getMsg());
		this.code = code;
	}

	public TransportException(ErrorCode code, String message) {
		super(message);
		this.code = code;
	}

	public TransportException(Throwable cause) {
		super(cause);
	}

	public TransportException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransportException(ErrorCode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * @return the code
	 * 
	 * @author zhangxulong
	 * @createDate 2015年5月28日
	 */
	public ErrorCode getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 * 
	 * @author zhangxulong
	 * @createDate 2015年5月28日
	 */
	public void setCode(ErrorCode code) {
		this.code = code;
	}
}
