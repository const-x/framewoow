function addPlugins(){
	
	
	$("textarea").on('load',this,_loadxheditor);
	
	function _loadxheditor(a){
		alert("loaded");
	}
	
	//对ajax请求返回进行默认处理
	$.ajaxSetup({
		complete : function(XMLHttpRequest, textStatus) {
			var response = XMLHttpRequest.responseText;
			if(response && response.indexOf('{') == 0){
				var json = JSON.parse(response);
				_handResponse(json);
			}
		},
		error : function(XMLHttpRequest, textStatus){
			_handError(XMLHttpRequest);
		} 
	});
	
	//扩展easyui中tabs的部分方法，实现根据唯一标识id的进行相应操作
    var _methods = $.fn.tabs.methods;  
    var _getTab = _methods.getTab;//保存原方法 
    $.extend($.fn.tabs.methods, {  
        getTabById : function(jq, id) {//重写getTab方法，增加根据id获取tab
            if(!id) return null;  
            var tabs = jq.data('tabs').tabs;  
            for (var i = 0; i < tabs.length; i++) {  
                var tab = tabs[i];  
                if (tab.panel("options").id==id) {  
                    return tab;  
                }  
            }  
            return _getTab(jq, id);//如果根据id无法获取，则通过easyui默认的getTab进行获取  
        }, 
        existsById : function(jq, id) {//重写exists方法，增加id判断  
            return this.getTab(jq,id)!=null;//调用重写后的getTab方法  
        }
    }); 
    
    //打开对话框拦截
    $.extend($.fn.dialog.defaults, {onLoadError:function(XMLHttpRequest){
    	var code = XMLHttpRequest.status;
    	closeDialog($(this).context.id);
    	_handError(XMLHttpRequest);
    },
    onBeforeLoad:function(param){
    	var opt = $(this).dialog("options");
		if(opt.queryParams){
			param = $.extend(param,opt.queryParams);
    	}
        return true;   
	}
    }); 
    
    
    //datagrid
    $.extend($.fn.datagrid.defaults,{rownumbers:true,checkOnSelect:false,selectOnCheck:false,singleSelect:true,fitColumns:true,
    	pagination:true,pageList:[10,20,30,40,50],pageSize:10,
    	onLoadError:_handError,
    	onBeforeLoad:function(param){
    		//解决实体字段与数据库列名不一致引起的排序问题
        	var sort = param.sort;
        	if(sort){
        		var map =  $(this).datagrid("options").sortcolumns;
            	if(map[sort]){
            		param.sort = map[sort];
            	}
        	}
    		
    		var autoload = $(this).attr("autoload");
    		if(autoload && autoload == 'false'){
    			$(this).attr("autoload","true");
    			 return false;
    		}
    		var form = $(this).attr("refForm");
        	var condition = form2Json($('#'+form));
        	param = $.extend($(this).datagrid('options').queryParams,condition);
        	$(this).datagrid('options').queryParams=param; 
    	}
    });
    
    
    //suggest
    var _inputevents = $.fn.combobox.defaults.inputEvents;
    var _blur = _inputevents.blur;
    $.extend(_inputevents,{
    	//如果强制必需选择，且没有执行选择动作 则清除输入内容
    	blur:function(e){
    		var $this = $(e.data.target);
    		var opt = $this.combobox("options");
    		_blur(e);
    		if(opt.forceSelect && !opt.select){
    			var lookupGroup = $this.attr('lookupGroup');
    			var inputs = $this.parents("form:first").find('[lookupGroup="'+lookupGroup+'"]');
    			for (var int = 0; int < inputs.length; int++) {
    				var $input = $(inputs[int]);
    				$input.textbox("setValue", '');
    			}
    		}
    	}
    });
    $.extend($.fn.combobox.defaults,{inputEvents:_inputevents,panelHeight:'auto',onLoadError:_handError,
    	onBeforeLoad:function(param){
    		var opt = $(this).combobox("options");
    		if(opt.mode != 'remote'){
    			return false; 
    		}
    		if(param == null || param.q == null || param.q.replace(/ /g, '') == ''){
        		return false; 
        	}
    		if(opt.valueField){
    			opt.valueField = opt.textField;
    		}
        	var field = $(this).attr('queryField') || opt.textField;
            param[field]= param.q;  
            param['page']= 1;  
            param['rows']= 5; 
            return true;   
    	},
    	loadFilter:function(data){
    		var opt = $(this).combobox("options");
    		if(opt.mode != 'remote'){
    			return data; 
    		}
    		if(_handResponse(data)){
    			 if(data['rows']){
    				  return data['rows'];
    			  }
    		}
		    return data;
    	},onSelect:function(row){
    		var lookupGroup = $(this).attr('lookupGroup');
    		var callback = $(this).attr('callback');
    		var opt = $(this).combobox("options");
    		opt.select=true;
    		_bringback($(this),row,lookupGroup,callback);
    	},onChange:function(values,o) { 
    		var opt = $(this).combobox("options");
    		opt.select=false;
    	}

    });
    
    
    //tree
    $.extend($.fn.tree.defaults,{lines:true,onLoadError:_handError,
    	onBeforeLoad:function(param){
            return true;   
    	},
    	loadFilter:function(data){
    		if(_handResponse(data)){
    			 if(data['rows']){
    				 if(data['rows'] instanceof Array){
    					 return data['rows']; 
    				 }else{
    					 var a =[];
	       				  a.push( data['rows']);
	       				  return a; 
    				 }
    			  }
    		}
		  return data;
    	},onDblClick:function(node){
    		var tree = $(this).closest('.easyui-tree');
    		var id = tree.attr("refDataGrid");
    		var datagrid = $('#'+id);
        	datagrid.attr("parent",node.id);
        	var refform = datagrid.attr("refForm");
        	var input = $('#'+refform +' input[name="parent.id"]');
        	if(!input || input.length == 0){
        		input = $("<input type='hidden' value="+node.id+" name='parent.id' >");
        		$('#'+refform).append(input);
        	}else{
        		input.first().val(node.id);
        	}
        	datagrid.datagrid("load");
    	}
    });
    
    $.extend($.fn.panel.defaults, {onLoadError:_handError});
    
 
   $.extend($.fn.validatebox.defaults.rules, {
	   required : {
	        validator : function(value) {
	           if(!value){
	        	   return false;
	           }
	           if(value.length ==0){
	        	   return false;
	           }
	           if($.trim(value).length ==0){
	        	   return false;
	           }
	           if($.trim(value)=='-选择-'){
	        	   return false;
	           }
	           return true;
	        },
	        message : '必填字段！'
	    }
   });
   

}
	
    
	



