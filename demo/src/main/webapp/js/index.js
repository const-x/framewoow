$(function() {
	
	initSize();
	$(window).resize(function() {
		windowResize();
	});
	
	var Num= 1;
	$("#fold_left").on("click",function(){
		var sidebarWidth = 110;
		$('#easyui-layout-so').layout();
		if(Num == 1){
			 $('#easyui-layout-so').layout('collapse','west');
			 $("#fold_left").css({"background-position":"left -31px"});
			 //$("#fold_left").css({"background-position":"left 0px","left":0});
			 $("#blo").animate({left:-sidebarWidth});
			 $("#fold_left").animate({left:0});
			 $("#blo").css({"width":sidebarWidth});
			 Num = 2;
		}else{
			$("#blo").animate({left:0});
			$("#fold_left").css({"background-position":"left 0px"});
			$('#easyui-layout-so').layout('expand','west');
			 $("#fold_left").animate({left:sidebarWidth});
 			$("#blo").css({"width":"0"});
			Num = 1;
		}
	});

	imgTop();
	hov();
	nextFlow();

	if ($("#left").hasClass("panel") || $("#left").hasClass("layout-panel")) {
		$("#left").addClass("overFlow");
		$("#left").css({
			"overflow" : "visible"
		});
	}

	$("#mm").menu({
		onClick : function(item) {
			var id = item.id;
			var currTitle = $("#mm").data("title");
			menuClick(id, currTitle);
		}
	});

	addPlugins();

	hoverli();
	
	if(!+[1,]){
	    alert("检测到您使用的是IE内核浏览器，这可能造成系统显示不正确或不稳定，强烈建议您使用google chrome或其他标准浏览器");	
	}
});


function changeTheme(record){
	var type = record.value;  
    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href = url.substring(0, url.indexOf('themes'))+ 'themes/' + type + '/easyui.css';
    $easyuiTheme.attr('href',href);
    $.cookie('easyuiThemeName', type, {
        expires : 7
    });
};

function nextFlow() {
	var marginT, imgH, marginW, imgW, imgW1;
	marginW = $(".pageFormContentStep dl dt").width()
			- $(".pageFormContentStep dl dt img").width();
	marginT = $(".pageFormContentStep dl dt").height()
			- $(".pageFormContentStep dl dt img").height();
	imgW = marginW / 2 - 47;
	imgW1 = marginW / 2 + 30;
	imgH = marginT / 2;
	$(".pageFormContentStep dl dt img").css({
		"margin-left" : imgW1,
		"margin-top" : imgH
	});
	$(".pageFormContentStep dl dt.active_next img").css({
		"margin-left" : imgW,
		"margin-top" : imgH
	});
}
function imgTop() {
	var marginTop, h;
	marginTop = $(".sidebar_ul_li span").height()
			- $(".sidebar_ul_li span img").height();
	h = marginTop / 2;
	$(".sidebar_ul_li span img").css({
		"marginTop" : h
	});

};

var navHeight = 26;
var sidebarWidth = 110;


function initSize(){
	$(".layout").height($(window).height());
	$("#container").width($(window).width() - sidebarWidth);
	$("#container").height($(window).height() - navHeight);
	$(".sidebar_li_ul").height($(window).height());
	$(".sidebar_li_div").height($(window).height());
	$(".layout_left").height($(window).height() - navHeight);
	$(".layout_right").width($(window).width() - sidebarWidth);
	$(".left").width(sidebarWidth);
	$("#blo").css({"height":$(window).height()});
	$(".fold_left").css({"top":navHeight + 30});
}

function windowResize() {
	initSize();
	$('#tabs').tabs('resize',{  
		width:$("#container").width(),  
		height:$("#container").height() 
	}); 
};

function hov() {
	$(".sidebar_li_ul").height($(window).height());
	$(".sidebar_li_div").height($(window).height());
	$(".sidebar_li_div").css({"margin-top" : -48});
	
	$(".sidebar_li_ul").hide();
	$(".sidebar_li_div").hide();
	var liTop, liLen, liHeight, ulHeight, hValue;
	$(".sidebar_ul_li").hover(function() {
		var img = $(this).find("img");
		var src = img.attr("hoverImg");
		$(this).find("img").attr("src", src);
		$(this).find(".sidebar_li_ul").show();
		$(this).find(".sidebar_li_div").show();
		liTop = $(this).offset().top;
		$(this).find(".sidebar_li_ul li:first-child").css({
			"margin-top" : liTop
		});
		winHeight = window.innerHeight;
		liLen = $(this).find(".sidebar_li_ul li").length;
		liHeight = $(".sidebar_li_ul li").height();
		ulHeight = liHeight * liLen;
		hValue = ulHeight + liTop - winHeight;
		if (hValue < 0) {
			$(this).find(".sidebar_li_ul li:first-child").css({
				"margin-top" : liTop
			});
		}
		if (hValue >= 0) {
			$(this).find(".sidebar_li_ul li:first-child").css({
				"margin-top" : liTop - hValue - 20
			});
		}

	}, function() {
		var img = $(this).find("img");
		var src = img.attr("comImg");
		$(this).find("img").attr("src", src);
		$(this).find(".sidebar_li_ul").hide();
		$(this).find(".sidebar_li_div").hide();
		if ($(this).hasClass("color1")) {
			var img = $(this).find("img");
			var src = img.attr("hoverImg");
			$(this).find("img").attr("src", src);
		}
	});
}

function hoverli(){
	$(".sidebar_li_ul li").hover(function(){
		this_ind = $(this).index();
		$(this).css({"border-bottom":"1px solid #c3e4ff","background":"#c3e4ff"});
		$(this).prev().css({"border-bottom":"1px solid #c3e4ff"});
	},function(){
		$(".sidebar_li_ul li").css({"border-bottom":"1px solid #cdcdcd","background":""});
	});
}


