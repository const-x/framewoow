<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

  <div class='dialogContentpanel'> 
		<div id="imgviews" style="MARGIN-RIGHT: auto; MARGIN-LEFT: auto;">
			${details}
		</div>
	</div>

  <div class="dialogButtonpanel">  
    <div class="buttonPanel">
      <p  style="float:right;text-decoration:none;">
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog()'>
	  </p>	
	</div>
  </div>
