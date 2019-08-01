package idv.constx.demo.security.service;

import java.util.List;

import idv.constx.demo.security.entity.UserRole;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-7 下午5:08:51 
 */

public interface UserRoleService {
	
	/**
	 * 根据userId，找到已分配的角色。
	 * 描述
	 * @param userId
	 * @return
	 */
	List<UserRole> find(Long userId);

	void save(UserRole userRole);

	void delete(UserRole userRole );
	
	/**
	 * 计算角色下的用户数，删除角色的时候必须角色下没有用户
	 * @param roleId
	 * @return
	 */
	Integer countByRoleId(Long roleId);
}
