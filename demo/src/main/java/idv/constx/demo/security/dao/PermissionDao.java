package idv.constx.demo.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import idv.constx.demo.security.entity.Permission;
import idv.constx.demo.security.entity.PermissionSearchCriteria;

public interface PermissionDao {
	
	List<String> findPermissionListByRole(PermissionSearchCriteria criteria);
	
	void save(List<Permission> list);
	
	void deleteByRoleId(Long roleid);
	
	
}
