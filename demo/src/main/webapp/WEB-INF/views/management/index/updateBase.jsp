<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<form method="post" id="updateBaseFm" action="<%=basePath %>/index/updateBase" onsubmit='return validateCallback(this)'  >

	<table style="margin-top:20px;">
		<tr>
			<td><input class="easyui-textbox" name="username" style="width:100%" data-options="label:'登录账号:',required:true,labelAlign: 'right'" validType='username'  value="${user.username }"></td>
		</tr>
		<tr>
			<td><input class="easyui-textbox" name="realname" style="width:100%" data-options="label:'真实姓名:',required:true,labelAlign: 'right'" value="${user.realname }"></td>
		</tr>
		<tr>
			<td><input class="easyui-textbox" name="phone" style="width:100%" data-options="label:'电     话:',labelAlign: 'right'" validType='mobile' value="${user.phone }" ></td>
		</tr>
		<tr>
			<td><input class="easyui-textbox" name="email" style="width:100%" data-options="label:'邮     箱:',labelAlign: 'right'" validType='email' value="${user.email }" ></td>
		</tr>
	</table>
	

			
	<div class="buttonPanel">
		<input type='submit' value='确定' class='easyui-linkbutton'  >
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>
	</div>
</form>
<script type="text/javascript">
</script>
