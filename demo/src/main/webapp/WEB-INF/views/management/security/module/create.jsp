<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<h2 class="contentTitle">添加模块</h2>
<form method="post" action="<%=basePath %>/security/module/create" class="required-validate pageForm" onsubmit="return validateCallback(this);">
	<input type="hidden" name="parent.id" value="${parentModule.id }"/>
	<div class="pageFormContent" layoutH="97">
		<dl>
			<dt>名称：</dt>
			<dd>
				<input type="text" name="name" class="required" size="32" maxlength="32" placeholder="请输入模块名称"/>
			</dd>
		</dl>
		<dl>
			<dt>优先级：</dt>
			<dd>
				<input type="text" name="priority" class="required digits" size="2" value="99" maxlength="2"/>
				<span class="info">&nbsp;&nbsp;默认:99</span>
			</dd>
		</dl>		
		<dl>
			<dt>URL：</dt>
			<dd>
				<input type="text" name="url" class="required" size="32" maxlength="255" placeholder="请输入访问地址"/>
			</dd>
		</dl>
		<dl>
			<dt>授权名称：</dt>
			<dd>
				<input type="text" name="sn" class="required" size="32" maxlength="32" placeholder="授权名称"/>
			</dd>
		</dl>	
		<dl>
			<dt>是否启用：</dt>
			<dd>
			<select name="enable">
				<option value="1">启用</option>
				<option value="0">停用</option>
			</select>
			</dd>
		</dl>		
		<dl>
			<dt>描述：</dt>
			<dd>
				<textarea name="description" cols="32" rows="3" maxlength="255"></textarea>
			</dd>
		</dl>	
	</div>
			
	<div class="buttonPanel">
		<input type='submit' value='确定' class='easyui-linkbutton'  >
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog(event)'>
	</div>
</form>