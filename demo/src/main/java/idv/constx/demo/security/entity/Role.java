package idv.constx.demo.security.entity;

import java.util.ArrayList;
import java.util.List;

import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.entity.IMapperEntity;
import com.google.common.collect.Lists;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-6-7 上午11:07:45 
 */
public class Role extends IDEntity implements  IMapperEntity {
	
	/** 描述  */
	private static final long serialVersionUID = -5537665695891354775L;
	
	private String name;
	
	private List<String> permissionList = Lists.newArrayList();
	
	private List<UserRole> userRoles = new ArrayList<UserRole>(0);

	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		super.setAttrbute("name", name);
	}

	/**  
	 * 返回 userRoles 的值   
	 * @return userRoles  
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**  
	 * 设置 userRoles 的值  
	 * @param userRoles
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		super.setAttrbute("userRoles", userRoles);
	}

	/**  
	 * 返回 permissionList 的值   
	 * @return permissionList  
	 */
	public List<String> getPermissionList() {
		return permissionList;
	}

	/**  
	 * 设置 permissionList 的值  
	 * @param permissionList
	 */
	public void setPermissionList(List<String> permissionList) {
		super.setAttrbute("permissionList", permissionList);
	}

	@Override
	public String getShowName() {
		return this.name;
	}
	
	
}
