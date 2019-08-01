package idv.constx.demo.common.exception;

/**
 * 业务错误
 * 
 *
 * @author const.x
 */
public class BussinessException extends RuntimeException {
	private static final long serialVersionUID = 3583566093089790852L;

	public BussinessException() {
		super();
	}

	public BussinessException(String message) {
		super(message);
	}

	public BussinessException(Throwable cause) {
		super(cause);
	}

	public BussinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