// 1.modal
// 当modal属性值为true时，弹出对话框后，对话框的底层是不可以进行操作的。
// 2.title,width,height
// 分别表示对话框的标题，宽度，高度。
// 3.collapsible,minimizable,maximizable
// 默认情况下，对话框右上角的操作按钮只有关闭。通过这3个属性，可以为对话框添加：折叠、最小化、最大化按钮。
//4.resizable
//   该属性用于说明对话框的大小是否可以调节。
// 5.iconCls
//　　这个属性可以修改对话框左侧的图标和title一起说明该对话框的用途。
function openUrlInDialog(a){
	var $this = $(a);
	var target = 'dialog' + $this.attr('id');
	$("#" + target).dialog('destroy');
	$("#" + target ).remove();
	$("body").append("<div id='"+target+"' class='easyui-dialog'></div>");
	
	$("#" + target).dialog({
	    title: $this.attr("title") || $this.text(),
	    width: $this.attr("width")|| 800,
	    height: $this.attr("height")|| 600,
	    collapsible:$this.attr("collapsible")|| false,
	    minimizable:$this.attr("minimizable")|| false,
	    maximizable:$this.attr("maximizable")|| true,
	    resizable:$this.attr("resizable")|| true,
	    iconCls:$this.attr("iconCls")|| 'icon-edit',
	    modal:$this.attr("modal")|| true,
	    closed: false,
	    cache: false,
	    href:unescape($this.attr("aim")),
	    onLoad:$this.attr("onLoad")
	});
}

