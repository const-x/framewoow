package idv.constx.demo.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.service.BaseServiceImpl;
import idv.constx.demo.common.exception.ExceptionUtils;
import idv.constx.demo.security.dao.PermissionDao;
import idv.constx.demo.security.dao.RoleDao;
import idv.constx.demo.security.entity.Permission;
import idv.constx.demo.security.entity.Role;
import idv.constx.demo.security.service.RoleService;
import idv.constx.demo.security.service.UserRoleService;
import idv.constx.demo.security.shiro.ShiroDbRealm;
import idv.constx.demo.util.dwz.Page;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-7 下午5:04:52 
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private UserRoleService userRoleService; 
	
	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;
	
	@Override
	@Transactional
	public void save(Role role) {
		try {
			Role r = roleDao.findByName(role.getName());
			if(r != null){
				ExceptionUtils.throwBizException("角色名称：" + role.getName() + " 已存在！");
			}
			roleDao.save(role);
			this.saveRolePernission(role);
			super.cache(role);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
	}

	/**   
	 * @param role  
	 */
	@Transactional
	public void update(Role role) {
		try {
			Role r = roleDao.findByName(role.getName());
			if(r != null && r.getId() != role.getId()){
				ExceptionUtils.throwBizException("角色名称：" + role.getName() + " 已存在！");
			}
			roleDao.update(role);
			//permissionDao.deleteByRoleId(role.getId());
			this.saveRolePernission(role);
			shiroRealm.clearAllCachedAuthorizationInfo();
			super.updateInCache(role);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
	}

	private void saveRolePernission(Role role){
		List<String> permissions = role.getPermissionList();
		if(permissions != null && permissions.size() > 0){
			List<Permission> criterias = new ArrayList<Permission>();
			if(role != null) {
				if(permissions != null && permissions.size() > 0) {
					for(String permission : permissions){
						Permission criteria = new Permission();
						criteria.setPermission(permission);
						criteria.setRoleId(role.getId());
						criterias.add(criteria);
					}
				}else {
					Permission criteria = new Permission();
					criteria.setRoleId(role.getId());
					criterias.add(criteria);
				}
			}
			permissionDao.deleteByRoleId(role.getId());
			permissionDao.save(criterias);
		}
	}
	
	
	/**   
	 * @param id  
	 */
	@Transactional
	public void delete(Long id) {
		try {
			permissionDao.deleteByRoleId(id);
			if( userRoleService.countByRoleId(id)>0)
				ExceptionUtils.throwBizException("角色删除失败，请确保角色下无用户存在！");
			
			roleDao.delete(id);
			shiroRealm.clearAllCachedAuthorizationInfo();
			super.delInCache(Role.class, id);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
	}





	@Override
	@Transactional(readOnly=true)
	public Role get(Long id) {
		try {
			Role role = super.getInCache(Role.class, id);
			if(role == null){
				role = roleDao.get(id);
				if(role != null){
					super.cache(role);
				}
			}
			return role;
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Role> findAll(Page page) {
		try {
			return roleDao.findAllPage(page);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Role> findByKeywordPage(Page page, String keywords) {
		try {
			return roleDao.findByKeywordPage(keywords,page);
		} catch (Exception e) {
		    logger.error(e.getLocalizedMessage(),e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	protected BaseDao getDaoByEntryType(Class<? extends IDEntity> clazz) {
		return null;
	}



}
