<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<form method="post" action="<%=basePath %>/security/organization/create" class="required-validate pageForm" onsubmit="return validateCallback(this);">
	<table cellpadding="5" class="fieldsPanel dlgFieldsPanel">
	     <tr>
   			<td>上级组织：</td>
   			<td><input class="easyui-textbox" type="text" name="parent.name" value="${parentOrganization.name }" readonly='readonly' ></input>
   			    <input type="hidden" name="parent.id" value="${parentOrganization.id }"/></td>
   		</tr>
	     <tr>
   			<td>编码：</td>
   			<td><input class="easyui-textbox" type="text" name="code" data-options="required:true"></input></td>
   		</tr>
   		<tr>
   			<td>名称：</td>
   			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
   		</tr>
   		<tr>
   			<td>描述：</td>
   			<td><input class="easyui-textbox" type="text" name="description" data-options="multiline:true"></input></td>
   		</tr>
	</table>
   <div class="buttonPanel">
		<input type='submit' value='确定' class='easyui-linkbutton'  >
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>
	</div>
</form>