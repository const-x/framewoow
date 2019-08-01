  var zooming=function(e){  
        e=window.event ||e;  
        var o=this,data=e.wheelDelta || -e.detail*40,zoom,size;  
        if(!+'\v1'){//IE  
            zoom = parseInt(o.style.zoom) || 100;   
            zoom += data / 12;  
            if(zoom > zooming.min)   
                o.style.zoom = zoom + '%';  
            e.returnValue=false;  
        }else {  
            size=o.getAttribute("_zoomsize").split(",");  
            zoom=parseInt(o.getAttribute("_zoom")) ||100;  
            zoom+=data/12;  
            if(zoom>zooming.min){  
                o.setAttribute("_zoom",zoom);  
                o.style.width=size[0]*zoom/100+"px";  
                o.style.height=size[1]*zoom/100+"px"; 
            }  
       
            e.preventDefault();//阻止默认行为  
       
            e.stopPropagation();//for Firefox3.6  
        }  
    };  
    zooming.add=function(obj,min){//第一个参数指定可以缩放的图片，min指定最小缩放的大小 ,default to 50  
        zooming.min=min || 50;  
        obj.onmousewheel=zooming;  
        if(/Firefox/.test(navigator.userAgent))//if Firefox  
            obj.addEventListener("DOMMouseScroll",zooming,false);  
        if(-[1,]){//if not IE  
            obj.setAttribute("_zoomsize",obj.offsetWidth+","+obj.offsetHeight);  
        }  
    };  