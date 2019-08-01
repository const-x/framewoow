<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="easyui-layout" data-options="fit:true" >

	<div class="searchPanel" data-options="region:'north',split:true,minHeight:49" >
		<form class="searchform" id="userSearchFm" refDataGrid="userGrid" onsubmit="return submitQuery(this)">
		    <table>
		      <tr>
		        <td>
			        <ul>
				       <li><input class="easyui-textbox" name="username" style="width:100%" data-options="label:'登录名称:'" ></li>
				       <li><input class="easyui-textbox" name="realname" style="width:100%" data-options="label:'真实名称:'" ></li>
				       <li><input class="easyui-combobox" name="organization.name"  style="width:100%" 
				                  data-options="label:'所属组织:',hasDownArrow:false,mode:'remote',textField:'name',valueField:'name'" 
				                    url='<%=basePath %>/security/user/lookup2org'  lookupGroup="organization" lookupfield="name">
				           <input  class="easyui-textbox"  name="organization.id" lookupGroup="organization" lookupfield="id" type="hidden"/></li>
				    </ul>
		        </td>
		        <td class="searchSubmit">
			        <ul><li>
			              <input type='submit' value='查询' iconCls="icon-search" class='easyui-linkbutton'  >
			        </li></ul>
		        </td>
		      </tr>
		    </table>
	    </form>
	</div>
	
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" fit='true' id="userGrid" refForm="userSearchFm"
				data-options="striped:true,url:'<%=basePath %>/security/user/data',method:'POST',toolbar:'#usertoolbar'">
			<thead>
				<tr>
				    <th data-options="field:'id',checkbox:true" >ID</th>
					<th data-options="field:'username',width:150,sortable:'true'" >登录名称</th>
					<th data-options="field:'realname',width:150" >真实名字</th>
					<th data-options="field:'userTypeStr',width:80,align:'center'" >用户类型</th>
					<th data-options="field:'email',width:150" >邮箱地址</th>
					<th data-options="field:'phone',width:150">电话</th>
					<th data-options="field:'organization',width:150,formatter:data2StringFomatter">所属组织</th>
					<th data-options="field:'status',width:80,align:'center'">账户状态</th>
					<th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		<div id="usertoolbar" style="padding:5px;height:auto">
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="User:save">
					<a id="add" iconCls="easy-icon-add" refDataGrid="userGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/user/create" width='530' height='380'
						type="dialog" plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">添加</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="User:edit">
					<a id="edit" iconCls="easy-icon-edit" refDataGrid="userGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/user/update/{id}" width='530'
						height='330' type="dialog" model='selected' plain="true"
						class="easyui-linkbutton" href="javascript:void(0)">编辑</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="User:edit">
					<a id="delete" iconCls="easy-icon-remove" refDataGrid="userGrid"
						confirm="确定要删除选中的用戶吗?" onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/user/delete/{id}" type="ajaxTodo"
						model='all' plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="User:edit">
					<a id="resetPassword" iconCls="easy-icon-refresh"
						refDataGrid="userGrid" confirm="确定要重置选中的密码吗?"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/user/reset/password/{id}"
						type="ajaxTodo" model='selected' plain="true"
						class="easyui-linkbutton" href="javascript:void(0)">重置密码</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="User:edit">
					<a id="resetStatus" iconCls="easy-icon-update-status"
						refDataGrid="userGrid" confirm="确定要重置选中的用户状态吗?"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/user/reset/status/{id}"
						type="ajaxTodo" model='selected' plain="true"
						class="easyui-linkbutton" href="javascript:void(0)">更新状态</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="User:edit">
					<a id="userRole" iconCls="easy-icon-role" model='selected'
						refDataGrid="userGrid" onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/user/lookup2create/userRole/{id}"
						width='530' height='430' type="dialog" plain="true"
						class="easyui-linkbutton" href="javascript:void(0)">分配角色</a>
				</shiro:hasPermission>
			</div>
		</div>
	
	</div>

</div>
<script type="text/javascript">



</script>
















