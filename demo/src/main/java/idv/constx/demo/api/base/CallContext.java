package idv.constx.demo.api.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author const.x
 *
 */
public class CallContext {
	private String method,tokenId;
	private Long timestamp;
	private Client client;
	
	private ErrorCode code = ErrorCode.SUCCED;
	private Token token;
	
	
	public CallContext(HttpServletRequest request) {
		method = request.getParameter("method");
		if(StringUtils.isBlank(method)){
			code = ErrorCode.METHOD_MISS;
		}
		String time = request.getParameter("timestamp");
		if(StringUtils.isBlank(time)){
			code = ErrorCode.TIME_MISS;
		}
		try{
			timestamp = Long.valueOf(time);
		}catch(Exception e){
			code = ErrorCode.TIME_INVALID;
		}
		
		tokenId = request.getHeader("token");
		client = new Client(request);
	}


	public ErrorCode getCode() {
		return code;
	}


	public void setCode(ErrorCode code) {
		this.code = code;
	}


	public Token getToken() {
		return token;
	}


	public void setToken(Token token) {
		this.token = token;
	}


	public String getMethod() {
		return method;
	}


	public String getTokenId() {
		return tokenId;
	}


	public Long getTimestamp() {
		return timestamp;
	}


	public Client getClient() {
		return client;
	}

	
	
	
	

}
