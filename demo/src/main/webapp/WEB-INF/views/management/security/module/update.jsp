<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<form method="post" action="<%=basePath %>/security/module/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${module.id}"/>
	<input type="hidden" name="parent.id" value="${module.parent.id}"/>
	<input type="hidden" name="type" value="${module.type}"/>
	
<div class="dialogContentpanel">	
	<table cellpadding="5" class="fieldsPanel dlgFieldsPanel tdWidth">
   		<tr>
   			<td>名称:</td>
   			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true,validType:'length[0,32]'" value="${module.name }"></input></td>
   		</tr>
   		<tr>
   			<td>优先级：</td>
   			<td><input class="easyui-textbox" type="text" name="priority" data-options="required:true,validType:['integer','length[0,2]']" value="${module.priority }"></input></td><td style="padding-left:10px;">默认:99</td></td>
   		</tr>
   		<tr>
   			<td>URL：</td>
   			<td><input class="easyui-textbox" type="text" name="url" data-options="required:true,validType:'length[0,255]'" value="${module.url }"></input></td>
   		</tr>
   		<tr>
   			<td>授权名称：</td>
   			<td><input class="easyui-textbox" type="text" name="sn" data-options="required:true,validType:'length[0,32]'" value="${module.sn }"></input></td>
   		</tr>
   		<tr>
   			<td>是否启用:</td>
   			<td>
   				<select class="easyui-combobox" name="enable" panelHeight="48">
   					<option value="1">正常</option>
					<option value="0" ${module.enable eq '0' ? 'selected':''}>停用</option>
   				</select>
   			</td>
   		</tr>
   		<tr>
   			<td>描述：</td>
   			<td><input class="easyui-textbox" type="text" name="description" data-options="required:true,multiline:true,validType:'length[0,255]'" value="${module.description }"></input></td>
   		</tr>
	
	</table>
</div>
<div class="dialogButtonpanel">	
	<div class="buttonPanel">
		<a href="javascript:void(0)" class='easyui-linkbutton' style="width:80px" onclick='submitform(this)' >确定</a>
		<a href="javascript:void(0)" class='easyui-linkbutton' style="width:80px" onclick='closeCurDialog(event)'>关闭</a>
	</div>
</div>	
</form>