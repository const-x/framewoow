<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<style type="text/css">
	#roleCreateFm .margin-bottom-10{margin-bottom:10px;}
	#roleCreateFm .margin-bottom-10 input{width:200px;}
	#roleCreateFm .tree-strong{font-weight:normal;display:inline-block;padding-right:20px;}
	#roleCreateFm .tree-m2-label{display:inline-block;width:105px;}
	#roleCreateFm .tree-span-label{width:121px;display:inline-block;}
	#roleCreateFm .easyui-tree ul{background:#fffff1;}
	
	#roleCreateFm .tree-file{margin-top: 4px;}
	#roleCreateFm .tree-title{line-height: 26px;height: 26px;}
	#roleCreateFm .tree-title input{vertical-align: middle;}
	
</style>
<form method="post" id="roleCreateFm" action="<%=basePath %>/security/role/create" class="required-validate pageForm" onsubmit="return validateCallback(this);" style="padding:20px;">
<div class="dialogContentpanel">
	<div class="margin-bottom-10" style="padding:0 20px;">
		<input class="easyui-textbox" name="name" placeholder="请输入角色名称" data-options="label:'角色名称:',required:true"></input><span class='starL' style="background-position:center bottom;"></span>
	</div>
  <ul class="easyui-tree" id ="all_easyui-tree" style="padding:0 20px;">
       <c:set var="ind" value="${0}"/>
       <c:forEach var="m1" items="${module.children }" >
            <li>
            	<span>
            		<label class="tree-span-label">${m1.name }</label>
	                 <c:forEach var="o1" items="${m1.operations }" >
	                      <input type="checkbox" name="permissionList[${ind}]" value="${m1.sn }:${o1.sn }" ><strong class="tree-strong">${o1.name }</strong>
	                      <c:set var="ind" value="${ind + 1 }"/>
	                 </c:forEach>
                 </span>
              <ul>
               	<c:forEach var="m2" items="${m1.children }" >
               	   <li><span ><label class="tree-m2-label">${m2.name }</label>
               	   <c:forEach var="o2" items="${m2.operations }" >
                       <input type="checkbox" name="permissionList[${ind}]" value="${m2.sn }:${o2.sn }"  name="${m2.sn }" group="${m2.sn }" onclick="thisclick(this)"><strong class="tree-strong">${o2.name }</strong>
                       <c:set var="ind" value="${ind + 1 }"/>
                   </c:forEach>
                   <input type="checkbox" group="${m2.sn }" class="allcheck" onclick="allclick(this)">所有</input>
                   </span></li>
               	</c:forEach>
              </ul>
           </li>
       </c:forEach>
    </ul>
<script type="text/javascript">


 function allclick(thisObj){
	if ($(thisObj).is(":checked")) {
        $(thisObj).siblings().prop("checked",true);
    //                         $(this).parent("span").children("input[type='checkbox']").prop("checked", true);
    } else {
        $(thisObj).siblings().prop("checked",false);
    //                         $(this).parent("span").children("input[type='checkbox']").prop("checked",false);
    }
}



 function thisclick(obj){
	var tempValue = $(obj).val();
	tempValue = tempValue.split(":")[0];
	var objs=$(":checkbox[group=" + tempValue + "]");
	var len = objs.length-1;
	if($(obj).is(":checked")){
		for(var i = 0; i < objs.length; i++){
			tObj = objs[i];
			if(tObj.checked && i != (objs.length - 1)){
				len--;
			}
		}
		if(len < 1){
			objs[objs.length-1].checked = true; 
		}
	}else{
		objs[objs.length-1].checked = false; 
	}
}

</script>
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