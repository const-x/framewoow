package idv.constx.api.common.exception;

/**
 * 服务器处理异常
 * 
 * Service层公用的Exception.
 * 
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3583566093089790852L;

	public ServiceException() {
		super("系统异常");
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
		super.setStackTrace(cause.getStackTrace());
	}

	public ServiceException(String message, Throwable cause) {
		super(message,cause);
		super.setStackTrace(cause.getStackTrace());
	}
}
