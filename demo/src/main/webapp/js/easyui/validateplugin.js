/**
 * 包含easyui的扩展和常用的方法
 *
 * @author
 *
 * @version 20120806
 */
 
var wjc = $.extend({}, wjc);/* 定义全局对象，类似于命名空间或包的作用 */
 
/**
 *
 * @requires jQuery,EasyUI
 *
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            for ( var i = 0; i < frame.length; i++) {
                frame[i].contentWindow.document.write('');
                frame[i].contentWindow.close();
            }
            frame.remove();
            if ($.browser.msie) {
                CollectGarbage();
            }
        }
    } catch (e) {
    }
};
 
/**
 * 使panel和datagrid在加载时提示
 *
 *
 * @requires jQuery,EasyUI
 *
 */

 
/**
 * @author wfire
 *
 * @requires jQuery,EasyUI
 *
 * 通用错误提示
 *
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
    $.messager.progress('close');
//  $.messager.alert('错误', XMLHttpRequest.responseText);
//  $.messager.confirm('错  误',XMLHttpRequest.responseText,function(r){  
//      if (r){
//          parent.location.replace('login.jsp');
//      }  
//  });
};

//datebox组件 添加清空按钮 并且默认设置editable=false
var buttons = $.extend([], $.fn.datebox.defaults.buttons);  
buttons.splice(1, 0, {  
text: '清空',  
handler : function(target) {  
    $(target).combo("setValue", "").combo("setText", ""); // 设置空值  
    $(target).combo("hidePanel"); // 点击清空按钮之后关闭日期选择面板  
}  
});  
$.fn.datebox.defaults.buttons = buttons;
$.fn.datebox.defaults.editable = false

var buttons = $.extend([], $.fn.datetimebox.defaults.buttons);  
buttons.splice(1, 0, {  
text: '清空',  
handler : function(target) {  
    $(target).combo("setValue", "").combo("setText", ""); // 设置空值  
    $(target).combo("hidePanel"); // 点击清空按钮之后关闭日期选择面板  
}  
});  
$.fn.datetimebox.defaults.buttons = buttons;
$.fn.datetimebox.defaults.editable = false;
 
/**
 *
 * @requires jQuery,EasyUI
 *
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
 */
