<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="easyui-layout" data-options="fit:true" >

      <div class="searchPanel" data-options="region:'north',split:true,minHeight:49" >
		<form class="searchform"  id="orgSearchFm" refDataGrid="orgGrid" onsubmit="return submitQuery(this)">
		    <table>
		      <tr>
		        <td>
			        <ul>
				       <li><input class="easyui-textbox" name="name" style="width:100%" data-options="label:'组织名称：'"  ></li>
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
	    <ul class="easyui-tree"  refDataGrid="orgGrid" data-options="url:'<%=basePath %>/security/organization/data',method:'POST'"></ul>
     </div>  
	
     <div data-options="region:'center',border:false">
		<table class="easyui-datagrid" fit='true' id="orgGrid" refForm="orgSearchFm" 
				data-options="striped:true,url:'<%=basePath %>/security/organization/list',method:'POST',toolbar:'#orgtoolbar'">
			<thead>
				<tr>
					<th data-options="field:'code',width:150,sortable:'true'" >组织编码</th>
					<th data-options="field:'name',width:150,sortable:'true'" >组织名称</th>
					<th data-options="field:'description',width:250" >描述</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		<div id="orgtoolbar" style="padding:5px;height:auto">
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="Organization:save">
					<a id="add" iconCls="easy-icon-add" refDataGrid="orgGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/organization/create/{parent}" width='550' height='350'
						type="dialog" plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">添加</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="Organization:edit">
					<a id="edit" iconCls="easy-icon-edit" refDataGrid="orgGrid"
						onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/organization/update/{id}" width='550' height='280'
					    type="dialog" model='selected' plain="true"
						class="easyui-linkbutton" href="javascript:void(0)">编辑</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="Organization:delete">
					<a id="delete" iconCls="easy-icon-remove" refDataGrid="orgGrid"
						confirm="确定要删除选中的组织吗?" onClick="toolbarButtonClick(this)"
						url="<%=basePath%>/security/organization/delete/{id}" type="ajaxTodo"
						model='all' plain="true" class="easyui-linkbutton"
						href="javascript:void(0)">删除</a>
				</shiro:hasPermission>
			
			</div>
		</div>
	
	</div>
 
</div>
