<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>


<script type="text/javascript">

//<!-- top
jQuery(document).ready(function(){
	
   $(".assignRole").click(assign);
   $(".deleteUserRole").click(del);
	   
   function assign(){
    	var roleId = $(this).attr("id").split("submit_")[1];
    	var priority = $("#priority_" + roleId).val();
    	jQuery.ajax({
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
            url: '<%=basePath %>/security/user/create/userRole?user.id=${userId}&role.id=' + roleId ,
            error: function() { 
            	 		alertMsg.error('分配角色失败！');
            		},
            success: function() { 
						// 删除可分配
            			var $remove = $("#roleRow_" + roleId).remove();
		            	var roleName = $remove.find("td").eq(0).text();
		            	if(roleName != null && roleName != ""){
        		    	  // 添加已分配
        		    	  //console.log('添加已分配' + roleName);
		            	  $("#hasRoles").append("<tr id=\"userRoleRow_"+roleId +"\"><td>" + roleName + "</td><td> <div class=\"button\"><div class=\"buttonContent\"><button id=\"submit_"
            					+ roleId + "\" class=\"deleteUserRole\">删除</button></div></div></td></tr>");
		            	   $(".deleteUserRole").click(del);
		            	}
            		}				
            		
        });	
    }
    
	function del(){
    	var roleId = $(this).attr("id").split("submit_")[1];
    	jQuery.ajax({
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
            url: '<%=basePath %>/security/user/delete/userRole?user.id=${userId}&role.id=' + roleId ,
            error: function() { 
    	 			alertMsg.error('删除角色关联失败！');
    			},
    		success: function() { 
    	    		// 删除已分配
    	    		var $remove =$("#userRoleRow_" + roleId).remove();
    	    		var roleName = $remove.find("td").eq(0).text();
    	    		if(roleName != null && roleName != ""){
    	    			// 添加可分配
    	    			//console.log('添加可分配' + roleName);
    	            	$("#roles").append("<tr id=\"roleRow_"+roleId +"\"><td>" + roleName + "</td><td> <div class=\"button\"><div class=\"buttonContent\"><button id=\"submit_"
            					+ roleId + "\" class=\"assignRole\">分配</button></div></div></td></tr>");
    	            	$(".assignRole").click(assign);
    	    		}
    			}
        });	
	}
    	
  });
//-->
</script>

	<div style="width:100%;height:100%;">
		<table class="easyui-table" style="width:49%;float:left;margin:0 auto;">
			<thead>
			    <tr >
			       <td colspan='2'>
			       <legend>已分配角色</legend>
			       </td>
			    </tr>
				<tr>
					<th width="210">角色名称</th>
					<th width="50">操作</th>
				</tr>
			</thead>
			<tbody id="hasRoles">
				<c:forEach var="item" items="${userRoles}">
				<tr id="userRoleRow_${item.role.id}">
					<td>${item.role.name}</td>
					<td>
					<div class="button"><div class="buttonContent"><button id="submit_${item.role.id}" class="deleteUserRole">删除</button></div></div>
				     </td>   
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<table class="easyui-table" style="width:49%;float:right;margin:0 auto;">
			<thead>
			    <tr >
			       <td colspan='2'>
			       <legend>可分配角色</legend>
			       </td>
			    </tr>
				<tr>
					<th width="210">角色名称</th>
					<th width="50">操作</th>
				</tr>
			</thead>
			<tbody id="roles">
				<c:forEach var="item" items="${roles}">
				<%-- 不显示超级管理员角色，超级管理员只能拥有一个，且id=1 --%>
				<c:if test="${item.id != 1 }">
				<tr id="roleRow_${item.id}">
					<td>${item.name}</td>
					<td>
						<div class="button"><div class="buttonContent"><button id="submit_${item.id}" class="assignRole">分配</button></div></div>
					</td>
				</tr>					
				</c:if>			
				</c:forEach>
			</tbody>
		</table>
	</div>




