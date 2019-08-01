<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="easyui-layout" data-options="fit:true" >

	
	<div data-options="region:'center',border:false">
		
		
		<div id="usertoolbar" style="padding:5px;height:auto">
			<div style="margin-bottom:5px">
				<a id="delete" iconCls="easy-icon-remove"  confirm="确认要清空缓存?" onClick="toolbarButtonClick(this)" url="<%=basePath %>/security/cacheManage/clear" 
				           type="ajaxTodo" plain="true" class="easyui-linkbutton" href="javascript:void(0)">清空缓存</a>                    
			</div>
		</div>
	
	</div>

</div>
<script type="text/javascript">



</script>











