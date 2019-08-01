package idv.constx.demo.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *将业务异常进行封装，记录日志 并快速抛出到边界
 *触发Spring事物回滚
 * 
 *
 * @author const.x
 */
public class ExceptionUtils {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

	public static void wapperException(Throwable e)  {
		if (e == null) {
			return;
		}
		if(e instanceof BussinessException){
			throw (BussinessException)e;
		}
		if(e instanceof ServiceException){
			throw (ServiceException)e;
		}
		if(e.getCause() != null){
			wapperException(e.getCause());
		}else{
			ServiceException se = new ServiceException(e);
			logger.error(e.getLocalizedMessage(),e);
			throw se;
		}
	}
	
	public static void throwBizException(String message)  {
		if (message == null) {
			return;
		}
		BussinessException exceptin = new BussinessException(message);
		logger.error(exceptin.getLocalizedMessage());
		throw exceptin;
	}

}
