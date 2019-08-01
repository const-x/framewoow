package idv.constx.demo.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import idv.constx.demo.base.view.TreeView;
import idv.constx.demo.base.controller.BaseControlller;
import idv.constx.demo.security.entity.Module;
import idv.constx.demo.security.entity.ModuleTypeEnum;
import idv.constx.demo.security.view.ModuleView;
import idv.constx.demo.security.service.ModuleCondition;
import idv.constx.demo.security.service.ModuleService;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.util.dwz.AjaxObject;
import idv.constx.demo.util.dwz.Page;
import idv.constx.demo.security.entity.UserTypeEnum;



/**
 * 系统设置
 * @author const.x
 * @since  2016-09-27 17:50:04
 */
@Controller
@RequestMapping("/security/module")
public class ModuleController extends BaseControlller{

	
	private static final String CREATE = "management/security/module/create";
	private static final String CREATE_OPERATION = "management/security/module/createoperation";
	private static final String UPDATE = "management/security/module/update";
	private static final String TREE = "management/security/module/tree";
	


	@Autowired
	private ModuleService moduleService;


       //-查询----------------------------------------------------------------------------
	@RequiresPermissions("module:view")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree( Map<String, Object> map) {
		try {
			return TREE;
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		
	}
	
	
	@RequiresPermissions("module:view")
	@RequestMapping(value="/data", method=RequestMethod.POST)
	public @ResponseBody String refresh(Long parentId,Map<String, Object> map) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			Module m = moduleService.getTree();
			TreeView view = new TreeView(m);
			TreeView.setFristNodeOpened(view);
			ajaxObject.setData( view);
		} catch (Exception e) {
			ajaxObject.error("模块获取失败："+e.getMessage());
		}
		return ajaxObject.toString();
		
	}
	

	
	@RequiresPermissions("module:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String list(ModuleCondition condition,Page page, Map<String, Object> map,
			HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			List<Module> modules = moduleService.getByCondition(condition, page);
			List<ModuleView> views = new ArrayList<ModuleView>();
			for (Module module : modules) {
				ModuleView view = new ModuleView(module);
				views.add(view);
			}
			ajaxObject.setData(page, views);
		} catch (Exception e) {
			ajaxObject.error("组织获取失败："+e.getMessage());
		}
		return ajaxObject.toString();
	}
  
     // -新增-------------------------------------------------
	@RequiresPermissions("module:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
	    try {
	        User user = super.getCurrentUser();
		    if(user.getUserType() != UserTypeEnum.SYSTEM.getValue()){
		  	  return super.alert("只有系统级用户才能维护此项数据", null, map);
		    }
        } catch (Exception e) {
			return super.alert("系统错误", e, map);
		}	    
		return CREATE;
	}

	@RequiresPermissions("module:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(Module module, HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			moduleService.save(module);
			ajaxObject.setMessage("业务模块添加成功！");
			ajaxObject.setNavTabId("info_module");
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("业务模块添加失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}

	@RequiresPermissions("Module:saveoperation")
	@RequestMapping(value = "/createoperation", method = RequestMethod.GET)
	public String preCreateOperation(HttpServletRequest request) {
		try {
			Module p = (Module) request.getSession().getAttribute(
					"parentModule");
			// 根目录，权限模块目录，以及已有业务操作下，不允许添加新的业务操作
			if (p.getType().equals(ModuleTypeEnum.ROOT.getValue())) {
				this.getErrorAjax("根目录下不允许添加业务操作");
			} else if (p.getType().equals(ModuleTypeEnum.OPERATION.getValue())) {
				this.getErrorAjax("业务操作下不允许添加业务操作");
			}
		} catch (Exception e) {
			return super.alert("系统错误", e, null);
		}
		return CREATE_OPERATION;
	}

	@RequiresPermissions("Module:saveoperation")
	@RequestMapping(value = "/createoperation", method = RequestMethod.POST)
	public @ResponseBody
	String createOperation(Module operation, HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			Module p = (Module) request.getSession().getAttribute(
					"parentModule");
			operation.setParent(p);
			operation.setType(ModuleTypeEnum.OPERATION.getValue());
			operation.setUrl("#");
			moduleService.save(operation);
			ajaxObject.setMessage("业务模块添加成功！");
			ajaxObject.setNavTabId("info_module");
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("业务模块添加失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}
	
	
	private String getErrorAjax(String message) {
		AjaxObject ajaxObject = new AjaxObject();
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setNavTabId("info_Module");
		ajaxObject.setMessage(message);
       return ajaxObject.toString();
	}
	
    // -修改-------------------------------------------------
	@RequiresPermissions("module:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(HttpServletRequest request,@PathVariable Long id, Map<String, Object> map) {
		try {
		    User user = super.getCurrentUser();
		    if(user.getUserType() != UserTypeEnum.SYSTEM.getValue()){
		  	  return super.alert("只有系统级用户才能维护此项数据", null, map);
		    }		    
			Module module = moduleService.getForUpdate(id);
			ModuleView view = new ModuleView(module);
			map.put("module", view);
		} catch (Exception e) {
			return super.alert("系统错误", e, map);
		}
		return UPDATE;
	}

	@RequiresPermissions("module:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(HttpServletRequest request, Module module) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			moduleService.update(module);
			ajaxObject.setMessage("业务模块修改成功！");
			ajaxObject.setNavTabId("info_module");
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("业务模块修改失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}
    
    // -删除-------------------------------------------------
	@RequiresPermissions("module:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody String delete(@PathVariable Long id,HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			moduleService.delete(id);
			ajaxObject.refreshTab("info_module", "刪除成功！");
		} catch (Exception e) {
			ajaxObject.error("刪除增失败：" + e.getMessage());
		}
		return ajaxObject.toString();
	}


 	

	private static final String LOOK_PARENT = "/security/module/lookup_parent";
	
	@RequiresPermissions(value = { "module:edit", "module:save" })
	@RequestMapping(value = "/lookup2parent", method = { RequestMethod.GET, RequestMethod.POST })
	public String lookup2parent(Page page, Map<String, Object> map, ModuleCondition condition) {
		try {
			if (condition == null) {
				condition = new ModuleCondition();
			}
			List<Module> parents = moduleService.getByCondition(condition, page);
			List<ModuleView> views = new ArrayList<ModuleView>(parents.size());
			for (Module parent : parents) {
				ModuleView view = new ModuleView(parent);
				views.add(view);
			}
			map.put("parents", views);
			map.put("condition", condition);
			map.put("page", page);
		} catch (Exception e) {
			return super.alert("关联失败", e, map);
		}
		return LOOK_PARENT;
	}


  

}
