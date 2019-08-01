package idv.constx.demo.security.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.service.BaseServiceImpl;
import idv.constx.demo.common.exception.ExceptionUtils;
import idv.constx.demo.common.exception.ServiceException;
import idv.constx.demo.security.dao.UserDao;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.entity.UserTypeEnum;
import idv.constx.demo.security.exception.ExistedException;
import idv.constx.demo.security.service.UserCondition;
import idv.constx.demo.security.service.UserService;
import idv.constx.demo.security.shiro.ShiroDbRealm;
import idv.constx.demo.security.shiro.ShiroDbRealm.HashPassword;
import idv.constx.demo.util.dwz.Page;

/**
 * 
 * @author const.x Version 1.1.0
 * @since 2012-8-7 下午3:14:29
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;

	@Autowired
	private ShiroDbRealm shiroRealm;
	
	

	@Override
	@Transactional(readOnly = true)
	public User getById(Long id) {
		try {
			User user = super.getInCache(User.class, id);
			if (user == null) {
				user = userDao.get(id);
				if (user != null) {
					super.cache(user);
				}
			}
			return user;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public User getForUpdate(Long id) {
		try {
			User user = this.getById(id);
			if(user == null ){
			   ExceptionUtils.throwBizException("所选数据已删除，请刷新界面数据");
			}
			return user;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getByIds(Long... ids) {
		try {
			return userDao.getByIds(ids);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}
	

	@Override
	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		try {
			User user = super.getInCache(username,User.class);
			if(user == null){
				user = userDao.findByUsername(username, null);
				super.cache(username, user);
			}
			return user;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getByCondition(UserCondition condition, Page page) {
		try {
			Long userId = null;
			User user = super.getCurrentUser();
			if (user != null
					&& user.getUserType() != UserTypeEnum.SYSTEM.getValue()) {
				userId = user.getId();
			}
			return userDao.findByConditionPage(userId, condition, page);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	@Transactional
	public Long save(User user) {
		try {
			if (userDao.findByUsername(user.getUsername(), null) != null) {
				throw new ExistedException("用户添加失败，登录名：" + user.getUsername()
						+ "已存在。");
			}

			if (userDao.findByRealname(user.getRealname(), null) != null) {
				throw new ExistedException("用户添加失败，真实名：" + user.getRealname()
						+ "已存在。");
			}

			// 设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
			if (StringUtils.isNotBlank(user.getPlainPassword())
					&& shiroRealm != null) {
				HashPassword hashPassword = shiroRealm.encrypt(user
						.getPlainPassword());
				user.setSalt(hashPassword.salt);
				user.setPassword(hashPassword.password);
			}
			userDao.save(user);
			shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
			super.cache(user);
			return user.getId();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
			return null;
		}
	}

	@Override
	@Transactional
	public void delete(Long... ids) {
		try {
			for (Long id : ids) {
				if (isSupervisor(id)) {
					logger.warn("操作员"+super.getCurrentUser().getRealname()+"，尝试删除超级管理员用户", SecurityUtils.getSubject()
							.getPrincipal() + "。");
					throw new ServiceException("不能删除超级管理员用户。");
				}
				super.delInCache(User.class, id);
			}
			userDao.deleteUserRoles(ids);
			userDao.delete(ids);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
	}

	@Override
	@Transactional
	public void update(User user) {
		try {
			if (userDao.findByUsername(user.getUsername(), user.getId()) != null) {
				throw new ExistedException("用户添加失败，登录名：" + user.getUsername()
						+ "已存在。");
			}

			if (userDao.findByRealname(user.getRealname(), user.getId()) != null) {
				throw new ExistedException("用户添加失败，真实名：" + user.getRealname()
						+ "已存在。");
			}
			// if (isSupervisor(user.getId())) {
			// logger.warn("操作员{},尝试修改超级管理员用户",
			// SecurityUtils.getSubject().getPrincipal());
			// throw new ServiceException("不能修改超级管理员用户");
			// }
			// 设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
			if (StringUtils.isNotBlank(user.getPlainPassword())
					&& shiroRealm != null) {
				HashPassword hashPassword = shiroRealm.encrypt(user
						.getPlainPassword());
				user.setSalt(hashPassword.salt);
				user.setPassword(hashPassword.password);
			}else{
				User orgi = this.getById(user.getId());
				user.setSalt(orgi.getSalt());
				user.setPassword(orgi.getPassword());
			}
			userDao.update(user);
			shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
			super.updateInCache(user);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
	}
	



	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == User.ADMIN_ID;
	}



	@Override
	protected BaseDao getDaoByEntryType(Class<? extends IDEntity> clazz) {
		return userDao;
	}

	

}
