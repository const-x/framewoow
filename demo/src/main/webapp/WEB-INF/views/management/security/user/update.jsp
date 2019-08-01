<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<form method="post" id="user_edit_form" action="<%=basePath %>/security/user/update" class="required-validate pageForm" onsubmit="return validateCallback(this);">
	<input type="hidden" name="id" value="${user.id}"/>
	<input type="hidden" name="userType" value="${user.userType}"/>
<div class="dialogContentpanel">
	<table cellpadding="5" class="fieldsPanel dlgFieldsPanel tdWidth">
   		<tr>
   			<td>登录名称:</td>
   			<td><input class="easyui-textbox" type="text" name="username" data-options="required:true" readonly="readonly" value="${user.username }"></input></td>
   			<td><span class="starL"></span></td>
   		</tr>
   		<tr>
   			<td>真实名字:</td>
   			<td><input class="easyui-textbox" type="text" name="realname" data-options="required:true" value="${user.realname }"></input></td>
   			<td><span class="starL"></span></td>
   		</tr>
   		<tr>
   			<td>电话:</td>
   			<td><input class="easyui-textbox" name="phone" data-options="" value="${user.phone }"></input></td>
   		</tr>
   		<tr>
   			<td>用户邮箱:</td>
   			<td><input class="easyui-textbox" name="email" data-options="required:false" value="${user.email }"></input></td>
   		</tr>	    		
   		<tr>
   			<td>用户状态:</td>
   			<td>
   				<select class="easyui-combobox" name="status" panelHeight="48">
   					<option value="enabled" ${user.status == "enabled" ? 'selected="selected"' : ''}>正常</option>
					<option value="disabled" ${user.status == "disabled" ? 'selected="selected"' : ''}>停用</option>
   				</select>
   			</td>
   		</tr>
   		
   		<tr>
   			<td>关联组织:</td>
   			<td>
   			   <div class="lookupField">
   			   		<input  class="easyui-textbox"  name="organization.id" lookupGroup="organization" lookupfield="id"  type="hidden" value="${user.organization.id }" />
	   			    <input class="easyui-textbox required" lookupGroup="organization" lookupfield="name" name="organization.name" type="text" value="${user.organization.name }" readonly="readonly"/>
					<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" lookupGroup="organization" url='<%=basePath %>/security/user/lookup2org' onclick="lookup(this)"></a>
				</div>
   			</td>	
   		</tr>
   	</table>
</div>
  <div class="dialogButtonpanel">  
    <div class="buttonPanel">
      <p  style="float:right;text-decoration:none;">
		<input type='submit' value='确定' class='easyui-linkbutton'  >
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>
	  </p>	
	</div>
  </div>	
</form>
<script type="text/javascript">
	
</script>