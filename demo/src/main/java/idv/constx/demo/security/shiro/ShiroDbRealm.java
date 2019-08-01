package idv.constx.demo.security.shiro;

import java.io.Serializable;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import idv.constx.demo.common.encode.Digests;
import idv.constx.demo.common.encode.Encodes;
import idv.constx.demo.common.exception.ExceptionUtils;
import idv.constx.demo.security.entity.PermissionSearchCriteria;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.entity.UserRole;
import idv.constx.demo.security.service.PermissionService;
import idv.constx.demo.security.service.UserRoleService;
import idv.constx.demo.security.service.UserService;

/**
 * shiro权限认证
 * 
 * @author const.x Version 1.1.0
 * @since 2012-8-2 下午3:09:50
 */

public class ShiroDbRealm extends AuthorizingRealm {
	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";

	@Autowired
	protected UserService userService;

	@Autowired
	protected UserRoleService userRoleService;

	@Autowired
	protected PermissionService permissionService;

	/**
	 * 给ShiroDbRealm提供编码信息，用于密码密码比对 描述
	 */
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				ALGORITHM);
		matcher.setHashIterations(INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		try {
			/**
			 * CaptchaUsernamePasswordToken token =
			 * (CaptchaUsernamePasswordToken) authcToken;
			 * 
			 * String parm = token.getCaptcha(); String c =
			 * (String)SecurityUtils.getSubject().getSession()
			 * .getAttribute(SimpleCaptchaServlet.CAPTCHA_KEY); // 忽略大小写 if
			 * (!parm.equalsIgnoreCase(c)) { throw new
			 * IncorrectCaptchaException("验证码错误！"); }
			 **/
			UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
			User user = userService.getByUsername(token.getUsername());
			if (user != null) {
				if (user.getStatus().equals("disabled")) {
					throw new DisabledAccountException();
				}

				byte[] salt = Encodes.decodeHex(user.getSalt());
				byte[] password = Encodes.decodeHex(user.getPassword());

				ShiroUser shiroUser = new ShiroUser(user.getId(),
						user.getUsername(), user);
				return new SimpleAuthenticationInfo(shiroUser,
						ByteSource.Util.bytes(password),
						ByteSource.Util.bytes(salt), getName());
			} else {
				return null;
			}
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		try {
			ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName())
					.iterator().next();
			List<UserRole> userRoles = userRoleService.find(shiroUser.getId());
			shiroUser.getUser().setUserRoles(userRoles);

			if (!userRoles.isEmpty()) {
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				for (UserRole userRole : userRoles) {
					// 基于Permission的权限信息
					PermissionSearchCriteria criteria = new PermissionSearchCriteria();
					criteria.setRoleId(userRole.getRole().getId());

					info.addStringPermissions(permissionService
							.getPermissionListByRole(criteria));
				}
				return info;
			} else {
				return null;
			}
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	public static class HashPassword {
		public String salt;
		public String password;
	}

	public HashPassword encrypt(String plainText) {
		HashPassword result = new HashPassword();
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		result.salt = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(plainText.getBytes(), salt,
				INTERATIONS);
		result.password = Encodes.encodeHex(hashPassword);
		return result;

	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 设置 userRoleService 的值
	 * 
	 * @param userRoleService
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -1748602382963711884L;
		private Long id;
		private String loginName;
		private User user;

		public ShiroUser() {

		}

		/**
		 * 构造函数
		 * 
		 * @param id
		 * @param loginName
		 * @param email
		 * @param createTime
		 * @param status
		 */
		public ShiroUser(Long id, String loginName, User user) {
			this.id = id;
			this.loginName = loginName;
			this.user = user;
		}

		/**
		 * 返回 id 的值
		 * 
		 * @return id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * 返回 loginName 的值
		 * 
		 * @return loginName
		 */
		public String getLoginName() {
			return loginName;
		}

		/**
		 * 返回 user 的值
		 * 
		 * @return user
		 */
		public User getUser() {
			return user;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}
	}
}
