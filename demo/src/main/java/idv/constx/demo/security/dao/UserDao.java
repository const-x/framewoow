package idv.constx.demo.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.service.UserCondition;
import idv.constx.demo.util.dwz.Page;

public interface UserDao extends BaseDao{
	
	void save(User user);
	
	void deleteUserRoles(@Param(value = "userids") Long... userid);
	
	// 根据登录名查找用户
	User findByUsername(@Param(value = "userName") String userName,
			@Param(value = "notId") Long notId);

	// 根据实名查找用户
	User findByRealname(@Param(value = "realname") String realname,
			@Param(value = "notId") Long notId);

	List<User> findByConditionPage(@Param(value = "userId") Long userId,
			@Param(value = "condition") UserCondition condition, Page page);

	List<User> findAllPage(Page page);

	void delete(@Param(value = "ids") Long... ids);


	User get(@Param(value = "id") Long id);
	
	List<User> getByIds(@Param(value = "ids") Long... id);

	void update(User user);

}
