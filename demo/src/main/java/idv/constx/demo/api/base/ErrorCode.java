package idv.constx.demo.api.base;

public enum ErrorCode {

	/**操作成功*/
	SUCCED("1000","操作成功"),
	
	
	/**token非法*/
	TOKEN_INVALID("2000","token非法"),
	/**请登陆*/
	TOKEN_MISS("2001","请登陆"),
	/**登陆已过期*/
	TOKEN_OVERDUE("2002","登陆已过期"),
	
	/**该IP被禁止访问*/
	IP_RESTRICTED("3000","该IP被禁止访问"),
	
	
	/**请求参数非法*/
	PARAM_INVALID("4000","请求参数非法"),
	/**请求时间和服务器时间差距过大*/
	REQUEST_TIMEOUT("4001","请求时间和服务器时间差距过大"),
	/**请求签名无效*/
	SIGN_INVALID("4002","请求签名无效"),
	/**参数method缺失*/
	METHOD_MISS("4003","参数method缺失"),
	/**参数timestamp缺失*/
	TIME_MISS("4004","参数timestamp缺失"),
	/**参数timestamp格式错误*/
	TIME_INVALID("4005","参数timestamp格式错误"),
	
	/**其他错误*/
	OTHERS("5000","其他错误"),
	
	
	/**业务异常*/
	BUSINESS_EX("9000","业务异常");

	
	private String code;
	private String msg;
	
	
	
	
	public String getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}




	private ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
	
	
}
