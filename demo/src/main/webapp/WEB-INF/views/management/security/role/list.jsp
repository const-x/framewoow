<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="easyui-layout" data-options="fit:true" >

	<div class="searchPanel" data-options="region:'north',split:true,minHeight:49" >
		<form class="searchform" id="roleSearchFm" refDataGrid="roleGrid" onsubmit="return submitQuery(this)">
		    <table>
		      <tr>
		        <td>
			        <ul>
			      	 	
				       <li><input class="easyui-textbox" name="name" style="width:100%;" data-options="label:'角色名称：'"  ></li>
				    </ul>
		        </td>
		        <td class="searchSubmit">
		        	<ul><li>
			             <a href="#" class="easyui-linkbutton" style="height:26px" iconCls="icon-search">查询</a>
			        </li></ul>
		        </td>
		      </tr>
		    </table>
	    </form>
	</div>
	
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" fit='true' id="roleGrid" refForm="roleSearchFm"
				data-options="striped:true,url:'<%=basePath %>/security/role/data',method:'POST',toolbar:'#roletoolbar'">
			<thead>
				<tr>
					<th data-options="field:'name',width:150,sortable:'true'" >角色名称</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	
		
		<div id="roletoolbar" style="padding:5px;height:auto">
			<div style="margin-bottom: 5px">
			   <shiro:hasPermission name="Role:view">
					<a id="add" iconCls="easy-icon-add" refDataGrid="roleGrid"
						onClick="toolbarButtonClick(this)" 
						url="<%=basePath%>/security/role/view/{id}" width='950' height='600'
						type="dialog" model='selected' plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">查看</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="Role:save">
					<a id="add" iconCls="easy-icon-add" refDataGrid="roleGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/role/create" width='950' height='600'
						type="dialog" plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">添加</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="Role:edit">
					<a id="edit" iconCls="easy-icon-edit" refDataGrid="roleGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/role/update/{id}" width='950' height='600'
					    type="dialog" model='selected' plain="true"
						class="easyui-linkbutton" href="javascript:void(0)">编辑</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="Role:delete">
					<a id="delete" iconCls="easy-icon-remove" refDataGrid="roleGrid"
						confirm="确定要删除选中的角色吗?" onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/role/delete/{id}" type="ajaxTodo"
						model='selected' plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">删除</a>
				</shiro:hasPermission>
			
			</div>
		</div>
	
	</div>

</div>
<script type="text/javascript">
</script>

