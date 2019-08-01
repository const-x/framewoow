package idv.constx.api.common.exception;

import com.bubugao.framework.mvc.ErrorCode;

/**
 * 业务异常信息
 * 
 * 
 * @author xieyalong
 */
public class BussinessException extends TransportException {

	private static final long serialVersionUID = 1L;
	
	protected Throwable cause;

	public BussinessException(String message) {
		super(message);
	}

	public BussinessException(String message, Throwable e) {
		super(message, e);
		if(e instanceof BussinessException){
			BussinessException c = (BussinessException)e;
			cause = c.getCause();
		}
	}

	public BussinessException(ErrorCode code, String message) {
		super(code, message);
	}
	
	public BussinessException(ErrorCode code) {
		super(code);
	}

	public BussinessException(ErrorCode code, String message, Throwable e) {
		super(code, message, e);
		if(e instanceof BussinessException){
			BussinessException c = (BussinessException)e;
			cause = c.getCause();
		}
	}

	
	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	

}
