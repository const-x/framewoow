package idv.constx.demo.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import idv.constx.demo.base.controller.BaseControlller;
import idv.constx.demo.base.view.TreeView;
import idv.constx.demo.common.exception.ServiceException;
import idv.constx.demo.security.entity.Organization;
import idv.constx.demo.security.service.OrganizationService;
import idv.constx.demo.security.view.OrganizationView;
import idv.constx.demo.util.dwz.AjaxObject;
import idv.constx.demo.util.dwz.Page;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-27 下午4:10:26 
 */
@Controller
@RequestMapping("/security/organization")
public class OrganizationController  extends BaseControlller{
	@Autowired
	private OrganizationService organizationService;
	
	private static final String CREATE = "management/security/organization/create";
	private static final String UPDATE = "management/security/organization/update";
	private static final String TREE = "management/security/organization/tree";
	
	
	
	@RequiresPermissions("Organization:view")
	@RequestMapping(value="/tree", method=RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		try {
			return TREE;
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		
	}
	
	@RequiresPermissions("Organization:view")
	@RequestMapping(value="/data", method=RequestMethod.POST)
	public @ResponseBody String refresh(Long parentId,Map<String, Object> map) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			Organization organization = organizationService.getTree();
			TreeView view = new TreeView(organization);
			ajaxObject.setData( view);
		} catch (Exception e) {
			ajaxObject.error("组织获取失败："+e.getMessage());
		}
		return ajaxObject.toString();
		
	}
	
	@RequiresPermissions("Organization:view")
	@RequestMapping(value="/list", method={RequestMethod.POST})
	public @ResponseBody String list(Page page, Organization condition, 
			Map<String, Object> map, HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			if(condition == null){
				condition = new Organization();
			}
			List<Organization> organizations = organizationService.getByCondition(condition, page);
			List<OrganizationView> views = new ArrayList<OrganizationView>(organizations.size());
			for (Organization  org : organizations) {
				OrganizationView view = new OrganizationView(org);
				views.add(view);
			}
			ajaxObject.setData(page, views);
		} catch (Exception e) {
			ajaxObject.error("组织获取失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}
	
	
	
	@RequiresPermissions("Organization:save")
	@RequestMapping(value="/create/{parentId}", method=RequestMethod.GET)
	public String preCreate(@PathVariable Long parentId,Map<String, Object> map) {
		try {
			Organization parent = organizationService.get(parentId);
			map.put("parentOrganization", parent);
			return CREATE;
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
	}
	
	@RequiresPermissions("Organization:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(Organization organization, HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			organizationService.save(organization);
			ajaxObject.refreshTab("info_Organization", "组织新增成功！");
		} catch (Exception e) {
			ajaxObject.error("组织新增失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Organization:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		try {
			Organization organization = organizationService.get(id);
			map.put("organization", organization);
			return UPDATE;
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
	}
	
	@RequiresPermissions("Organization:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(Organization organization) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			organizationService.update(organization);
			ajaxObject.refreshTab("info_Organization", "组织修改成功！");
		} catch (Exception e) {
			ajaxObject.error("组修改增失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Organization:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public @ResponseBody String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			organizationService.delete(id);
			ajaxObject.refreshTab("info_Organization", "组织刪除成功！");
		} catch (Exception e) {
			ajaxObject.error("组织刪除增失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}
	
	
	

	
}
