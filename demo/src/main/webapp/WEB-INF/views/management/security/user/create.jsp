<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<form method="post" id="user_add_form" action="<%=basePath %>/security/user/create" class="required-validate pageForm" onsubmit="return validateCallback(this);">
<div class="dialogContentpanel">
	<table cellpadding="5" class="fieldsPanel dlgFieldsPanel tdWidth">
   		<tr>
   			<td>登录名称:</td>
   			<td><input class="easyui-textbox" type="text" name="username" data-options="required:true"></input></td>
   			<td><span class="starL"></span></td>
   		</tr>
   		<tr>
   			<td>真实名字:</td>
   			<td><input class="easyui-textbox" type="text" name="realname" data-options="required:true"></input></td>
   			<td><span class="starL"></span></td>
   		</tr>
   		<tr>
   			<td>登录密码:</td>
   			<td><input class="easyui-textbox" type="text" name="plainPassword" value='123456' data-options="required:true"></input></td><td style="text-align:center">默认：123456</td>
   		</tr>
   		<tr>
   			<td>电话:</td>
   			<td><input class="easyui-textbox" name="phone" data-options="validType:'mobile'"></input></td>
   		</tr>
   		<tr>
   			<td>用户邮箱:</td>
   			<td><input class="easyui-textbox" name="email" data-options="validType:'email'"></input></td>
   		</tr>	    		
   		<tr>
   			<td>用户状态:</td>
   			<td>
   				<select class="easyui-combobox" name="status" panelHeight="auto">
   					<option value="enabled">可用</option><option value="disabled">不可用</option>
   				</select>
   			</td>
   		</tr>
   		<tr>
   			<td>用户类型:</td>
   			<td>
   				<select class="easyui-combobox text-Center" name="userType" panelHeight="auto">
   					<option value="1">系统级</option>
					<option value="2">商户级</option>
					<option value="3">门店级</option>
				</select>
   			</td>
   		</tr>
   		<tr>
   			<td>关联组织:</td>
   			<td>
   			   <div class="lookupField">
   			   		<input  class="easyui-textbox"  name="organization.id" lookupGroup="organization" lookupfield="id" type="hidden"/>
					<input class="easyui-textbox required" lookupGroup="organization" lookupfield="name" name="organization.name" type="text" readonly="readonly"/>
					<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" lookupGroup="organization" url='<%=basePath %>/security/user/lookup2org' onclick="lookup(this)"></a>
   			   </div>
   			</td>	
   		</tr>
   		
   	</table>
   	
</div>
 <div class="dialogButtonpanel buttonPanel">  
	<input type='submit' value='确定' class='easyui-linkbutton'  >
	<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>
 </div>
	
</form>
<script type="text/javascript">
</script>