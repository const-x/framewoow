<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<style type="text/css">
    #roleView .margin-bottom-10{margin-bottom:10px;}
	#roleView .margin-bottom-10 input{width:200px;}
	#roleView .tree-strong{font-weight:normal;display:inline-block;padding-right:20px;}
	#roleView .tree-m2-label{display:inline-block;width:105px;}
	#roleView .tree-span-label{width:121px;display:inline-block;}
	#roleView li ul{background:#fffff1;}
	
	#roleView .tree-file{margin-top: 4px;}
	#roleView .tree-title{line-height: 26px;height: 26px;}
	#roleView .tree-title input{vertical-align: middle;}
</style>
<div class="dialogContentpanel">
   <ul class="easyui-tree" id="roleView" style="padding:0 20px">
        <c:forEach var="m1" items="${module.children }" >
            <li><span><label class="tree-span-label">${m1.name }</label>
                  <c:forEach var="o1" items="${m1.operations }" >
                       <input type="checkbox" value="${m1.sn }:${o1.sn }" ${o1.hasPermission } disabled="disabled" ><strong class="tree-strong">${o1.name }</strong>
                  </c:forEach>
                  </span>
	              <ul>
	               	<c:forEach var="m2" items="${m1.children }" >
	               	   <li><span ><label class="tree-m2-label">${m2.name }</label>
	               	   <c:forEach var="o2" items="${m2.operations }" >
	                       <input type="checkbox" value="${m2.sn }:${o2.sn }" ${o2.hasPermission } disabled="disabled"><strong class="tree-strong">${o2.name }</strong>
	                   </c:forEach>
	                   </span></li>
	               	</c:forEach>
	              </ul>
            </li>
        </c:forEach>
    </ul>
</div>
<div class="dialogButtonpanel">		
	<div class="buttonPanel">
		<a href="javascript:void(0)" class='easyui-linkbutton' style="width:80px" onclick='closeCurDialog(event)'>关闭</a>
	</div>
</div>