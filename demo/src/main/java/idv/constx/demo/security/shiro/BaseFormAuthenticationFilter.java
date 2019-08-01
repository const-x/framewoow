package idv.constx.demo.security.shiro;

import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.shiro.ShiroDbRealm.ShiroUser;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-10-29 上午9:37:02 
 */

public class BaseFormAuthenticationFilter extends FormAuthenticationFilter {

	/**
	 * 覆盖默认实现，用sendRedirect直接跳出框架，以免造成js框架重复加载js出错。
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception  
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
		//issueSuccessRedirect(request, response);
		//we handled the success redirect directly, prevent the chain from continuing:
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
				|| request.getParameter("ajax") == null) {// 不是ajax请求
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
		} else {
			httpServletRequest.getRequestDispatcher("/login/timeout/success").forward(httpServletRequest, httpServletResponse);
		}
		
		return false;
	}
	

	@Override
	 protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        if (isLoginRequest(request, response)){
            if (isLoginSubmission(request, response)){
                //本次用户登陆账号
                String account = this.getUsername(request);
                Subject subject = this.getSubject(request, response);
                //之前登陆的用户
                ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
                //如果两次登陆的用户不一样，则先退出之前登陆的用户
                if (account != null && shiroUser != null && !account.equals(shiroUser.getUser().getUsername())){
                    subject.logout();
                }
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
