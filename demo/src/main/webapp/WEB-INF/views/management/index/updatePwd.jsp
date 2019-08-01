<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<form id="updatePwdFm" method="post" action="<%=basePath%>/index/updatePwd" onsubmit='return  validateCallback(this)' class="required-validate" >
	<table style="margin-top:20px;">
		<tr>
			<td> <input class="easyui-textbox" name="oldPassword" type="password"  style="width:100%"  data-options="label:'当前密码:',required:true,labelAlign: 'right'"   validType='password'></td>
		</tr>
		<tr>
			<td><input class="easyui-textbox" id="plainPassword" name="plainPassword" type="password" style="width:100%"  data-options="label:'新密码:',required:true,labelAlign: 'right'" validType='password'></td>
		</tr>
		<tr>
			<td><input class="easyui-textbox" name="rPassword" type="password" style="width:100%"  data-options="label:'确认新密码:',required:true,labelAlign: 'right'" validType="eqPwd['#plainPassword']"></td>
		</tr>
	</table>

	<div class="buttonPanel">
		<input type='submit' value='确定' class='easyui-linkbutton'  >
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>
	</div>
	
</form>
<script type="text/javascript">
</script>
