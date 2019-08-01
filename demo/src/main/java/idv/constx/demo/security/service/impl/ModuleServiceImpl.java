package idv.constx.demo.security.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.constx.demo.base.service.BaseServiceImpl;
import idv.constx.demo.common.exception.ExceptionUtils;
import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.security.dao.ModuleDao;
import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.entity.UserTypeEnum;
import idv.constx.demo.security.entity.Module;
import idv.constx.demo.security.service.ModuleCondition;
import idv.constx.demo.security.service.ModuleService;
import idv.constx.demo.security.entity.TypeEnum;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import idv.constx.demo.common.excel.ExcelColumnMeta;
import idv.constx.demo.common.excel.renderer.EnumValueRenderer;

import java.io.OutputStream;
import java.util.ArrayList;

import idv.constx.demo.common.excel.ExcelCreater;
import idv.constx.demo.util.dwz.Page;

/**
 * 
 * 模块管理后台服务
 * 
 * @author const.x
 * @since  2016-09-27 17:50:04
 */
@Service
@Transactional
public class ModuleServiceImpl extends BaseServiceImpl implements ModuleService {


	@Autowired
	ModuleDao moduleDao;


	@Override
	@Transactional
	public Long save(Module module) {
		try {
		    this.beforeSave(module);
		    this.validate(module);
			moduleDao.save(module);
			super.cache(module);
			Long id = module.getId();
			return id;
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
        return null;
	}

	@Override
	@Transactional
	public void delete(Long ...ids) {
		try {
		    List<Module> items = moduleDao.findByIds(ids);
			Module[] array = items.toArray(new Module[0]);
		    this.beforeDelete(array);
		    for (Long id : ids) {
		    	deleteAll(id);
			}
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
	}

	private void deleteAll(Long id){
		List<Module> chs = moduleDao.findByParentId(null, id);
		for (Module module : chs) {
			deleteAll(module.getId());
		}
		moduleDao.delete(id);
		super.delInCache(Module.class, id);
	}
	
    @Override
	@Transactional(readOnly = true)
	public Module getForUpdate(Long id) {
		try {
		    Module module = super.getInCache( Module.class, id);
			if(module == null){
				  module =  moduleDao.findById(id);
			}
		    if(module == null ){
			   ExceptionUtils.throwBizException("所选数据已删除，请刷新界面数据");
			}
			super.cache(module);
		    this.proUpdate(module);
			return module;
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
        return null;
	}

	@Override
	@Transactional
	public void update(Module module) {
		try {
		    this.beforeUpdate(module);
		    this.validate(module);
			moduleDao.update(module);
			super.updateInCache(module);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Module getById(Long id) {
		try {
			Module module = super.getInCache( Module.class, id);
			if(module == null){
				  module =  moduleDao.findById(id);
			}
			super.cache(module);
			return  module;
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
        return null;
	}
	

   
    @Override
	@Transactional(readOnly = true)
	public List<Module> getByIds(Long ...ids) {
		try {
			return moduleDao.findByIds(ids);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
        return null;
	}
   
    @Override
	@Transactional(readOnly = true)
		public List<Module> getByParentId(Page page, Long... ParentId) {
		try {
			return moduleDao.findByParentId(page,ParentId);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
        return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Module> getByCondition(ModuleCondition condition, Page page) {
		try {
		    User user = super.getCurrentUser();
		    Long accountId = null;
		    //如果是系统级用户则允许查询所有数据
			if(user != null && user.getUserType() != UserTypeEnum.SYSTEM.getValue()){
				accountId = user.getId();
			}
			return moduleDao.findByPage(accountId,condition,null, page);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}


    @Override
	protected BaseDao getDaoByEntryType(Class<? extends IDEntity> clazz) {
		if(clazz.equals(Module.class)){
			return this.moduleDao;
		}else{
			return null;
		}
	}
	
	
	protected void validate(Module module) {
		if(module.getName() == null || module.getName().toString().equals("")){
		    ExceptionUtils.throwBizException("模块名称不能为空");
		}

		StringBuilder namewhere = new StringBuilder();
		namewhere.append(" a.NAME = '" ).append(module.getName()).append("'");
		if(module.getId() != null){
			namewhere.append(" and a.id  <> " ).append(module.getId());
		}
		List<Module>  names = this.moduleDao.findFieldsByWherePage(null, null,  namewhere.toString(), null, null);
		if ( names != null &&  names.size() > 0) {
			ExceptionUtils.throwBizException("重复的模块名称：" + module.getName());
		}


	}


	@Override
	@Transactional(readOnly = true)
	public void exportExcel(ModuleCondition condition, Page page,String selectedFields, OutputStream out) {
		try {
			List<ExcelColumnMeta> columns = new ArrayList<ExcelColumnMeta>();
			ExcelColumnMeta column = null;
			String[] exportColumns = StringUtils.split(selectedFields, ",");
			for (String str : exportColumns) {
				switch (str) {
				    case "type":
				        column = new ExcelColumnMeta("type", "模块类型");
				        column.setRenderer(new EnumValueRenderer<Module>(TypeEnum.values()));
				        columns.add(column);
				        break;
			
			    }
			}    
			List<Module> list = this.getByCondition(condition,null);
			ExcelCreater<Module> creater = new ExcelCreater<Module>();
			HSSFWorkbook workbook = creater.create(columns, list, Module.class);
			workbook.write(out);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
		    super.exportError(e, out);
		}
	}

	/**
	 * 
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Module getTree() {
		List<Module> list = moduleDao.findByPage(null, new ModuleCondition() , "PARENT_ID,PRIORITY", null);
		List<Module> rootList = makeTree(list);
		return rootList.get(0);
	}
	
	private List<Module> makeTree(List<Module> list) {
		List<Module> parent = new ArrayList<Module>();
		// get parentId = null;
		for (Module e : list) {
			if (e.getParent() == null && e.getType() == 0) {
				e.setChildren(new ArrayList<Module>(0));
				parent.add(e);
				break;
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		makeChildren(parent, list);

		return parent;
	}

	private void makeChildren(List<Module> parent, List<Module> children) {
		if (children.isEmpty()) {
			return;
		}

		List<Module> tmp = new ArrayList<Module>();
		for (Module c1 : parent) {
			for (Module c2 : children) {
				if(c2.getParent() != null){
					c2.setChildren(new ArrayList<Module>(0));
					if (c1.getId().equals(c2.getParent().getId())) {
						c1.getChildren().add(c2);
						tmp.add(c2);
					}
				}
			}
		}

		children.removeAll(tmp);
		if (tmp != null) {
			makeChildren(tmp, children);
		}
	}

}
