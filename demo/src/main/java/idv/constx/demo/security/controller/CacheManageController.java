package idv.constx.demo.security.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import idv.constx.demo.security.service.CacheService;
import idv.constx.demo.util.dwz.AjaxObject;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-9-14 上午11:08:15 
 */
@Controller
@RequestMapping("/security/cacheManage")
public class CacheManageController {
	@Autowired
	private CacheService cacheService;
	
	private static final String INDEX = "management/security/cacheManage/index";
	
	@RequiresPermissions("CacheManage:view")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return INDEX;
	}
	
	@RequiresPermissions("CacheManage:edit")
	@RequestMapping(value="/clear", method=RequestMethod.GET)
	public @ResponseBody String clear() {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			cacheService.clearAllCache();
			ajaxObject.info("缓存清除成功");
		} catch (Exception e) {
			ajaxObject.error("缓存清除失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}
}
