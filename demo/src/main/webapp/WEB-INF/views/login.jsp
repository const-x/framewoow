<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=basePath %>/js/formValidator.2.2.1/js/jquery-1.6.min.js" type="text/javascript"></script>
<script>
	jQuery(document).ready(function() {
		// binds form submission and fields to the validation engine
		jQuery("#formID").validationEngine();
	});
	jQuery(document).ready(function() {
		$("#captcha").click(function() {
			$(this).attr("src", "<%=basePath %>/Captcha.jpg");
    		return false;
    	});
    });
    jQuery(document).ready(function(){
    	$("#captcha").click(function(){
    		$(this).attr("src", "<%=basePath %>/Captcha.jpg");
    		return false;
    	});
    });
</script>
<link href="<%=basePath %>/css/login.css" rel="stylesheet"
	type="text/css" media="screen" />
<title>安全管理平台</title>
</head>
<body>
	<div id="login">
		<h1>Welcome to Const.x</h1>
		<form method="post" action="<%=basePath%>/login">
			<p style="color: red; margin-left: 10px; height: 20px;">
				<c:if test="${msg != null }">
						${msg }
					</c:if>
			</p>
			<input type="text" required="required" placeholder="用户名" name="username"></input>
			<input type="password" required="required" placeholder="密码" name="password"></input>
			<button class="but" type="submit">登录</button>
		</form>
	</div>
</body>
</html>
