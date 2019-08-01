package idv.constx.demo.base.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import idv.constx.demo.common.exception.BussinessException;
import idv.constx.demo.common.service.DocService;
import idv.constx.demo.common.service.impl.DocServiceImpl;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.shiro.ShiroDbRealm.ShiroUser;

public class BaseControlller {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 *
	 * @author const.x
	 * @createDate 2014年8月30日
	 */
	protected User getCurrentUser(){
		ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		return shiroUser.getUser();
	}
	
	/**
	 * 验证是否有某些权限
	 * 
	 * @return
	 *
	 * @author const.x
	 * @createDate 2014年8月30日
	 */
	protected boolean hasPermission(String ...sn){
		Subject sub = SecurityUtils.getSubject();
		if(sub == null){ 
			return false;
		}
		try{
			sub.checkPermissions(sn);
		}catch(Exception e){
			return false;
		}
		return true;
	}
   
	private static final String ERROR = "common/error/error";// 错误显示页面
	
	protected String alert(String title, Throwable e, Map<String, Object> map) {
		map.put("message", title);
		if (e == null) {
			return ERROR;
		}

		e.printStackTrace();
		if (e instanceof BussinessException) {
			BussinessException biz = (BussinessException) e;
			if(StringUtils.isNotBlank(biz.getMessage())){
				map.put("message", biz.getMessage());
			}
			e = biz.getCause();
		}
		if (e != null) {
			e.printStackTrace();
			StringBuilder sb = new StringBuilder();
			int count = 0;
//			boolean added = false;
			for (StackTraceElement stack : e.getStackTrace()) {
				String msg = filterStackTrace(stack);
				if (msg != null) {
//					if (added) {
//						sb.append(".....<br>");
//					} else {
//						added = true;
//					}
					sb.append(msg).append("<br>");
					count++;
					if (count == 50) {
						sb.append(".....");
						break;
					}
				}
			}
			map.put("details", sb.toString());
			map.put("cause", e.getMessage());
			map.put("exception", e.getClass().getName());
		}
		return ERROR;
	}
    
    private String filterStackTrace(StackTraceElement stack){
    	String msg = stack.toString();
    	
    	if(msg.startsWith("com.sun.proxy.") || msg.startsWith("org.springframework.") 
    			|| msg.startsWith("sun.reflect.") || msg.startsWith("java.lang.reflect.")
    			|| msg.startsWith("org.mybatis.spring.") || msg.startsWith("org.apache.")
    			){
    		return null;
    	}else{
    		return msg;
    	}
    }
    
    
    protected String alertMsg(String title,String message,Map<String, Object> map){
    	map.put("message", message);
    	map.put("details", null);
    	return ERROR;
    }
    
    
    
    private static final String IMAGES = "/common/showImages";
    
    protected String viewPictures(String[] urls,Map<String, Object> map) {
		if(urls == null){
			map.put("images", new String[0]);
		}else{
			map.put("images", urls);
		}
		return IMAGES;
	}
    
    

    private static final String RICHTEXT = "/common/richText";
	
	
    protected String viewRichText(String detials,Map<String, Object> map) {
    	map.put("details", detials);
		return RICHTEXT;
	}
    
    
	@Autowired
	private DocService docService;
	
	
	protected String convertToUrl(String relKey){
		return docService.getUrlByRel(relKey);
	}
	

	protected String[] convertToUrls(String... relKeys) {
		String[] urls = new String[relKeys.length];
		for (int i = 0; i < relKeys.length; i++) {
			urls[i] = docService.getUrlByRel(relKeys[i]);
		}
		return urls;
	}
	
	protected String addRelkey(String cacheKey){
		if(cacheKey.startsWith(DocServiceImpl.CACHE_KEY_PROFIX)){
			return docService.saveRel(cacheKey);
		}else{
			return cacheKey;
		}
	}
	
	protected void relaseRelKeys(String... relKeys){
		for (int i = 0; i < relKeys.length; i++) {
			if(!relKeys[i].startsWith(DocServiceImpl.CACHE_KEY_PROFIX)){
			    docService.delRel(relKeys[i]);
			}
		}
	}
	
	//TODO 富文本内容中的上传文档key值转换
	protected String convertToUrlInContent(String content){
		return content;
	}
	
	protected String addRelkeyInContent(String content){
		return content;
	}
	
	protected void relaseRelKeysInContent(String content){
		
	}
	
} 
