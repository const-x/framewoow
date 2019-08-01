<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<form  data-options="novalidate:true" class="required-validate pageForm">
  <div class='dialogContentpanel'> 
    <h1>${not empty message ? message:'系统错误'}</h1>
	<a onclick="javascript:$('#errorMsgTable').show();">点击查看错误信息</a>
    <table id="errorMsgTable" style="display:none; border:solid #ebebeb 1px;" >
      <tr style="border:solid #ebebeb 1px;"><td><h2>exception:</h2></td><td width="100%" >${exception}</td></tr>
      <tr style="border:solid #ebebeb 1px;"><td><h2>cause:</h2></td><td width="100%" >${cause}</td></tr>
      <tr style="border:solid #ebebeb 1px;"><td><h2>stacktrace:</h2></td><td width="100%" >${details}</td></tr>
    </table>
  </div>	
  <div class="dialogButtonpanel buttonPanel"> 
      <p  style="float:right;text-decoration:none;">
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog()'>
	  </p>	
  </div>
	
	
</form>



