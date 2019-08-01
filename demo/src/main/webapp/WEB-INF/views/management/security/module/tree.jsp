<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="easyui-layout" data-options="fit:true" >

      <div class="searchPanel" data-options="region:'north',split:true,minHeight:49" >
		<form class="searchform"  id="moduleSearchFm" refDataGrid="moduleGrid" onsubmit="return submitQuery(this)">
		    <table>
		      <tr>
		        <td>
			        <ul>
				       <li><input class="easyui-textbox" name="name" style="width:100%" data-options="label:'模块名称：'"  ></li>
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
	
	<div class="treePanel" data-options="region:'west',split:true">
	    <ul class="easyui-tree"  refDataGrid="moduleGrid" data-options="url:'<%=basePath %>/security/module/data',method:'POST'"></ul>
     </div>  
	
     <div data-options="region:'center',border:false">
		<table class="easyui-datagrid" fit='true' id="moduleGrid" refForm="moduleSearchFm" 
				data-options="striped:true,url:'<%=basePath %>/security/module/list',method:'POST',toolbar:'#moduletoolbar'">
			<thead>
				<tr>
					<th data-options="field:'name',width:150," >名称</th>
					<th data-options="field:'typeStr',width:150,sortable:'true'" >类型</th>
					<th data-options="field:'sn',width:150" >SN</th>
					<th data-options="field:'enable',width:60" >是否启用</th>
					<th data-options="field:'priority',sortable:'true',width:60" >优先级</th>
					<th data-options="field:'parentModule.name',width:250" >父模块</th>
					<th data-options="field:'url',width:250" >模块地址</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		
		<div id="moduletoolbar" style="padding:5px;height:auto">
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="Module:save">
					<a id="add" iconCls="easy-icon-add" refDataGrid="moduleGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/organization/module/create" width='550' height='350'
						type="dialog" plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">添加模块</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="Module:saveoperation">
					<a id="add" iconCls="easy-icon-add" refDataGrid="moduleGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/organization/module/createoperation" width='550' height='350'
						type="dialog" plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">添加操作</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="Module:edit">
					<a id="edit" iconCls="easy-icon-edit" refDataGrid="moduleGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/module/update/{id}" width='550' height='320'
					    type="dialog" model='selected' plain="true"
						class="easyui-linkbutton" href="javascript:void(0)">编辑</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="Organization:delete">
					<a id="delete" iconCls="easy-icon-remove" refDataGrid="moduleGrid"
						confirm="确定要删除选中的模块吗?" onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/module/delete/{id}" type="ajaxTodo"
						model='all' plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">删除</a>
				</shiro:hasPermission>
			
			</div>
		</div>
	
	</div>
 
</div>


