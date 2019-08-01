package idv.constx.demo.security.entity;

import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.entity.IMapperEntity;

public class UserRole extends IDEntity implements  IMapperEntity {
	
	/** 描述  */
	private static final long serialVersionUID = -8888778227379780116L;
	
	/**
	 * 值越小，优先级越高
	 */
	private Integer priority = 99;
	
	private Role role;
	
	private User user;

	/**  
	 * 返回 role 的值   
	 * @return role  
	 */
	public Role getRole() {
		return role;
	}

	/**  
	 * 设置 role 的值  
	 * @param role
	 */
	public void setRole(Role role) {
		super.setAttrbute("role", role);
	}

	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	public User getUser() {
		return user;
	}

	/**  
	 * 设置 user 的值  
	 * @param user
	 */
	public void setUser(User user) {
		super.setAttrbute("user", user);
	}

	/**  
	 * 返回 priority 的值   
	 * @return priority  
	 */
	public Integer getPriority() {
		return priority;
	}

	/**  
	 * 设置 priority 的值  
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		super.setAttrbute("priority", priority);
	}

	@Override
	public String getShowName() {
		return this.role.getName();
	}
	
}
