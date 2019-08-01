		
            
function ImgShow(evt){
 var imgTag=(window.event)?event.srcElement:evt.target;
 var imgPath=imgTag.src.replace(/\_\d\./,"_4.");//取得弹出的大图url
 var tagTop=Math.max(document.documentElement.scrollTop,document.body.scrollTop);
 var tag=document.createElement("div");
  tag.style.cssText="width:100%;height:"+Math.max(document.body.clientHeight,document.body.offsetHeight,document.documentElement.clientHeight)+"px;position:absolute;background:black;top:0;filter: Alpha(Opacity=80);Opacity:0.8;z-index:9800";
  tag.ondblclick=closes;
 var tagImg=document.createElement("div");
  tagImg.style.cssText="font:12px /18px verdana;overflow:auto;text-align:center;position:absolute;width:200px;border:5px solid white;background:white;color:white;left:"+(parseInt(document.body.offsetWidth)/2-100)+"px;z-index:9801;top:"+(document.documentElement.clientHeight/3+tagTop)+"px;"
  tagImg.innerHTML="<div style='padding:10px;background:#cccccc;border:1px solid white'><img src='./images/loading.gif' /><br /><br /><b style='color:#999999;font-weight:normal'>Image loading...</b><br /></div>";
  tagImg.oncontextmenu=function(){var clsOK=confirm("确定要取消图片显示吗?点击确定将关闭图片。\n如果您是想缩放图片请在图片上双击。"); if(clsOK){closes();};return false};
 var closeTag=document.createElement("div");
  closeTag.style.cssText="display:none;position:absolute;left:10px;top:10px;color:black;";
  var closesHtml="<b style='background:red;border:1px solid red;filter:Alpha(Opacity=50);Opacity:0.5;cursor:pointer;'>&nbsp;关闭&nbsp;</b>";
  closeTag.innerHTML=closesHtml+" 提示:双击图片缩放";
  closeTag.onclick=closes;
 document.body.appendChild(tag);
 document.body.appendChild(tagImg);
 var img=new Image();
  img.src=imgPath;
  img.style.cssText="border:1px solid #cccccc;filter: Alpha(Opacity=0);Opacity:0;cursor:pointer";
 var barShow,imgTime;
 img.complete?ImgOK():img.onload=ImgOK;

 function ImgOK(){
  var Stop1=false,Stop2=false,temp=0;
  var tx=tagImg.offsetWidth,ty=tagImg.offsetHeight;
  var ix=img.width,iy=img.height;
  var sx=document.documentElement.clientWidth,sy=window.innerHeight||document.documentElement.clientHeight;
  if(iy>sy||ix>sx){
    var yy=sy-100;
    var xx=(ix/iy)*yy;
  }else{
   var xx=ix+4;
   var yy=iy+3;
  }
   img.style.width=xx-4+'px';
   img.style.height=yy-3+'px';
  if(ix<sx&&iy<sy){tagImg.alt="";closeTag.innerHTML=closesHtml;};
  var maxTime=setInterval(function(){
   temp+=35;
   if((tx+temp)<xx){
    tagImg.style.width=(tx+temp)+"px";
    tagImg.style.left=(sx-(tx+temp))/2+"px";
   }else{
    Stop1=true;
    tagImg.style.width=xx+"px";
    tagImg.style.left=(sx-xx)/2+"px";
   }
   if((ty+temp)<yy){
    tagImg.style.height=(ty+temp)+"px";
    tagImg.style.top=(tagTop+((sy-(ty+temp))/2))+"px";
   }else{
    Stop2=true;
    tagImg.style.height=yy+"px";
    tagImg.style.top=(tagTop+((sy-yy)/2))+"px";
   }
   if(Stop1&&Stop2){
    clearInterval(maxTime);
    tagImg.appendChild(img);
    temp=0;
    imgOPacity();
   }
  },1);
  function imgOPacity(){
   temp+=10;
   img.style.filter="alpha(opacity="+temp+")";
   img.style.opacity=temp/100;
   imgTime=setTimeout(imgOPacity,1)
   if(temp>100) clearTimeout(imgTime);
  }
  tagImg.innerHTML="";
  tagImg.appendChild(closeTag);
   
  if(ix>xx||iy>yy){
   img.alt="左键拖动,双击放大缩小";
   img.ondblclick=function (){
    if(tagImg.offsetWidth<img.offsetWidth||tagImg.offsetHeight<img.offsetHeight){
     img.style.width="auto";
     img.style.height="100%";
     img.alt="双击可以放大";
     img.onmousedown=null;
     closeTag.style.top="10px";
     closeTag.style.left="10px";
     tagImg.style.overflow="visible";
     tagImg.style.width=img.offsetWidth+"px";
     tagImg.style.left=((sx-img.offsetWidth)/2)+"px";
    }else{
     img.style.width=ix+"px";
     img.style.height=iy+"px";
     img.alt="双击可以缩小";
     img.onmousedown=dragDown;
     tagImg.style.overflow="auto";
     tagImg.style.width=(ix<sx-100?ix+20:sx-100)+"px";
     tagImg.style.left=((sx-(ix<sx-100?ix+20:sx-100))/2)+"px";
    }
   }
   img.onmousedown=dragDown;
   tagImg.onmousemove=barHidden;
   tagImg.onmouseout=moveStop;
   document.onmouseup=moveStop;
  }else{
   tagImg.style.overflow="visible";
   tagImg.onmousemove=barHidden;
  }
 }
 function dragDown(a){
  img.style.cursor="move";
  var evts=a||window.event;
  var onx=evts.clientX,ony=evts.clientY;
  var sox=tagImg.scrollLeft,soy=tagImg.scrollTop;
  var sow=img.width+2-tagImg.clientWidth,soh=img.height+2-tagImg.clientHeight;
  var xxleft,yytop;
  document.onmousemove=function(e){
   var evt=e||window.event;
   xxleft=sox-(evt.clientX-onx)<0?"0":sox-(evt.clientX-onx)>sow?sow:sox-(evt.clientX-onx);
   yytop=soy-(evt.clientY-ony)<0?"0":soy-(evt.clientY-ony)>soh?soh:soy-(evt.clientY-ony);
   tagImg.scrollTop=yytop;
   tagImg.scrollLeft=xxleft;
   closeTag.style.top=(yytop+10)+"px";
   closeTag.style.left=(xxleft+10)+"px";
   return false;
  }
  return false;
 }
 function barHidden(){
  clearTimeout(barShow);
  closeTag.style.top=(tagImg.scrollTop+10)+"px";
  closeTag.style.left=(tagImg.scrollLeft+10)+"px";
  if(closeTag.style.display=="none")closeTag.style.display="block";
  barShow=setTimeout(function(){closeTag.style.display="none";},1000);
 }
 function closes(){
  document.body.removeChild(tag);
  document.body.removeChild(tagImg);
  clearTimeout(barShow);
  clearTimeout(imgTime);
  document.onmouseup=null;
  tag=img=tagImg=closeTag=null;
 }
 function moveStop(){
  document.onmousemove=null;
  tagImg.onmousemove=barHidden;
  img.style.cursor="pointer";
 }
}
