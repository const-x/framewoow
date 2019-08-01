package idv.constx.demo.security.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.yt3p.mng.security.entity.UserSite;
//import com.yt3p.mng.security.service.UserSiteService;
//import com.yt3p.mng.websitemng.service.WebSiteService;





import idv.constx.demo.base.controller.BaseControlller;
import idv.constx.demo.common.RadomUtils;
import idv.constx.demo.security.entity.Organization;
import idv.constx.demo.security.entity.Role;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.entity.UserRole;
import idv.constx.demo.security.exception.ExistedException;
import idv.constx.demo.security.service.OrganizationService;
import idv.constx.demo.security.service.RoleService;
import idv.constx.demo.security.service.UserCondition;
import idv.constx.demo.security.service.UserRoleService;
import idv.constx.demo.security.service.UserService;
import idv.constx.demo.security.view.OrganizationView;
import idv.constx.demo.security.view.UserView;
import idv.constx.demo.util.dwz.AjaxObject;
import idv.constx.demo.util.dwz.Page;

/**
 * 
 * @author const.x Version 1.1.0
 * @since 2012-8-7 下午3:12:23
 */
@Controller
@RequestMapping("/security/user")
public class UserController extends BaseControlller {

	@Autowired
	private UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private OrganizationService organizationService;
	

	private static final String CREATE = "management/security/user/create";
	private static final String UPDATE = "management/security/user/update";
	private static final String LIST = "management/security/user/list";
	private static final String LOOK_UP_ROLE = "management/security/user/assign_role";
	private static final String LOOK_USER_ROLE = "management/security/user/delete_user_role";
	private static final String LOOK_ORG = "management/security/user/lookup_org";

	
	@RequiresPermissions("User:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET})
	public String index(Map<String, Object> map) {
		try {
			return LIST;
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
	}

	@RequiresPermissions("User:view")
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public @ResponseBody String refresh(Page page, UserCondition condition, Map<String, Object> map) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			if(condition == null){
				condition = new UserCondition();
			}
			Organization org = new Organization();
			List<User> users;
		    users = userService.getByCondition(condition,page);
			List<UserView> views = new ArrayList<UserView>();
			for (User user : users) {
				UserView view = new UserView(user);
				views.add(view);
			}
			ajaxObject.setData(page, views);
		} catch (Exception e) {
			ajaxObject.error("用户获取失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}

	@RequiresPermissions("User:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}

	@RequiresPermissions("User:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(User user) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			user.setCreateTime(new Date());
			userService.save(user);
			ajaxObject.refreshTab("info_User", "用户添加成功！");
		} catch (Exception e) {
			ajaxObject.error("用户添加失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		try {
			User user = userService.getById(id);
			//UserView view = new UserView(user);
			map.put("user", user);
			return UPDATE;
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(User user,HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			userService.update(user);
			ajaxObject.refreshTab("info_User", "用户修改成功！");
		} catch (Exception e) {
			ajaxObject.error("用户修改失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}

	@RequiresPermissions("User:delete")
	@RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
	public @ResponseBody
	String delete(@PathVariable String ids) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			String[] cid = ids.split(",");
		    Long[] userIds = new Long[cid.length];
		    for(int i = 0 ; i < cid.length; i++){
		    	userIds[i] = Long.valueOf(cid[i]);
		    }
			userService.delete(userIds);
			ajaxObject.refreshTab("info_User", "用户删除成功！");
		} catch (Exception e) {
			ajaxObject.error("用户删除失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}

	
	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/reset/{type}/{userId}", method = RequestMethod.GET)
	public @ResponseBody
	String reset(@PathVariable String type, @PathVariable Long userId) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			User user = userService.getById(userId);
			if (type.equals("password")) {
				String password = RadomUtils.getRandomString(6);
				user.setPlainPassword(password);
				ajaxObject.info("重置密码成功，默认密码为:" + password);
			} else if (type.equals("status")) {
				if (user.getStatus().equals("enabled")) {
					user.setStatus("disabled");
				} else {
					user.setStatus("enabled");
					ajaxObject.info("更新状态为" + user.getStatus());
				}
			}
			userService.update(user);
		} catch (Exception e) {
			ajaxObject.error("用户更新失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}



	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/lookup2create/userRole/{userId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String listUnassignRole(Map<String, Object> map, @PathVariable Long userId) {
		try {
			Page page = new Page();
			page.setNumPerPage(Integer.MAX_VALUE);

			List<UserRole> userRoles = userRoleService.find(userId);
			List<Role> roles = roleService.findAll(page);
			// 删除已分配roles
			for (UserRole ur : userRoles) {
				if (ur == null) {
					continue;
				}
				for (Role role : roles) {
					if (ur.getRole().getId().equals(role.getId())) {
						roles.remove(role);
						break;
					}
				}
			}
			map.put("userRoles", userRoles);
			map.put("roles", roles);
			map.put("userId", userId);
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		return LOOK_UP_ROLE;
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/create/userRole", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String assignRole(UserRole userRole) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			userRoleService.save(userRole);
		} catch (Exception e) {
			ajaxObject.error("用户更新失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/delete/userRole", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String deleteUserRole(UserRole userRole) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			userRoleService.delete(userRole);
		} catch (Exception e) {
			ajaxObject.error("用户更新失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/lookup2delete/userRole/{userId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String listUserRole(Map<String, Object> map, @PathVariable Long userId) {
		try{
			List<UserRole> userRoles = userRoleService.find(userId);
			map.put("userRoles", userRoles);
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		return LOOK_USER_ROLE;
	}

	@RequiresPermissions(value = { "User:edit", "User:save" })
	@RequestMapping(value = "/lookup2org", method = { RequestMethod.GET })
	public String lookup(Map<String, Object> map) {
		try{
			return LOOK_ORG;
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
	}
	
	@RequiresPermissions(value = { "User:edit", "User:save" })
	@RequestMapping(value = "/lookup2org", method = { RequestMethod.POST })
	public @ResponseBody String lookup(Page page, Organization condition, Map<String, Object> map) {
		AjaxObject ajaxObject = new AjaxObject();
		try {	
			String keywords = condition == null ? null:condition.getName();
			List<Organization> orgs = organizationService.find(null, keywords, page);
			List<OrganizationView> views = new ArrayList<OrganizationView>();
			for (Organization org : orgs) {
				OrganizationView view = new OrganizationView(org);
				views.add(view);
			}
			ajaxObject.setData(page, views);
		} catch (Exception e) {
			ajaxObject.error("用户获取失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}
	


}