function openInDialog(url,target,title,width,height,iconCls,onClose,refForm){

	$("#" + target).dialog('destroy');
	$("#" + target ).remove();
	var param = {};
	if(refForm){
		param = form2Json($('#'+refForm));
	}
	$("body").append("<div id='"+target+"' class='easyui-dialog'></div>");
	$("#" + target).dialog({
	    title:title,
	    width: width,
	    height:height,
	    collapsible:false,
	    minimizable:false,
	    maximizable:true,
	    resizable:true,
	    iconCls:iconCls,
	    modal:true,
	    closed: false,
	    cache: false,
	    queryParams:param,
	    href:unescape(url)
	});
}
function openLoginDialog(title){
	var target = "loginDialog";
	$("#" + target).dialog('destroy');
	$("#" + target ).remove();
	
	$("body").append("<div id='"+target+"' class='easyui-dialog'></div>");
	
	$("#" + target).dialog({
	    title: title || "登陆",
	    width: 400,
	    height: 240,
	    modal:true,
	    closed: false,
	    cache: false,
	    href:unescape("./login/timeout")
	});
	return false;
}


function closeCurDialog(event){
	var evt = event || window.event;  
	var $this = $(evt.target);
	var dlg = $this.closest('.easyui-dialog');
	if(dlg){
		dlg.dialog('destroy');
		dlg.remove();
	}
}

function closeDialog(target){
	var $this = $("#" + target);
	var dlg = $this.closest('.easyui-dialog');
	if(dlg){
		dlg.dialog('destroy');
		dlg.remove();
	}
}

function submitQuery(form){
	try {
		var $this = $(form);
		var refDatagrid = $this.attr("refDataGrid");
		$('#'+refDatagrid).datagrid({ queryParams: form2Json($this)});
	}finally{
		return false;
	}
}

function validateCallback(form, callback, confirmMsg) {
	try {
		var $form = $(form);
		if (!$form.form('validate') ) {
			return false;
		}
		var _submitFn = function(e){
			if(e){
				$.ajax({
					type: $form.method || 'POST',
					url:$form.attr("action"),
					data:$form.serializeArray(),
					cache: false,
					success: function(data,XMLHttpRequest, textStatus){
						if(data && data.indexOf('{') == 0){
							var json = JSON.parse(data);
							if(json["statusCode"] == 200){
								var dlg = $form.parents(".easyui-dialog:first");
								if(dlg){
									dlg.dialog('destroy');
									dlg.remove();
								}
							}
						}
					    if (callback != undefined && callback != '') {
					    	eval(callback+'(json)');
					    }
					},
					error: function(XMLHttpRequest, textStatus){
						_handError(XMLHttpRequest);
					}
				});
			}
		};
		if (confirmMsg) {
			$.messager.confirm("提示", confirmMsg, _submitFn);
		} else {
			_submitFn(true);
		}
	}finally{
		return false;
	}
}


