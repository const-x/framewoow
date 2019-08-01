package idv.constx.demo.security.service;

import java.util.List;
import idv.constx.demo.util.dwz.Page;

import idv.constx.demo.base.service.BaseService;
import idv.constx.demo.security.entity.Module;
import java.io.OutputStream;


/**
 * 
 * @{entryAlias}后台服务
 *
 * @author const.x
 * @since  2016-09-27 17:50:04
 */
public interface ModuleService  extends BaseService<Module,ModuleCondition>{

	List<Module> getByParentId(Page page,Long ... ParentId);
    
	Module getTree();
	
	void exportExcel(ModuleCondition condition, Page page,String selectedFields, OutputStream out);
	
	
}
