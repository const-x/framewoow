package idv.constx.demo.security.service;


import idv.constx.demo.base.service.BaseService;
import idv.constx.demo.security.entity.User;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-7 下午3:03:59 
 */

public interface UserService  extends BaseService<User,UserCondition>{

	User getByUsername(String username);
	


}
