<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
	<div class="searchPanel positionTop" >
		<form id="organizationSearchFm" id="userSearchFm" refDataGrid="orgGrid"  onsubmit="return submitQuery(this)">
		    <table>
		      <tr>
		        <td>
			        <ul>
				       <li><input class="easyui-textbox" name="name" style="width:100%" data-options="label:'组织名称:'" value="${name}"></li>
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
	<div style="padding:0px" class="dialogDatagrid">
		<table class="easyui-datagrid" fit='true' id="orgGrid" refForm="organizationSearchFm"
				data-options="striped:true,onDblClickRow:bringback,url:'<%=basePath %>/security/user/lookup2org',method:'POST'">
			<thead>
			<tr>
				<th data-options="field:'code',width:150">组织编码</th>
				<th data-options="field:'name',width:150">组织名称</th>
				<th data-options="field:'description',width:250">组织描述</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div class="buttonPanel dialogButtonpanel">
		<input type='submit' value='确定' class='easyui-linkbutton' onclick="bringback( $('#orgGrid').datagrid('getSelected'))">
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>	
	</div>
	
	
</div>	
<script type="text/javascript">
	
</script>