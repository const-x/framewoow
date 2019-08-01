<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div style="width:780px;">
	
	<div style="width:43%;float:left;margin-left:1%;">
	      <h1 class="contentTitle">已选字段</h1>
		<div id="selectedFields" style="width:100%;border:1px solid #ebebeb;margin-top:2px;height:237px;background:#FFFFFF;overflow:auto;">
			<c:forEach var="item" items="${selectFields}">
	            <c:if test="${item.selected}">
		            <div style="border: 1px solid #ebebeb; padding: 5px; margin: 5px; display: block;" onClick="onSelected(this)">
						<input type="hidden" name="selectedFields"  value="${item.name}"  />${item.alias}
					</div>
	            </c:if>
	        </c:forEach> 
		</div>
	</div>	
	
	<div style="width:10%;float:left;" align="center">
	    <h1 class="contentTitle">操作</h1>
		<div style="width:100%;margin-left:10px;margin-right:10px;margin-top:5px;min-height:237px">
		  
		  <button style="margin-bottom:20px;" type="button" onClick="toLeft()">&nbsp;>&nbsp;</button>
		  <button type="button" onClick="toRight()">&nbsp;<&nbsp;</button>
		  
		  <button style="margin-bottom:20px;" type="button" onClick="toLeftALL()">>></button>
		  <button type="button" onClick="toRightAll()"><<</button>
		  
		  
		  
		  <button type="button" onClick="sortUp()">&nbsp;↑&nbsp;</button>
		  <button type="button" onClick="sortDown()">&nbsp;↓&nbsp;</button>
		</div>
	</div>
	
    <div style="width:43%;float:right;">
	    <h1 class="contentTitle">可选字段</h1>
		<div id="unSelectedFields" style="width:100%;border:1px solid #ebebeb;margin-top:2px;height:240px;background:#FFFFFF;overflow:auto;">
		        <c:forEach var="item" items="${selectFields}">
		            <c:if test="${!item.selected}">
			            <div style="border: 1px solid #ebebeb; padding: 5px; margin: 5px; display: block;" onClick="onSelected(this)">
							<input type="hidden" name="selectedFields"  orgi="${item.name}"  />${item.alias}
						</div>
		            </c:if>
		        </c:forEach> 
		</div>
	</div>
	<style type="text/css">
	 .selectedfield {
	    background-color: #ebebeb;
	 }
	</style>

	<script type="text/javascript">
	
	function onSelected(div){
		if($(div).hasClass('selectedfield')){
			$ (div).removeClass ("selectedfield");
		}else{
			$ (div).addClass ("selectedfield");
		}
	}
	
	function toRight(){
		$.each($('#unSelectedFields div'), function(index, item) {
			if($(item).hasClass('selectedfield')){
				selected(item);
			}
    	});	
	}
	
	function toLeft(){
		$.each($('#selectedFields div'), function(index, item) {
			if($(item).hasClass('selectedfield')){
				unSelected(item);
			}
    	});
	}
	
    
    function toRightAll(){
    	$.each($('#unSelectedFields div'), function(index, item) {
    		selected(item);
    	});
	}
    
    function toLeftALL(){
    	$.each($('#selectedFields div'), function(index, item) {
    		unSelected(item);
    	});
	}
	
    function selected(div){
    	var input = $($($(div).children()).get(0));
    	var value = input.attr("orgi");
    	input.attr("value",value);
    	if($(div).hasClass('selectedfield')){
			$ (div).removeClass ("selectedfield")
		}
    	document.getElementById("unSelectedFields").removeChild(div);
    	document.getElementById("selectedFields").appendChild(div);
    }
  
    function unSelected(div){
    	var input = $($($(div).children()).get(0));
    	input.removeAttr("value");
    	if($(div).hasClass('selectedfield')){
			$ (div).removeClass ("selectedfield")
		}
    	document.getElementById("selectedFields").removeChild(div)
    	document.getElementById("unSelectedFields").appendChild(div)
    }
    
    function sortUp(){
    	var divs = new Array();
    	$.each($('#selectedFields div'), function(index, item) {
			if($(item).hasClass('selectedfield') && index !=0){
				var div = divs[index - 1];
				divs[index] = div;
				divs[index - 1] = $(item);
			}else{
				divs[index] = $(item);
			}
    	});
    	$('#selectedFields').empty();
    	for(var i =0; i<divs.length; i++){
    		$('#selectedFields').append(divs[i]);
    	}
	}
    
    function sortDown(){
    	var divs = new Array();
    	$.each($('#selectedFields div'), function(index, item) {
			divs[index] = $(item);
    	});
    	for(var i = divs.length - 2; i>=0; i--){
    		var div = divs[i];
    		if(div.hasClass('selectedfield') ){
				divs[i] = divs[i + 1];
				divs[i + 1] = div;
    		}
    	}
    	$('#selectedFields').empty();
       	for(var i =0; i<divs.length; i++){
    		$('#selectedFields').append(divs[i]);
    	}
	}
	</script>
</div>
	