package idv.constx.demo.security.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import idv.constx.demo.base.controller.BaseControlller;
import idv.constx.demo.security.entity.Module;
import idv.constx.demo.security.entity.ModuleTypeEnum;
import idv.constx.demo.security.entity.PermissionSearchCriteria;
import idv.constx.demo.security.entity.Role;
import idv.constx.demo.security.entity.RoleModulePermission;
import idv.constx.demo.security.service.ModuleService;
import idv.constx.demo.security.service.PermissionService;
import idv.constx.demo.security.service.RoleService;
import idv.constx.demo.security.view.RoleView;
import idv.constx.demo.util.dwz.AjaxObject;
import idv.constx.demo.util.dwz.Page;

/**
 * 
 * @author const.x Version 1.1.0
 * @since 2012-8-7 下午5:44:13
 */
@Controller
@RequestMapping("/security/role")
public class RoleController extends BaseControlller {

	@Autowired
	private RoleService roleService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	protected PermissionService permissionService;

	private static final String CREATE = "management/security/role/create";
	private static final String UPDATE = "management/security/role/update";
	private static final String LIST = "management/security/role/list";
	private static final String VIEW = "management/security/role/view";
	
	@RequiresPermissions("Role:view")
	@RequestMapping(value = "/list", method = {RequestMethod.GET })
	public String list(Map<String, Object> map) {
		try {
			return LIST;
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
	}
	
	@RequiresPermissions("Role:view")
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public @ResponseBody  String refresh(Role condition,Page page, Map<String, Object> map) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			List<Role> roles = null;
			if(condition != null){
				roles = roleService.findByKeywordPage(page, condition.getName());
			} else {
				roles = roleService.findAll(page);
			}
			List<RoleView> views = new ArrayList<RoleView>();
			for (Role role :roles) {
				views.add(new RoleView(role));
			}
			ajaxObject.setData(page, views);
		} catch (Exception e) {
			ajaxObject.error("角色获取失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}

	@RequiresPermissions("Role:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		try {
			Module module = moduleService.getTree();
			RoleModulePermission rmp = this.convert(null, module, null, null);
			map.put("module", rmp);
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		return CREATE;
	}

	// 重新组装PermissionList（切分test:save,test:edit的形式）
	private void refactor(Role role) {
		List<String> allList = Lists.newArrayList();
		List<String> list = role.getPermissionList();
		for (String string : list) {
			if (StringUtils.isBlank(string)) {
				continue;
			}

			if (string.contains(",")) {
				String[] arr = string.split(",");
				allList.addAll(Arrays.asList(arr));
			} else {
				allList.add(string);
			}
		}
		role.setPermissionList(allList);
	}

	@RequiresPermissions("Role:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(Role role) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			refactor(role);
			roleService.save(role);
			ajaxObject.refreshTab("info_Role", "角色添加成功！");
		} catch (Exception e) {
			ajaxObject.error("角色添加失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}

	@RequiresPermissions("Role:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		try {
			Role role = roleService.get(id);
			Module module = moduleService.getTree();
			RoleModulePermission rmp = this.convert(role, module, null, null);
			map.put("module", rmp);
			map.put("role", role);
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		return UPDATE;
	}

	@RequiresPermissions("Role:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(Role role) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			refactor(role);
			roleService.update(role);
			ajaxObject.refreshTab("info_Role", "角色修改成功！");
		} catch (Exception e) {
			ajaxObject.error("角色修改失败：" + e.getMessage());
		}
		return ajaxObject.toString();

	}

	private RoleModulePermission convert(Role role, Module module,
			RoleModulePermission parent, List<String> permissions) {
		if (module.getEnable() == 0) {
			return null;
		}
		if (permissions == null) {
			if (role != null) {
				PermissionSearchCriteria criteria = new PermissionSearchCriteria();
				criteria.setRoleId(role.getId());
				permissions = permissionService
						.getPermissionListByRole(criteria);
			} else {
				permissions = new ArrayList<String>(0);
			}
		}

		RoleModulePermission rmp = new RoleModulePermission(module);
		rmp.setParent(parent);

		if (module.getType().equals(ModuleTypeEnum.OPERATION.getValue())) {
			String sn = parent.getSn() + ":" + rmp.getSn();
			if (permissions.contains(sn)) {
				rmp.setHasPermission(RoleModulePermission.HAS_PERMISSION);
			}
		}
		if (module.getChildren() != null) {
			List<Module> children = new ArrayList<Module>(module.getChildren()
					.size());
			for (Module m : module.getChildren()) {
				RoleModulePermission ch = this.convert(role, m, rmp,
						permissions);
				if (ch != null) {
					children.add(ch);
				}
			}
			rmp.setChildren(children);
		}

		return rmp;
	}

	@RequiresPermissions("Role:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			roleService.delete(id);
			ajaxObject.refreshTab("info_Role", "角色删除成功！");
		} catch (Exception e) {
			ajaxObject.error("角色删除失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}

	

	@RequiresPermissions("Role:permission")
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String preView(@PathVariable Long id, Map<String, Object> map) {
		try {
			Role role = roleService.get(id);
			Module module = moduleService.getTree();
			RoleModulePermission rmp = this.convert(role, module, null, null);
			map.put("module", rmp);
			map.put("role", role);
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		return VIEW;
	}

}
