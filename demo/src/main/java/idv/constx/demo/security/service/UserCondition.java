package idv.constx.demo.security.service;


import java.util.Date;
import java.util.List;


import com.google.common.collect.Lists;

import idv.constx.demo.base.service.BaseCondition;
import idv.constx.demo.security.entity.Organization;
import idv.constx.demo.security.entity.UserRole;
 
/**
 * VIP会员
 */ 
public class UserCondition extends  BaseCondition { 

	private static final long serialVersionUID = 1L;

	
	private String realname;

	private String username;
	
	
	
	/**
	 * 使用状态disabled，enabled
	 */
	private String status = "enabled";
	
	private List<UserRole> userRoles = Lists.newArrayList();
	

	/**
	 * 帐号创建时间
	 */
	private Date createTime;
	/**
	 * 所属组织（人力归属）
	 */
	private Organization organization;


	

	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	


	/**  
	 * 返回 realname 的值   
	 * @return realname  
	 */
	public String getRealname() {
		return realname;
	}

	/**  
	 * 设置 realname 的值  
	 * @param realname
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**  
	 * 返回 username 的值   
	 * @return username  
	 */
	public String getUsername() {
		return username;
	}

	/**  
	 * 设置 username 的值  
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	
	/**  
	 * 返回 createTime 的值   
	 * @return createTime  
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * 设置 createTime 的值  
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**  
	 * 返回 status 的值   
	 * @return status  
	 */
	public String getStatus() {
		return status;
	}

	/**  
	 * 设置 status 的值  
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
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
		this.userRoles = userRoles;
	}
	





}