package idv.constx.api.base.mvc.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import idv.constx.api.common.DataPacket;
import idv.constx.api.common.RSAUtils;

public class OpenApiSecurityInterceptor  extends HandlerInterceptorAdapter {
	
	
	private final static ThreadLocal<CallContext> callContextLocal = new ThreadLocal<CallContext>();
	
	private Long timeDiff = -1L;
	
	private Integer signverify = -1;
	
	private String publicKey = null;
	
	
	public Long getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(Long timeDiff) {
		this.timeDiff = timeDiff;
	}
	
	public Integer getSignverify() {
		return signverify;
	}

	public void setSignverify(Integer signverify) {
		this.signverify = signverify;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		callContextLocal.remove();
		CallContext callContext = new CallContext(request);
		callContextLocal.set(callContext);
		
		if(!this.validateContext(callContext) || !this.validateTimeDiff(callContext) || !this.validateSign(callContext)|| !this.validateToken(callContext)){
			this.handleError(response, callContext.getCode());
			return false;
		}
		
		return true;
	}
	
	//验证必要参数是否存在
	private boolean validateContext(CallContext callContext){
		
		if(StringUtils.isBlank(callContext.getMethod())){
			callContext.setCode(ErrorCode.METHOD_MISS);
			return false;
		}
		if(StringUtils.isBlank(callContext.getTimestamp())){
			callContext.setCode(ErrorCode.TIME_MISS);
			return false;
		}
		try{
			Long time = Long.valueOf(callContext.getTimestamp());
		}catch(Exception e){
			callContext.setCode(ErrorCode.TIME_INVALID);
			return false;
		}
		
		return true;
	}
	
	
	//验证请求时间与服务器时间差
	private boolean validateTimeDiff(CallContext callContext){
		if(timeDiff > 0){
			Long ts = Long.valueOf(callContext.getTimestamp());
			Long cur = System.currentTimeMillis();
			if(Math.abs(cur - ts ) > timeDiff){
				callContext.setCode(ErrorCode.REQUEST_TIME_DIFF);
				return false;
			}
		}
		return true;
	}
	
	// 验证签名
	private boolean validateSign(CallContext callContext) {
		if (signverify > 0) {
			if (StringUtils.isBlank(callContext.getSign())) {
				callContext.setCode(ErrorCode.SIGN_MISS);
				return false;
			}
			Map<String, String[]> params = callContext.getParams();

			List<Entry<String, String[]>> list = new ArrayList<Entry<String, String[]>>(params.entrySet());
			Collections.sort(list, new Comparator<Entry<String, String[]>>() {
				// 升序排序
				public int compare(Entry<String, String[]> o1, Entry<String, String[]> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			StringBuilder sb = new StringBuilder();
			for (Entry<String, String[]> entry : list) {
				if(!entry.getKey().equals("sign")){
					sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
			}
			if(sb.length() > 0){
				sb.deleteCharAt(sb.lastIndexOf("&"));
			}
			String signbase = sb.toString();
			if (StringUtils.isBlank(publicKey)) {
				callContext.setCode(ErrorCode.VERIFY_KEY_MISS);
				return false;
			}
			if (!RSAUtils.verify(signbase, callContext.getSign(), publicKey)) {
				callContext.setCode(ErrorCode.SIGN_INVALID);
				return false;
			}
			return true;
		}
		return true;
	}
	
	//验证Token
	private boolean validateToken(CallContext callContext){
		return true;
	}
	
	public static CallContext getContext(){
		return callContextLocal.get();
	}
	
	
	private void handleError(HttpServletResponse response, ErrorCode errorCode){
		String content = DataPacket.toJson(DataPacket.jsonResult(errorCode.getCode(),errorCode.getMsg()));
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
