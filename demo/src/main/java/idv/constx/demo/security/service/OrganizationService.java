package idv.constx.demo.security.service;

import java.util.List;

import idv.constx.demo.base.service.BaseService;
import idv.constx.demo.security.entity.Organization;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.util.dwz.Page;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-27 下午3:56:25 
 */

public interface OrganizationService{
	
	List<Organization> find(Long parentId, Page page);
	
	List<Organization> find(Long parentId, String name, Page page);
	
	Organization getTree();

	void save(Organization organization);

	void update(Organization organization);

	void delete(Long id);

	Organization get(Long parentId);

	List<Organization> getByCondition(Organization condition, Page page);

	List<Organization> getByIds(Long... array);
}
