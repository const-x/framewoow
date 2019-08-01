<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

  <div class='dialogContentpanel'> 
		<div id="imgviews" style="MARGIN-RIGHT: auto; MARGIN-LEFT: auto;">
			<%
				String[] images = (String[]) request.getAttribute("images");
				if (images != null) {
					for (int i = 0; i < images.length; i++) {
						if (i % 4 == 0) {
							out.println("<br/>");
						}
						out.println("<img  onclick='ImgShow()'   style='margin:5px;max-width:180px;max-height:160px;'  src ='" + images[i] +  "' />");
					}

				}
			%>
		</div>
	</div>

  <div class="dialogButtonpanel">  
    <div class="buttonPanel">
      <p  style="float:right;text-decoration:none;">
		<input class='easyui-linkbutton' value='关闭'  onclick='closeCurDialog()'>
	  </p>	
	</div>
  </div>

