package idv.constx.demo.util.dwz;

import idv.constx.demo.base.view.View;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AjaxObject {
	// 状态码
	public final static int STATUS_CODE_SUCCESS = 200;
	public final static int STATUS_CODE_FAILURE = 300;
	public final static int STATUS_CODE_TIMEOUT = 301;
	public final static int STATUS_CODE_WARN = 302;//警告提示
	public final static int STATUS_CODE_FORBIDDEN = 403;

	// callbackType类型
	public final static String CALLBACK_TYPE_REFRESH_CURRENT = "refreshCurrent";
	public final static String CALLBACK_TYPE_CLOSE_CURRENT = "closeCurrent";
	public final static String CALLBACK_TYPE_FORWARD = "forward";
	public final static String CALLBACK_TYPE_FORWARD_CONFIRM = "forwardConfirm";
	public final static String CALLBACK_TYPE_INFO = "info";
	public final static String CALLBACK_TYPE_WARN = "warn";
	public final static String CALLBACK_TYPE_ERROR = "error";
	

	private int statusCode = STATUS_CODE_SUCCESS;
	private String message = "";

	private String callbackType = "";
	private String navTabId = "";
	private String forwardUrl = "";
	private String forwardConfirmMsg = "";
	
	private long total = 0;
	private String data;
	
	
	

	public AjaxObject() {

	}

	public AjaxObject(String message) {
		this.message = message;
	}

	/**
	 * 构造函数
	 * 
	 * @param statusCode
	 * @param message
	 * @param callbackType
	 */
	public AjaxObject(int statusCode, String message, String callbackType) {
		this.statusCode = statusCode;
		this.message = message;
		this.callbackType = callbackType;
	}


	/**
	 * 返回 statusCode 的值
	 * 
	 * @return statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * 设置 statusCode 的值
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 返回 message 的值
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置 message 的值
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 返回 forwardUrl 的值
	 * 
	 * @return forwardUrl
	 */
	public String getForwardUrl() {
		return forwardUrl;
	}

	/**
	 * 设置 forwardUrl 的值
	 * 
	 * @param forwardUrl
	 */
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}



	/**
	 * 返回 callbackType 的值
	 * 
	 * @return callbackType
	 */
	public String getCallbackType() {
		return callbackType;
	}

	/**
	 * 设置 callbackType 的值
	 * 
	 * @param callbackType
	 */
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	/**
	 * 返回 navTabId 的值
	 * 
	 * @return navTabId
	 */
	public String getNavTabId() {
		return navTabId;
	}

	/**
	 * 设置 navTabId 的值
	 * 
	 * @param navTabId
	 */
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
		this.callbackType = CALLBACK_TYPE_REFRESH_CURRENT;
	}
	
	

	public String getForwardConfirmMsg() {
		return forwardConfirmMsg;
	}

	public void setForwardConfirmMsg(String forwardConfirmMsg) {
		this.forwardConfirmMsg = forwardConfirmMsg;
	}

	public void setData(Page page,List<? extends View> views) {
		if(page != null){
			this.total = page.getTotalCount();
		}
		if(views != null){
			StringBuffer buffer = new StringBuffer("[");
			for (View view : views) {
				buffer.append(view.toJson()).append(",");
			}
			if(buffer.length() > 1){
				buffer.deleteCharAt(buffer.length() -1);
			}
			buffer.append("]");
			this.data = buffer.toString();
		}
		this.callbackType="";
	}
	
	public void setData(View view) {
		this.data = view.toJson();
		this.callbackType="";
	}
	
	public void refreshTab(String navTabId,String message){
		this.callbackType = CALLBACK_TYPE_REFRESH_CURRENT;
		this.statusCode = STATUS_CODE_SUCCESS;
		this.message = message;
		this.navTabId = navTabId;
	}
	
	public void forward(String forwardUrl,String forwardConfirmMsg){
		this.forwardUrl = forwardUrl;
		if(StringUtils.isNotBlank(forwardConfirmMsg)){
			this.forwardConfirmMsg = forwardConfirmMsg;
			this.callbackType = CALLBACK_TYPE_FORWARD_CONFIRM;
		}else{
			this.callbackType = CALLBACK_TYPE_FORWARD;
		}
	}
	
	public void error(String message){
		this.message = message;
		this.statusCode = STATUS_CODE_FAILURE;
		this.callbackType = CALLBACK_TYPE_ERROR;
	}
	
	public void info(String message){
		this.message = message;
		this.statusCode = STATUS_CODE_SUCCESS;
		this.callbackType = CALLBACK_TYPE_INFO;
	}
	
	public void warn(String message){
		this.message = message;
		this.statusCode = STATUS_CODE_SUCCESS;
		this.callbackType = CALLBACK_TYPE_WARN;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		try {
			json.put("statusCode", statusCode);
			json.put("message", message);
			json.put("navTabId", navTabId);
			json.put("callbackType", callbackType);
			json.put("forwardUrl", forwardUrl);
			json.put("forwardConfirmMsg", forwardConfirmMsg);
			json.put("total", total);
			if(StringUtils.isNotBlank(data)){
				if(data.startsWith("{")){
					JSONObject rows = new JSONObject(data);
					json.put("rows", rows);
				}else{
					JSONArray rows = new JSONArray(data);
					json.put("rows", rows);
				}
			}else{
				json.put("rows", new JSONArray());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

}
