package idv.constx.demo.security.dao;


import java.util.List;



import org.apache.ibatis.annotations.Param;

import idv.constx.demo.security.entity.Role;
import idv.constx.demo.util.dwz.Page;

/**
 * 
 * @author const.x Version 1.1.0
 * @since 2012-8-7 下午5:03:07
 */


public interface RoleDao {
	List<Role> findByNameContainingPage(String name, Page page);

	void save(Role role);

	void delete(Long id);

	Role findByName(@Param(value = "name") String name);

	Role get(Long id);

	List<Role> findAllPage(Page page);

	List<Role> findByKeywordPage(@Param(value = "keywords") String keywords, Page page);

	void update(Role role);


}
