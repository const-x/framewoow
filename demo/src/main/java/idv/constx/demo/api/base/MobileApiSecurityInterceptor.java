package idv.constx.demo.api.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 登陸攔截器
 * @author const.x
 *
 */
public class MobileApiSecurityInterceptor  extends HandlerInterceptorAdapter {
	
	
	private final ThreadLocal<CallContext> callContextLocal = new ThreadLocal<CallContext>();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		callContextLocal.remove();
		
		CallContext callContext = new CallContext(request);
		callContextLocal.set(callContext);
		
		if(callContext.getCode() != ErrorCode.SUCCED){
			this.handleError(response, callContext.getCode());
			return false;
		}
		
		
		
		return true;
	}
	
	
	
	
	
	private void handleError(HttpServletResponse response, ErrorCode errorCode){
		String content = null;
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getOutputStream().write(content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		callContextLocal.remove();
	}

}
