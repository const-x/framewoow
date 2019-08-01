package idv.constx.api.base.mvc.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author const.x
 *
 */
public class CallContext {
	private String method,tokenId;
	private String timestamp;
	private Client client;
	private String sign;
	private Map<String, String[]> params;
	
	private ErrorCode code = ErrorCode.SUCCED;
	
	
	public CallContext(HttpServletRequest request) {
		method = request.getParameter("method");
	    timestamp = request.getParameter("ts");
		tokenId = request.getHeader("token");
		sign = request.getHeader("sign");
		params = request.getParameterMap();
		client = new Client(request);
	}


	public ErrorCode getCode() {
		return code;
	}


	public void setCode(ErrorCode code) {
		this.code = code;
	}


	public String getMethod() {
		return method;
	}


	public String getTokenId() {
		return tokenId;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public Client getClient() {
		return client;
	}


	public String getSign() {
		return sign;
	}


	public Map<String, String[]> getParams() {
		return params;
	}

	
	
	
	

}