//tab
function addTab(id, title, url) {
	if ($('#tabs').tabs('exists', title)) {
		$('#tabs').tabs('select', title);
	} else {
		$('#tabs').tabs('add', {
			id : id,
			title : title,
			href : url,
			closable : true
		});
		$("#tabs").tabs({
			fit : true,
			// 为其附加鼠标右键事件
			onContextMenu : function(e, title, index) {
				e.preventDefault();
				var li = $(e.target).closest("li");
				if (li.hasClass("tabs-first")) {
					return;
				}
				var target = li.context.innerHTML;
				$('#mm').data("title", target);
				$('#mm').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		$('#tabs').tabs('select', title);
	}
}

function refreshTab(id){
    var target = $('#tabs').tabs('getTabById',id); 
    if(target){
    	var grid = target.find(".easyui-datagrid")[0];
    	if(grid){
    		$(grid).datagrid("load");
    	}
    	var tree = target.find(".easyui-tree")[0];
    	if(tree){
    		$(tree).tree("reload");
    	}
    }
}

function closeTab(id){
    var target = $('#tabs').tabs('getTabById',id); 
    if(target){
    	$('#tabs').tabs('close', target);
    }
}

function menuClick(id,currTitle){
	switch (id) {
		case "mm-tabclose": {
			$('#tabs').tabs('close', currTitle);
			break;
		};
		case "mm-tabcloseall": {
			var tablist = $('#tabs').tabs('tabs');
			for (var i = tablist.length - 1; i > 0; i--) {
				$('#tabs').tabs('close', i);
			}
			break;
		};
		case "mm-tabcloseother": {
			var tablist = $('#tabs').tabs('tabs');
			var tab = $('#tabs').tabs('getTab',currTitle);
			var index = $('#tabs').tabs('getTabIndex', tab);
			var num = tablist.length - index;
			for (var i = 0; i <= num; i++) {
				$('#tabs').tabs('close', index + 1);
			}
			num = index - 2;
			for (var i = 0; i <= num; i++) {
				$('#tabs').tabs('close', 1);
			}
			$('#tabs').tabs('select', currTitle);
			break;
		};
		case "mm-tabcloseright": {
			var tablist = $('#tabs').tabs('tabs');
			var tab = $('#tabs').tabs('getTab',currTitle);
			var index = $('#tabs').tabs('getTabIndex', tab);
			var num = tablist.length - index;
			for (var i = 0; i <= num; i++) {
				$('#tabs').tabs('close', index + 1);
			}
			break;
		};
		case "mm-tabcloseleft": {
			var tab = $('#tabs').tabs('getTab',currTitle);
			var index = $('#tabs').tabs('getTabIndex', tab);
			var num = index - 2;
			for (var i = 0; i <= num; i++) {
				$('#tabs').tabs('close', 1);
			}
			break;
		};
		case "mm-tabrefresh": {
			var tab = $('#tabs').tabs('getTab',currTitle);
			var id = tab.panel("options").id;
			refreshTab(id);
			break;
		};
		
	}
}

//toolbar
function toolbarButtonClick(a){
	var $this = $(a);
	var datagrid  = $this.attr('refDataGrid');
	var url  = $this.attr('url');
	var id  = $this.attr('id');
	var idField  = $this.attr('idField')||'id';
	var target  = $this.attr('type')||'dialog' ;
	var refForm  = $this.attr('refForm');
	var name = $this.html();
	var width  = _getIntAttr($this,'width',800);
	var height  = _getIntAttr($this,'height',600);
	
	var model  = $this.attr('model');
	var data = null;
	if(model){
		data = _doGetTargetRows(model,datagrid);
		if(data == -1){
			return false;
		}
	}
	var parent  = $('#'+datagrid).attr('parent');
	url = _convertUrl(url,data,idField,parent);
	if(!url){
		return false;
	}
	
    var _invoke = function(e){
    	if(e){
    		switch(target){
	            case 'dialog':{
	         	   openInDialog(url,id+'_Dialog',name,width,height,'','',refForm);
	         	   break;
	            }
	            case 'ajaxTodo':{
	         		$.ajax({
	         			type: $this.attr('method') || 'GET',
	         			url:url,
	         			cache: false
	         		});
	         	   break;
	            }
	            default:console.log('不支持的target：' + target);
	         }
    	}
    	return false;
    };
    
    var confirm  = $this.attr('confirm');
    if(confirm){
    	$.messager.confirm("提示", confirm, _invoke);
    }else{
    	_invoke(true);
    }
	return false;
}

function _getIntAttr(target,attr,defaultVal){
	if(target.attr(attr)){
		return parseInt(target.attr(attr));
	}
	return defaultVal;
}

function _doGetTargetRows(model,datagrid) {
	switch (model) {
		case 'selected': {
			var row = $('#' + datagrid).datagrid('getSelected');
			if (row == null) {
				$.messager.alert("错误", "请选择要操作的数据行！", 'error');
				return -1;
			}
			return row;
		}
		case 'checked': {
			var rows = $('#' + datagrid).datagrid('getChecked'); ;
			if (rows == null || rows.length ==0) {
				$.messager.alert("错误", "请勾选要操作的数据！", 'error');
				return -1;
			}
			return rows; 
		}
		case 'all': {
			var rows = $('#' + datagrid).datagrid('getChecked'); ;
			if (rows == null ) {
				rows = [];
			}
			var row = $('#' + datagrid).datagrid('getSelected');
			if(row && $.inArray(row, rows) == -1){
				rows.push(row);
			}
			if (rows.length == 0) {
				$.messager.alert("错误", "请选择或勾选要操作的数据！", 'error');
				return -1;
			}
			return rows; 
		}
		case 'none': {
			return null; 
		}
		default:console.log('不支持的取数方式：' + model);
		return -1;
	}
}

function _convertUrl(url, data, idField,parent) {
	if (data) {
		if (Array.isArray(data)) {
			var ids = [];
			for (var i = 0; i < data.length; i++) {
				ids.push(data[i][idField]);
			}
			url = url.replace("{" + idField + "}", ids.join(','));
		} else {
			for ( var key in data) {
				url = url.replace("{" + key + "}", data[key]);
			}
		}
	}
	if(url.indexOf("{parent}") > 0){
		if(parent){
			url = url.replace("{parent}", parent);
		}else{
			$.messager.alert("错误", "请先选择父节点", 'error');
			return null;
		}
	}
	var fr = url.indexOf("{");
	if( fr > 0 ){
		var end = url.indexOf("}");
		$.messager.alert("错误", "未获取到参数值：" + url.substring(fr, end+1), 'error');
		return null;
	}
	return url;
}

//bringback
var _lookups =[];

function lookup(a){
	var $this = $(a);
	var url  = $this.attr('url');
	var name = $this.html();
	var width  = _getIntAttr($this,'width',800);
	var height  = _getIntAttr($this,'height',600);
	var lookupGroup = $this.attr('lookupGroup');
	var dialog='lookup'+lookupGroup+'_Dialog'+_lookups.length;
	var lookup = {'lookupGroup':lookupGroup,'dialog':dialog,'target':$this};
	_lookups.push(lookup);
	openInDialog(url,dialog,name,width,height,'',function(){
		 $.each(_lookups,function(index,lookup){
			if(lookup['dialog'] == $(this).id){
				_lookups.remove(lookup);
			}
		});
	});
	return false;
}

function bringback(index, row){
	var lookup = _lookups.pop();
	var lookupGroup = lookup['lookupGroup'];
	var callback = lookup['target'].attr('callback');
	_bringback(lookup['target'],row,lookupGroup,callback);
	closeDialog(lookup['dialog']);
}

function mutibringback(rows,names){
	var lookup = _lookups.pop();
	var lookupGroup = lookup['lookupGroup'];
	var target = lookup['target'];
	var div = target.parents("form:first").find('div[lookupGroup="'+lookupGroup+'"]');
	for (var int = 0; int < rows.length; int++) {
		var row=rows[int];
		for (var key in row) {
			var input = $("<input type='hidden' name='"+lookupGroup+"["+int+"]."+key+"' value='"+row[key]+"' />");
			$(div).append(input);
		}
	}
	var input = target.parents("form:first").find('input[lookupGroup="'+lookupGroup+'"]');
	var $input = $(input);
	$input.textbox("setValue", names);
	closeDialog(lookup['dialog']);
}


function _bringback(target,row,lookupGroup,callback){
	var inputs = target.parents("form:first").find('[lookupGroup="'+lookupGroup+'"]');
	for (var int = 0; int < inputs.length; int++) {
		var $input = $(inputs[int]);
		var lookupfield = $input.attr('lookupfield');
		if(lookupfield){
			if(row){
				$input.textbox("setValue", row[lookupfield]);
			}else{
				$input.textbox("setValue", '');
			}
		}
		if (callback != undefined && callback != '') {
	    	eval(callback+'(row)');
	    }
	}
}

//将表单数据转为json
function form2Json(form) {
    var arr = form.serializeArray();
    var jsonStr = "";
    jsonStr += '{';
    for (var i = 0; i < arr.length; i++) {
        jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
    }
    if(jsonStr.length > 1){
    	jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
    }
    jsonStr += '}';

    var json = JSON.parse(jsonStr);
    return json;
}

function _handResponse(data){
	switch(data["statusCode"]){
		case 300:{var message = data["message"]||"数据获取失败";$.messager.alert("错误", message, 'error');  return false;}
		case 301:{var message = data["message"]||"会话超时，请重新登陆";
		         openLoginDialog(message);
		         return false;}
		case 302:{var message = data["message"];$.messager.alert("警告", message, 'warn');  return false;}
		case 403:{var message = data["message"]||"请求非法";$.messager.alert("错误", message, 'error');  return false;}
		case 200:{
			var message = data["message"];
			if(message){
				$.messager.alert("信息", message, 'info');
			}
			switch(data["callbackType"]){
			   case "refreshCurrent":{if(data["navTabId"]){refreshTab(data["navTabId"])};return false;}
			   case "closeCurrent":{if(data["navTabId"]){closeTab(data["navTabId"])};return false;}
			   case "forward":{return false;}
			   case "forwardConfirm":{return false;}
			   case "warn":{$.messager.alert("警告", data["message"], 'warn');return false;}
			   case "error":{$.messager.alert("错误", data["message"], 'error');return false;}
			}
		}
		default:return true;
	}
}

function _handError(XMLHttpRequest) { 
	var err = XMLHttpRequest.responseText;
	if(err){
		if(err.indexOf('{') == 0){
			_handResponse(JSON.parse(err));
		}
	}else{
		err =  XMLHttpRequest.statusText +":"+ XMLHttpRequest.status;
	}
    $.messager.alert("错误", err, 'error');  
    return false;
}  


function data2StringFomatter(value,row,index){
	if(row){
		var key = this.field + "Str";
		return row[key];
 	}
	return "";
}

function imageFomatter(value,row,index){
	if(value){
		return "<img style='width:100%' src='"+value+"'  onclick='ImgShow()'/>";
 	}
	return "";
}



function operations(compoment,id,editPermission){
	var actions = '';
	if(editPermission){
		var edit =  "<a id='memberEdit' class='easyui-linkbutton'  onClick='toolbarButtonClick(this)'  href='javascript:void(0)'"
	      + " url='"+basePath+"/"+compoment+"/update/"+id+"'  type='dialog' model='none' plain='true' >编辑</a>";
		actions += edit;
	}
	return actions;
}

/** 0:收回（动作） 、 待提交（状态）*/
/** 10:提交（动作） 、待审批（状态）*/
/** 20:审批（动作） 、已通过（状态） */
/** 30:驳回（动作） 、已驳回（状态）*/
function operationsWithAudit(compoment,id,auditStatus,editPermission,submitPermission,auditPermission){
	var actions = '';
	if(auditStatus == 0 || auditStatus == 30){
		if(editPermission){
			var edit =  "<a id='memberEdit' class='easyui-linkbutton datagridoperation'  onClick='toolbarButtonClick(this)'  href='javascript:void(0)'"
			      + " url='"+basePath+"/"+compoment+"/update/"+id+"'  type='dialog' model='none' plain='true' >编辑</a>";
			actions += edit;
		}
		if(submitPermission){
			var edit =  "<a id='memberSubmit' class='easyui-linkbutton datagridoperation' onClick='toolbarButtonClick(this)' width='420' height='280' href='javascript:void(0)'"
			      + " url='"+basePath+"/"+compoment+"/submit/10/"+id+"'  type='dialog' model='none' plain='true' >提交</a>";
			actions += edit;
		}
	}else if(auditStatus == 10){
		if(submitPermission){
			var edit =  "<a id='memberSubmit' class='easyui-linkbutton datagridoperation' onClick='toolbarButtonClick(this)' width='420' height='280' href='javascript:void(0)'"
			      + " url='"+basePath+"/"+compoment+"/submit/0/"+id+"'  type='dialog' model='none' plain='true' >收回</a>";
			actions += edit;
		}
		if(auditPermission){
			var edit =  "<a id='memberSubmit' class='easyui-linkbutton datagridoperation' onClick='toolbarButtonClick(this)' width='420' height='280' href='javascript:void(0)'"
			      + " url='"+basePath+"/"+compoment+"/audit/20/"+id+"'  type='dialog' model='none' plain='true' >审批</a>";
			actions += edit;
		}
		if(auditPermission){
			var edit =  "<a id='memberSubmit' class='easyui-linkbutton datagridoperation' onClick='toolbarButtonClick(this)' width='420' height='280' href='javascript:void(0)'"
			      + " url='"+basePath+"/"+compoment+"/audit/30/"+id+"'  type='dialog' model='none' plain='true' >驳回</a>";
			actions += edit;
		}
	}
	return actions;
}


function removeUploaded(a){
	var $this = $(a);
	$this.attr('src','');
}

function removeMutiUploaded(a){
	var $this = $(a);
	$this.remove();
}





