package idv.constx.demo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.constx.demo.security.dao.UserRoleDao;
import idv.constx.demo.security.entity.UserRole;
import idv.constx.demo.security.service.UserRoleService;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-7 下午5:09:50 
 */
@Service
@Transactional
public class UserRoleServiceImpl  implements UserRoleService {
	@Autowired
	private UserRoleDao userRoleDao;
	
	/**   
	 * @param userId
	 * @return  
	 */
	@Transactional(readOnly=true)
	public List<UserRole> find(Long userId) {
		return userRoleDao.findByUserId(userId);
	}

	@Override
	@Transactional
	public void save(UserRole userRole) {
		userRoleDao.save(userRole);
	}

	@Override
	@Transactional
	public void delete(UserRole userRole) {
		userRoleDao.deleteByRole(userRole);
	}

	@Override
	public Integer countByRoleId(Long roleId) {
		return userRoleDao.countByRoleId(roleId);
	}

	
	
	
}
