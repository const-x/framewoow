package idv.constx.demo.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import idv.constx.demo.security.entity.UserRole;

/**
 * 
 * @author const.x Version 1.1.0
 * @since 2012-8-7 下午5:08:15
 */

public interface UserRoleDao {
	List<UserRole> findByUserId(@Param(value = "userId")Long userId);

	void save(UserRole userRole);

	void delete(@Param(value = "userRoleId")Long userRoleId);

	void deleteByRole(UserRole userRole);
	
	Integer countByRoleId(@Param(value = "roleId")Long roleId);
	
}
