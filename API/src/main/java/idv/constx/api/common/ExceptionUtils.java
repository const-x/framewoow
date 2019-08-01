package idv.constx.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bubugao.framework.mvc.ErrorCode;

import idv.constx.api.common.exception.BussinessException;
import idv.constx.api.common.exception.TransportException;

/**
 * 将业务异常进行封装，记录日志 并快速抛出到边界
 * 
 * 
 * @author const.x
 */
public class ExceptionUtils {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

	/**
	 * 快速抛出异常信息
	 * 
	 * @param e
	 * 
	 * @author const.x
	 * @createDate 2014年9月13日
	 */
	public static void wapperException(Throwable e) {
		if (e == null) {
			return;
		}

		TransportException exceptin;
		if (e instanceof TransportException) {
			exceptin = (TransportException) e;
		} else {
			exceptin = new TransportException(e);
		}
		logger.error(exceptin.getLocalizedMessage(), e);
		throw exceptin;
	}

	/**
	 * 封装并快速抛出业务异常
	 * 
	 * @param message
	 * 
	 * @author const.x
	 * @createDate 2014年9月13日
	 */
	public static void wapperBussinessException(String message) {
		if (message == null) {
			message = "服务异常";
		}

		BussinessException exceptin = new BussinessException(message);

		logger.error(exceptin.getLocalizedMessage());
		throw exceptin;
	}

	public static void wapperBussinessException(ErrorCode code) {
		if (code == null) {
			code = new ErrorCode("9999", "服务异常");
		}

		BussinessException exceptin = new BussinessException(code);

		logger.error(exceptin.getLocalizedMessage());
		throw exceptin;
	}

	public static void wapperBussinessException(ErrorCode code, String message) {
		if (message == null) {
			message = "服务异常";
		}

		BussinessException exceptin = new BussinessException(code, message);

		logger.error(exceptin.getLocalizedMessage());
		throw exceptin;
	}

	/**
	 * 封装并快速抛出业务异常
	 * 
	 * @param message
	 * 
	 * @author const.x
	 * @createDate 2014年9月13日
	 */
	public static void wapperBussinessException(String message, Throwable e) {
		if (message == null) {
			message = "服务异常";
		}

		BussinessException exceptin = new BussinessException(message, e);

		logger.error(exceptin.getLocalizedMessage(), e);
		throw exceptin;
	}

	public static void wapperBussinessException(ErrorCode code, String message, Throwable e) {
		if (message == null) {
			message = "服务异常";
		}

		BussinessException exceptin = new BussinessException(code, message, e);

		logger.error(exceptin.getLocalizedMessage(), e);
		throw exceptin;
	}
}
