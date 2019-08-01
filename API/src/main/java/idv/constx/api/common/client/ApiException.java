package idv.constx.api.common.client;

import com.bubugao.framework.mvc.AbstractApiController;

/**
 * 包含有{@linkplain ApiError}数据结构的异常<br/>
 * 会通过{@linkplain AbstractApiController}自动转换为json错误
 * @author <a href="mailto:takeseem@gmail.com">杨浩</a>
 * @since 0.1.0
 */
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private ApiError error;

	public ApiError getError() {
		return error;
	}
	
	public ApiException() {
	}

	public ApiException(ApiError error) {
		super(error.getMsg());
		this.error = error;
	}
	public ApiException(Exception ex) {
		super(ex.getMessage(), ex);
		error = new ApiError(ex);
	}

	public ApiException(int httpCode, String code, String msg) {
		this(new ErrorCode(code, msg));
		error.setHttpCode(httpCode);
	}
	public ApiException(String code, String msg) {
		this(new ErrorCode(code, msg));
	}
	public ApiException(ErrorCode errorCode) {
		super(errorCode.getMsg());
		error = new ApiError(errorCode.getCode(), errorCode.getMsg());
	}
}
