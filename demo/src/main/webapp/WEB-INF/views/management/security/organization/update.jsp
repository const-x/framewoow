<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<form method="post" action="<%=basePath %>/security/organization/update" class="required-validate pageForm" onsubmit="return validateCallback(this);">
	<input type="hidden" name="id" value="${organization.id }"/>
	<table cellpadding="5" class="fieldsPanel dlgFieldsPanel tdWidth "  style="padding:20px 0 0;">
	     <tr>
   			<td>上级组织：</td>
   			<td><input class="easyui-textbox" type="text" name="parent.name" value="${organization.parent.name }" readonly='readonly' ></input>
   			<input type="hidden" name="parent.id" value="${organization.parent.id }"/></td>
   		</tr>
	     <tr>
   			<td>编码：</td>
   			<td><input class="easyui-textbox" type="text" name="code"  value="${organization.code }" data-options="required:true"></input></td>
   		</tr>
   		<tr>
   			<td>名称：</td>
   			<td><input class="easyui-textbox" type="text" name="name" value="${organization.name }"  data-options="required:true"></input></td>
   		</tr>
   		<tr>
   			<td>描述：</td>
   			<td><input class="easyui-textbox" type="text" name="description" value="${organization.description }"  data-options="multiline:true,height:80"></input></td>
   		</tr>
	</table>
	<div class="buttonPanel">
		<input type='submit' value='确定' class='easyui-linkbutton'  >
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>
	</div>
</form>