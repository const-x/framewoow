package idv.constx.demo.security.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import idv.constx.demo.base.controller.BaseControlller;
import idv.constx.demo.base.service.BaseService;
import idv.constx.demo.security.SecurityConstants;
import idv.constx.demo.security.entity.Module;
import idv.constx.demo.security.entity.ModuleTypeEnum;
import idv.constx.demo.security.entity.PermissionSearchCriteria;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.entity.UserRole;
import idv.constx.demo.security.service.ModuleService;
import idv.constx.demo.security.service.PermissionService;
import idv.constx.demo.security.service.UserRoleService;
import idv.constx.demo.security.service.UserService;
import idv.constx.demo.security.shiro.ShiroDbRealm;
import idv.constx.demo.util.dwz.AjaxObject;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-2 下午5:45:57 
 */
@Controller
@RequestMapping("/index")
public class IndexController  extends BaseControlller {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private PermissionService permissionService;
	
	private static final String INDEX = "management/index/index";
	private static final String UPDATE_PASSWORD = "management/index/updatePwd";
	private static final String UPDATE_BASE = "management/index/updateBase";
	
	@RequiresAuthentication 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(HttpServletRequest request, Map<String, Object> map) {
		try {
			Subject subject = SecurityUtils.getSubject();
			ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser)subject.getPrincipal();
			
			//User user = userService.get(shiroUser.getLoginName());
			List<UserRole> userRoles = userRoleService.find(shiroUser.getId());
			shiroUser.getUser().setUserRoles(userRoles);
			
			Module menuModule = getMenuModule(userRoles);
	
			// 这个是放入user还是shiroUser呢？
			request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());
			request.setAttribute("menuModule", menuModule);
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		return INDEX;
	}
	
	private Module getMenuModule(List<UserRole> userRoles) {
		// 得到所有权限
		Set<String> permissionSet = Sets.newHashSet();
		for (UserRole userRole : userRoles) {
			PermissionSearchCriteria criteria = new PermissionSearchCriteria();
			criteria.setRoleId(userRole.getRole().getId());
			List<String> list = permissionService.getPermissionListByRole(criteria);
			Set<String> tmp = Sets.newHashSet(list);
			permissionSet.addAll(tmp);
		}
		
		// 组装菜单,只获取二级菜单
		//Module rootModule = moduleService.get(1L);
		Module rootModule = moduleService.getTree();
		List<Module> list1 = Lists.newArrayList();
		for (Module m1 : rootModule.getChildren()) {
			if(m1.getType().equals(ModuleTypeEnum.OPERATION.getValue())){
				continue;
			}
			// 过滤掉停用的module
			if(m1.getEnable() == 0){
				continue;
			}
			// 只加入拥有view权限的Module
			if (permissionSet.contains(m1.getSn() + ":" + SecurityConstants.OPERATION_VIEW)) {
				list1.add(m1);
				List<Module> list2 = Lists.newArrayList();
				for (Module m2 : m1.getChildren()) {
					if(m2.getType().equals(ModuleTypeEnum.OPERATION.getValue())){
						continue;
					}
					// 只加入拥有view权限的Module
					if (permissionSet.contains(m2.getSn() + ":" + SecurityConstants.OPERATION_VIEW)) {
						list2.add(m2);
					}
				}
				m1.setChildren(list2);
			}
		}
		rootModule.setChildren(list1);
		
		return rootModule;
	}

	
	@RequestMapping(value="/updatePwd", method=RequestMethod.GET)
	public String updatePassword() {
		return UPDATE_PASSWORD;
	}
	
	@RequestMapping(value="/updatePwd", method=RequestMethod.POST)
	public @ResponseBody String updatePassword(HttpServletRequest request, String oldPassword, 
			String plainPassword, String rPassword) {
		AjaxObject ajaxObject = new AjaxObject();
		try{
			User user = (User)request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
			if (!plainPassword.equals(oldPassword)) {
				user.setPlainPassword(plainPassword);
				userService.update(user);
				ajaxObject.info("密码修改成功！" );
			}else{
				ajaxObject.error("密码修改失败：新密码不能与原密码相同");
			}
		} catch (Exception e) {
			ajaxObject.error("密码修改失败！：" + e.getMessage());
		}
		return ajaxObject.toString();
	}
	
	@RequestMapping(value="/updateBase", method=RequestMethod.GET)
	public String preUpdate() {
		return UPDATE_BASE;
	}
	
	@RequestMapping(value="/updateBase", method=RequestMethod.POST)
	public @ResponseBody String update(User user, HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			User loginUser = (User) request.getSession().getAttribute(
					SecurityConstants.LOGIN_USER);
			loginUser.setPhone(user.getPhone());
			loginUser.setEmail(user.getEmail());
			userService.update(loginUser);
			ajaxObject.info("详细信息修改成功！");
		} catch (Exception e) {
			ajaxObject.error("详细信息修改失败！：" + e.getMessage());
		}
		return ajaxObject.toString();
	}

}
