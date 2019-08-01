<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">


 function refreshCaptcha(e){
	 var $img = $(e);
	 $img.attr("src", "<%=basePath %>/Captcha.jpg");
	 return false;
 }

 function submitLogin(form){
	 var $form = $(form);
		if (!$form.form('validate') ) {
			return false;
		}
		$.ajax({
			type: $form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			complete :function(XMLHttpRequest, textStatus){
				closeDialog('loginDialog');
			},
			error: function(XMLHttpRequest, textStatus){
				_handError(XMLHttpRequest);
			}
		});
		return false;
 }
</script>
	<form method="post" action="<%=basePath %>/login" class="pageForm" onsubmit="return submitLogin(this)">
	  <div class='dialogContentpanel'> 
		<table style="margin-top:20px;">
			<tr>
				<td><input class="easyui-textbox" name="username" style="width:100%" data-options="label:'用户名:',required:true" validType='username'  value="${user.username }"></td>
			</tr>
			<tr>
				<td><input class="easyui-textbox" name="password" style="width:100%" type="password"   data-options="label:'密&nbsp;&nbsp;码:',required:true" validType='password'></td>
			</tr>
		</table>
	
			<%--
			<p>
				<label>验证码:</label>
				<input type="text" id="captcha_key" name="captcha_key" class="code validate[required,maxSize[6]]" size="6" />&nbsp;&nbsp;
				<span><img src="<%=basePath %>/Captcha.jpg" placeholder="点击刷新验证码" onclick="refreshCaptcha(this)" width="75" height="24" id="captcha"/></span>
			</p>
			 --%>	
   </div>			 		
  <div class="dialogButtonpanel">  
    <div class="buttonPanel">
		<input type='submit' value='确定' class='easyui-linkbutton'  >
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>
	</div>
  </div>
	</form>
	
