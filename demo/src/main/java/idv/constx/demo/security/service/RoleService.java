package idv.constx.demo.security.service;

import java.util.List;

import idv.constx.demo.security.entity.Role;
import idv.constx.demo.util.dwz.Page;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-7 下午5:04:05 
 */

public interface RoleService {


	void save(Role role);

	void update(Role role);

	Role get(Long id);

	void delete(Long id);


	List<Role> findAll(Page page);

	List<Role> findByKeywordPage(Page page, String keywords);
}
