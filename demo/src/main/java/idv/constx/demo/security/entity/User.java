package idv.constx.demo.security.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.entity.IMapperEntity;
import com.google.common.collect.Lists;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-8-2 下午2:44:58 
 */
public class User extends IDEntity implements  IMapperEntity {
	
	public static final Long ADMIN_ID = 1l;

	/** 描述  */
	private static final long serialVersionUID = -4277639149589431277L;
	
	private String realname;

	private String username;
	
	private String password;
	
	private String plainPassword;
	
	private String salt;
	
	private String phone;
	
	private String email;
	
	private Integer userType;
	
	private String userTypeStr;
	
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


	/**
	 * @return the userType
	 *
	 * @author const.x
	 * @createDate 2014年9月4日
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 *
	 * @author const.x
	 * @createDate 2014年9月4日
	 */
	public void setUserType(Integer userType) {
		super.setAttrbute("userType", userType);
		userTypeStr = UserTypeEnum.getEnumByInt(userType).getName();
	}

	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		super.setAttrbute("organization", organization);
	}
	
	

	public String getUserTypeStr() {
		return userTypeStr;
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
		super.setAttrbute("realname", realname);
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
		super.setAttrbute("username", username);
	}

	/**  
	 * 返回 password 的值   
	 * @return password  
	 */
	public String getPassword() {
		return password;
	}

	/**  
	 * 设置 password 的值  
	 * @param password
	 */
	public void setPassword(String password) {
		super.setAttrbute("password", password);
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
		super.setAttrbute("createTime", createTime);
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
		super.setAttrbute("status", status);
	}

	/**  
	 * 返回 plainPassword 的值   
	 * @return plainPassword  
	 */
	public String getPlainPassword() {
		return plainPassword;
	}

	/**  
	 * 设置 plainPassword 的值  
	 * @param plainPassword
	 */
	public void setPlainPassword(String plainPassword) {
		super.setAttrbute("plainPassword", plainPassword);
	}

	/**  
	 * 返回 salt 的值   
	 * @return salt  
	 */
	public String getSalt() {
		return salt;
	}

	/**  
	 * 设置 salt 的值  
	 * @param salt
	 */
	public void setSalt(String salt) {
		super.setAttrbute("salt", salt);
	}
	
	/**  
	 * 返回 email 的值   
	 * @return email  
	 */
	public String getEmail() {
		return email;
	}

	/**  
	 * 设置 email 的值  
	 * @param email
	 */
	public void setEmail(String email) {
        super.setAttrbute("email", email);
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
	 * 返回 phone 的值   
	 * @return phone  
	 */
	public String getPhone() {
		return phone;
	}

	/**  
	 * 设置 phone 的值  
	 * @param phone
	 */
	public void setPhone(String phone) {
		super.setAttrbute("phone", phone);
	}
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


	@Override
	public String getShowName() {
		return this.realname;
	}


	
	
	
}