var createGridHeaderContextMenu = function(e, field) {
    e.preventDefault();
    var grid = $(this);/* grid本身 */
    var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
    if (!headerContextMenu) {
        var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
        var fields = grid.datagrid('getColumnFields');
        for ( var i = 0; i < fields.length; i++) {
            var fildOption = grid.datagrid('getColumnOption', fields[i]);
            if (!fildOption.hidden) {
                $('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            } else {
                $('<div iconCls="icon-empty" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            }
        }
        headerContextMenu = this.headerContextMenu = tmenu.menu({
            onClick : function(item) {
                var field = $(item.target).attr('field');
                if (item.iconCls == 'icon-ok') {
                    grid.datagrid('hideColumn', field);
                    $(this).menu('setIcon', {
                        target : item.target,
                        iconCls : 'icon-empty'
                    });
                } else {
                    grid.datagrid('showColumn', field);
                    $(this).menu('setIcon', {
                        target : item.target,
                        iconCls : 'icon-ok'
                    });
                }
            }
        });
    }
    headerContextMenu.menu('show', {
        left : e.pageX,
        top : e.pageY
    });
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;


$.extend($.fn.panel.defaults, {
	onBeforeDestroy:function() {
		   //panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
	       var frame = $('iframe', this);
	       try {
	           if (frame.length > 0) {
	               for ( var i = 0; i < frame.length; i++) {
	                   frame[i].contentWindow.document.write('');
	                   frame[i].contentWindow.close();
	               }
	               frame.remove();
	               if ($.browser.msie) {
	                   CollectGarbage();
	               }
	           }
	       } catch (e) {
	       }
	   }
});

$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

$.fn.layout.paneldefaults = $.extend( $.fn.panel.defaults, {
	collapsedSize:0,
	collapsible:false
});
 
/**
 *
 * @requires jQuery,EasyUI
 *
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
        validator : function(value, param) {
            return value == $(param[0]).val();
        },
        message : '密码不一致！'
    },
    idcard : {// 验证身份证
        validator : function(value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message : '身份证号码格式不正确'
    },
    minLength: {
        validator: function(value, param){
            return value.length >= param[0];
        },
        message: '请输入至少（2）个字符.'
    },
     maxlength: {
        validator: function(value, param){
            return value.length <= param[0];
        },
        message: '最多可输入{0}个字符.'
    },
    length:{validator:function(value,param){
        var len=$.trim(value).length;
            return len>=param[0]&&len<=param[1];
        },
        message:"输入内容长度必须介于{0}和{1}之间."
    },
    phone : {// 验证电话号码
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message : '格式不正确,请使用下面格式:010-88888888'
    },
    mobile : {// 验证手机号码
        validator : function(value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message : '手机号码格式不正确'
    },
    intOrFloat : {// 验证整数或小数
        validator : function(value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message : '请输入数字，并确保格式正确'
    },
    currency : {// 验证货币
        validator : function(value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message : '货币格式不正确'
    },
    qq : {// 验证QQ,从10000开始
        validator : function(value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message : 'QQ号码格式不正确'
    },
    integer : {// 验证整数
        validator : function(value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message : '请输入整数'
    },
    age : {// 验证年龄
        validator : function(value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message : '年龄必须是0到120之间的整数'
    },
    chinese : {// 验证中文
        validator : function(value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message : '请输入中文'
    },
    english : {// 验证英语
        validator : function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message : '请输入英文'
    },
    unnormal : {// 验证是否包含空格和非法字符
        validator : function(value) {
            return /.+/i.test(value);
        },
        message : '输入值不能为空和包含其他非法字符'
    },
    username : {// 验证用户名
        validator : function(value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{4,15}$/i.test(value);
        },
        message : '用户名不合法（字母开头，允许5-16字节，允许字母数字下划线）'
    },
    faxno : {// 验证传真
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message : '传真号码不正确'
    },
    zip : {// 验证邮政编码
        validator : function(value) {
            return /^[0-9]\d{5}$/i.test(value);
        },
        message : '邮政编码格式不正确'
    },
    ip : {// 验证IP地址
        validator : function(value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message : 'IP地址格式不正确'
    },
    name : {// 验证姓名，可以是中文或英文
            validator : function(value) {
                return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
            },
            message : '请输入姓名'
    },
    msn:{
            validator : function(value){
                return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
            },
            message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    digits : {// 验证整数
        validator : function(value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message : '请输入整数'
    },
    maxlength: {
        validator: function(value, param){
            return value.length <= param[0];
        },
        message: '最多可输入{0}个字符.'
    },
    accept: {
        validator: function(value, param){
        	var d=value.length-param[0].length;
            return (d>=0&&value.lastIndexOf(param[0])==d);
        },
        message:  "请输入拥有合法后缀名的字符串"
    },
    alphanumeric: {
    	 validator : function(value) {
             return /.+/i.test(value);
         },
         message : '输入值不能为空和包含其他非法字符'
    },
    equalTo : {
        validator : function(value, param) {
            return value == $(param[0]).val();
        },
        message : '两次输入的内容不一致！'
    },
    lettersonly : {
    	validator : function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message : '输入必须是字母！'
    },
    max: {
        validator: function(value, param){
            return value <= param[0];
        },
        message: '输入值不能大于{0}.'
    },
    min: {
        validator: function(value, param){
            return value >= param[0];
        },
        message: '输入值不能小于{0}.'
    },
    number: {
        validator: function(value, param){
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '输入必须是数字！'
    },
    range: {
        validator: function(value, param){
            return value >= param[0] && value <= param[1] ;
        },
        message: '请输入{0}到{1}之间的数值.'
    },
    date: {
        validator: function(value, param){
        	 return /^(\d{4})-(\d{2})-(\d{2})$/.test(value);
        },
        message: '请输入正确的日期.'
    },
    datetime: {
        validator: function(value, param){
        	return /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/.test(value);
        },
        message: '请输入正确的日期和时间.'
    },
    time: {
        validator: function(value, param){
        	return /^(\d{1,2}):(\d{1,2}):(\d{1,2})$/.test(value);
        },
        message: '请输入正确的时间.'
    }
});

/**
*
* @requires jQuery,EasyUI
*
* 防止panel/window/dialog组件超出浏览器边界
* @param left
* @param top
*/
var easyuiPanelOnMove = function(left, top) {
   var l = left;
   var t = top;
   if (l < 1) {
       l = 1;
   }
   if (t < 1) {
       t = 1;
   }
   var width = parseInt($(this).parent().css('width')) + 14;
   var height = parseInt($(this).parent().css('height')) + 14;
   var right = l + width;
   var buttom = t + height;
   var browserWidth = $(window).width();
   var browserHeight = $(window).height();
   if (right > browserWidth) {
       l = browserWidth - width;
   }
   if (buttom > browserHeight) {
       t = browserHeight - height;
   }
   $(this).parent().css({/* 修正面板位置 */
       left : l,
       top : t
   });
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;



/**
 *
 * @requires jQuery,EasyUI
 *
 * 扩展tree，使其支持平滑数据格式
 */
$.fn.tree.defaults.loadFilter = function(data, parent) {
    var opt = $(this).data().tree.options;
    var idFiled, textFiled, parentField;
    if (opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};
 
/**
 *
 * @requires jQuery,EasyUI
 *
 * 扩展treegrid，使其支持平滑数据格式
 */
$.fn.treegrid.defaults.loadFilter = function(data, parentId) {
    var opt = $(this).data().treegrid.options;
    var idFiled, textFiled, parentField;
    if (opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};
 
/**
 * @author wfire
 *
 * @requires jQuery,EasyUI
 *
 * 扩展combotree，使其支持平滑数据格式
 */
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;
 
/**
 *
 * @requires jQuery,EasyUI
 *
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
    var l = left;
    var t = top;
    if (l < 1) {
        l = 1;
    }
    if (t < 1) {
        t = 1;
    }
    var width = parseInt($(this).parent().css('width')) + 14;
    var height = parseInt($(this).parent().css('height')) + 14;
    var right = l + width;
    var buttom = t + height;
    var browserWidth = $(window).width();
    var browserHeight = $(window).height();
    if (right > browserWidth) {
        l = browserWidth - width;
    }
    if (buttom > browserHeight) {
        t = browserHeight - height;
    }
    $(this).parent().css({/* 修正面板位置 */
        left : l,
        top : t
    });
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
 
/**
 *
 * @requires jQuery,EasyUI,jQuery cookie plugin
 *
 * 更换EasyUI主题的方法
 *
 * @param themeName
 *            主题名称
 */
changeTheme = function(themeName) {
    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
    $easyuiTheme.attr('href', href);
 
    var $iframe = $('iframe');
    if ($iframe.length > 0) {
        for ( var i = 0; i < $iframe.length; i++) {
            var ifr = $iframe[i];
            $(ifr).contents().find('#easyuiTheme').attr('href', href);
        }
    }
 
    $.cookie('easyuiThemeName', themeName, {
        expires : 7
    });
};
 
 
serializeObject = function(form) {
    var o = {};
    $.each(form.serializeArray(), function(index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};
 
/**
 *
 * 增加formatString功能
 *
 * 使用方法：formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 *
 * @returns 格式化后的字符串
 */
formatString = function(str) {
    for ( var i = 0; i < arguments.length - 1; i++) {
        str = str.replace("{" + i + "}", arguments[i + 1]);
    }
    return str;
};
 
 
stringToList = function(value) {
    if (value != undefined && value != '') {
        var values = [];
        var t = value.split(',');
        for ( var i = 0; i < t.length; i++) {
            values.push('' + t[i]);/* 避免他将ID当成数字 */
        }
        return values;
    } else {
        return [];
    }
};
 
 
//$.ajaxSetup({
//  type : 'POST',
//  error : function(XMLHttpRequest, textStatus, errorThrown) {
//      $.messager.progress('close');
//      $.messager.alert('错误', errorThrown);
//  }
//});
/**
 * @author
 *
 * @requires jQuery
 *
 * 判断浏览器是否是IE并且版本小于8
 *
 * @returns true/false
 */
wjc.isLessThanIe7 = function() {
    return ($.browser.msie && $.browser.version < 7);
};
 
//时间格式化
wjc.dateFormat = function (format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }
     
    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds()
        // millisecond
    };
     
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
     
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};