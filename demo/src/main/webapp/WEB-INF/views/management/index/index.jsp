<%@ page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 使IE5,IE6,IE7,IE8兼容到IE9模式 -->
<!-[if lt IE]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
<![endif]–>


<script type="text/javascript">
     var basePath = '<%=basePath%>';
</script>
<title>安全管理平台</title>
<link href="<%=basePath%>/css/index.css" rel="stylesheet" type="text/css" media="screen" />
<link href="<%=basePath%>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/icon_add.css" rel="stylesheet" type="text/css" />
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="<%=basePath%>/css/easyui/themes/material/easyui.css" data-filtered="filtered" />
<link href="<%=basePath%>/css/easyui/themes/icon.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script src="<%=basePath%>/js/easyui/easyloader.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugin.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/easyui/validateplugin.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/index.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<!--富文本编辑框 -->
<script src="<%=basePath%>/js/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>

<!--文件上传-->
<script src="<%=basePath%>/js/fileupload/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/fileupload/jquery.fileupload.js" type="text/javascript"></script>

<!-- 图片控制 -->
<script src="<%=basePath%>/js/image/zoomingimage.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>/js/image/viewimage.js" type="text/javascript" charset="utf-8"></script>

<!-- 高德地图
<script language="javascript" src="http://webapi.amap.com/maps?v=1.3&amp;key=54c9d7035bdde160f04a9cee4101edbf" type="text/javascript"></script>
<script language="javascript" src="http://api.map.baidu.com/api?v=2.0&ak=r4zTEDmEW3WOnDcVL4PVLxYL" type="text/javascript"></script>
-->

<!-- 二维码生成
<script src="<%=basePath%>/js/qrcode/jquery.qrcode.min.js" type="text/javascript" charset="utf-8"></script>
-->

</head>
<body class="easyui-layout" id="easyui-layout-so" style="overflow-y: hidden;background:#1c2029;position:relative;" scroll="no">
	
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<p>抱歉，请开启脚本支持！</p>
		</div>
	</noscript>
	
	
	<div data-options="region:'north',border:false,collapsible:false,collapsedSize:0" id="header" class="headerNavCss" style="background:#1c2029;">
		<ul class="navCss">
			<li><a class="color1" href='javascript:void(0)' onclick="{$('#tabs').tabs('select', 0);}">主页</a></li>
			<li><a id="updateBaseDialog" aim='<%=basePath%>/index/updateBase' href="javascript:void(0)" modal="true" title='修改用户信息'
			  width='400' height='220' onclick='openUrlInDialog(this)'>${user.realname }</a></li>
			<li><a id="updatePwdDialog" aim='<%=basePath%>/index/updatePwd' href="javascript:void(0)" modal="true" title='修改密码' 
			  width='400' height='220' onclick='openUrlInDialog(this)'>修改密码</a></li>
			<li><a href="<%=basePath%>/logout">退出系统</a></li>
			<!--  
			<li>
			  <select class="easyui-combobox" panelHeight="auto" data-options="onSelect:changeTheme" >
   					<option value="default">default</option>
   					<option value="black">black</option>
   					<option value="bootstrap">bootstrap</option>
   					<option value="material" selected >material</option>
   					<option value="metro">metro</option>
   				</select>
   			</li>
   			-->	
		</ul>
	</div>
	<!--left区域导航菜单定位start-->
	<div id="blo" style="position:absolute;z-index:999;top:83px;">
		<a class="layout_logo" href="javascript:void(0)"></a>
		<ul class="sidebar_ul" fillSpace="sideBar">
			<c:forEach var="level1" items="${menuModule.children}">
				<li class="sidebar_ul_li"><span><img
						class="sidebar_img"
						hoverImg="<%=basePath%>/images/index/layout_indent_h.png"
						src="<%=basePath%>/images/index/layout_indent.png"
						comImg="<%=basePath%>/images/index/layout_indent.png" /></span> <a
					href="#">${level1.name }</a> <i></i>
					<ul class="sidebar_li_ul">
						<c:forEach var="level2" items="${level1.children }">
							<li style="display: flex;display:-webkit-flex;display:-moz-flex;"><a href="javascript:void(0)"  style="padding-left:30px;flex: 1;"
								onclick="addTab('info_${level2.sn}','${level2.name }','<%=basePath %>${level2.url}')">${level2.name }<strong style="width:8px;"></strong></a></li>
						</c:forEach>
					</ul>
					<div class="sidebar_li_div"></div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<!--left区域导航菜单定位end-->
	
	<div id="fold_left" class="fold_left"></div>
	
	<!--left区域导航菜单占位end-->
	<div id="left" data-options="region:'west',split:false,border:false,singleSelect:true" title="" style="width:110px;background:#1c2029;">
		<div id="sidebar_oy">

		</div>
	</div>
	<!--left区域导航菜单占位end-->
	
	
	<!--center区域start-->
	<div data-options="region:'center', split:false,border:true,border:false,collapsible:false,collapsedSize:0" id="center">
		<div id="tabs" class="easyui-tabs" fit="true">
			 <div title="主页" id='main'> 
			 <p class="padding14"><span>欢迎, ${user.realname } 。</span><span class="float-right"><fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd EEEE"/></span></p>
			 <fieldset style="border:0;">
			 	<legend>基本信息</legend>
			 	<table class="fieldsPanel fieldsPanel-textbox">
			 		<tr><td><input class="easyui-textbox" name="" readonly="readonly" style="width:100%;" data-options="label:'登录名称:'" value="${user.username}" /></td></tr>
			 		<tr><td><input class="easyui-textbox" name="" readonly="readonly" style="width:100%" data-options="label:'真实名字：'" value="${user.realname}" /></td></tr>
			 		<tr><td><input class="easyui-textbox" name="" readonly="readonly" style="width:100%" data-options="label:'电话：'" value="${user.phone}" /></td></tr>
			 		<tr><td><input class="easyui-textbox" name="" readonly="readonly" style="width:100%" data-options="label:'E-Mail：'" value="${user.email}" /></td></tr>
			 		<tr><td><input class="easyui-textbox" name="" readonly="readonly" data-options="label:'创建时间：'"/><span><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td></tr>
			 		<tr><td><input class="easyui-textbox" name="" readonly="readonly" style="width:100%" data-options="label:'所属组织：'" value="${user.organization.name}" /></td></tr>
			 	</table>
			</fieldset>
			</div>
		</div>
	</div>
	<!--center区域end-->
	

	<div id="mm" class="easyui-menu" style="width: 120px;">
	    <div id="mm-tabrefresh" >刷新当前页签</div>
	    <div class="menu-sep"></div>
		<div id="mm-tabclose" >关闭当前页签</div>
		<div id="mm-tabcloseall" >关闭所有页签</div>
		<div id="mm-tabcloseother" >关闭其他页签</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright" >关闭右侧页签</div>
		<div id="mm-tabcloseleft" >关闭左侧页签</div>
	</div>
</body>
</html>
