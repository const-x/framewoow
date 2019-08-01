package idv.constx.demo.security.entity;

import java.util.List;

import idv.constx.demo.base.entity.IMapperEntity;
import idv.constx.demo.base.entity.TreeEntity;

import com.google.common.collect.Lists;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-27 下午3:25:15 
 */
public class Organization extends TreeEntity<Organization> implements  IMapperEntity{

	/** 描述  */
	private static final long serialVersionUID = -7324011210610828114L;
	
	private String code;
	
	private String name;
	
	private String description;

	private Organization parent;
	
	
	private List<User> users = Lists.newArrayList();

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
	 * 返回 description 的值   
	 * @return description  
	 */
	public String getDescription() {
		return description;
	}

	/**  
	 * 设置 description 的值  
	 * @param description
	 */
	public void setDescription(String description) {
		super.setAttrbute("description", description);
	}

	/**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public Organization getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(Organization parent) {
		super.setAttrbute("parent", parent);
	}


	/**  
	 * 返回 users 的值   
	 * @return users  
	 */
	public List<User> getUsers() {
		return users;
	}

	/**  
	 * 设置 users 的值  
	 * @param users
	 */
	public void setUsers(List<User> users) {
		super.setAttrbute("users", users);
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		super.setAttrbute("code", code);
	}

	@Override
	public String getShowName() {
		return this.name;
	}
}
