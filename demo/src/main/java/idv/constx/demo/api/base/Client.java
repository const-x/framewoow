package idv.constx.demo.api.base;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端
 * 
 * @author const.x
 * 
 */
public class Client {
	/** 平台 */
	private String platform = null;

	/** 平台版本 */
	private String platformVersion = null;

	/** 访问IP */
	private String ip = null;

	/** 应用版本 */
	private String version = null;

	/** 应用ID */
	private String appId = null;
	
	
	public Client(HttpServletRequest request) {
		platform = request.getHeader("platform");
		platformVersion = request.getHeader("platformVersion");
		ip = request.getHeader("ip");
		appId = request.getHeader("appId");
		version = request.getHeader("version");
	}

	/**
	 * 获取应用ID
	 * 
	 * @return String
	 */
	public String getAppId() {
		return this.appId;
	}

	/**
	 * 设置应用ID
	 * 
	 * @param String
	 */
	public void setAppId(String value) {
		this.appId = value;
	}

	/**
	 * 获取平台
	 * 
	 * @return String
	 */
	public String getPlatform() {
		return this.platform;
	}

	/**
	 * 设置平台
	 * 
	 * @param String
	 */
	public void setPlatform(String value) {
		this.platform = value;
	}

	/**
	 * 获取平台版本
	 * 
	 * @return String
	 */
	public String getPlatformVersion() {
		return this.platformVersion;
	}

	/**
	 * 设置平台版本
	 * 
	 * @param String
	 */
	public void setPlatformVersion(String value) {
		this.platformVersion = value;
	}

	/**
	 * 获取访问IP
	 * 
	 * @return String
	 */
	public String getIp() {
		return this.ip;
	}

	/**
	 * 设置访问IP
	 * 
	 * @param String
	 */
	public void setIp(String value) {
		this.ip = value;
	}

	/**
	 * 获取应用版本
	 * 
	 * @return String
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * 设置应用版本
	 * 
	 * @param String
	 */
	public void setVersion(String value) {
		this.version = value;
	}

}
