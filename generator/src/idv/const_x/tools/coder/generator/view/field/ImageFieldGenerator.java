package idv.const_x.tools.coder.generator.view.field;

import java.util.HashMap;
import java.util.Map;

import idv.const_x.file.PlaceHodler;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.view.ViewContext;

public class ImageFieldGenerator extends AbsFieldGenerator{
	
	
	@Override
	public void init(ViewContext context, Field field) {
		ImageField img = (ImageField)field;
		String profix =super.getProfix(context, field);
		String limited = this.getSizeLimited(img);
		
		StringBuilder create = new StringBuilder();
		if(img.isMultiple()){
			create.append("   <div style='padding:0 10px;'>\n			<table>\n			  <tr>\n				  <td ><label style='width:90px;text-align:right;display:inline-block;'>")
			.append(field.getAlias()).append(":</label></td>\n")
			.append("				  <td><label style='color:red;display:inline-block;'>").append(limited).append(",双击删除图片)</label>").append(profix).append("</td>\n			  </tr>\n")
			.append("			  <tr><td ></td><td><div id='").append(context.getBaseContext().getEntity()).append("_").append(img.getName()).append("views' style='MARGIN-RIGHT: auto; MARGIN-LEFT: auto;'></div>")
			.append("<input id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("'  name='").append(field.getName()).append("' type='hidden' /></td></tr>\n")
			.append("			  <tr><td ></td><td><div id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("_progress' class='progressbar'><div></div></div></td></tr>\n")
			.append("			  <tr><td ></td><td><a href='javascript:;' class='fileupload'>选择文件<input type='file' multiple id='").append(context.getBaseContext().getEntity()).append("_").append(img.getName()).append("_upload' /></a></td></tr>\n")
			.append("			</table>\n   </div>\n");
		}else{
			create.append("   <div style='padding:0 10px;'>\n			<table>\n			  <tr>\n				  <td ><label style='width:90px;text-align:right;display:inline-block;'>")
			.append(field.getAlias()).append(":</label></td>\n")
			.append("				  <td><label style='color:red;display:inline-block;'>").append(limited).append(",双击删除图片)</label>").append(profix).append("</td>\n			  </tr>\n")
			.append("			  <tr><td ></td><td><img id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("_view'  ondblclick='removeUploaded(this)' width='100px'   />")
			.append("<input id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("'  name='").append(field.getName()).append("' type='hidden' /></td></tr>\n")
			.append("			  <tr><td ></td><td><div id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("_progress' class='progressbar'><div></div></div></td></tr>\n")
			.append("			  <tr><td ></td><td><a href='javascript:;' class='fileupload'>选择文件<input type='file' id='").append(context.getBaseContext().getEntity()).append("_").append(img.getName()).append("_upload' /></a></td></tr>\n")
			.append("			</table>\n   </div>\n");
		}
		super.createFieldElements = create.toString();
		
		StringBuilder update =  new StringBuilder();
		String entry = context.getBaseContext().getEntity().toLowerCase();
		if(img.isMultiple()){
			update.append("   <div style='padding:0 10px;'>\n			<table>\n			  <tr>\n				  <td ><label style='width:90px;text-align:right;display:inline-block;'>")
			.append(field.getAlias()).append(":</label></td>\n")
			.append("				  <td><label style='color:red;display:inline-block;'>").append(limited).append(",双击删除图片)</label>").append(profix).append("</td>\n			  </tr>\n")
			.append("			  <tr><td ></td><td><div id='").append(context.getBaseContext().getEntity()).append("_").append(img.getName()).append("views' style='MARGIN-RIGHT: auto; MARGIN-LEFT: auto;'>\n")
			.append("              <%\n")
			.append("              String ").append(img.getName()).append(" = ").append("(String)view.get(\"").append(img.getName()).append("\");\n")
			.append("              if(").append(img.getName()).append(" != null && !").append(img.getName()).append(".equals(\"\") ){\n")
			.append("                 String[] ids = ").append(img.getName()).append(".split(\",\");\n")
			.append("                 String[] images = ").append("(String[])view.get(\"").append(img.getName()).append("Urls\");\n")
			.append("                 for(int i = 0 ; i < images.length;i++){\n")
			.append("                    if(i % 6 == 0){out.println(\"<br/>\");}\n")
			.append("                    out.println(\"<img  width='120px' style='margin:5px' ondblclick='removeMutiUploaded(this)' src ='\"+images[i]+\"' id='\"+ids[i]+\"' />\");}\n")
			.append("              }  %>\n")
			.append("				</div>\n")
			.append("			  <tr><td ></td><td><div id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("_progress' class='progressbar'><div></div></div></td></tr>\n")
			.append("			    <input id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("'  name='").append(field.getName()).append("' type='hidden' /></td></tr>\n")
			.append("			  <tr><td ></td><td><a href='javascript:;' class='fileupload'>选择文件<input type='file' multiple id='").append(context.getBaseContext().getEntity()).append("_").append(img.getName()).append("_upload' /></a></td></tr>\n")
			.append("			</table>\n   </div>\n");
			
			
		}else{
			update.append("   <div style='padding:0 10px;'>\n			<table>\n			  <tr>\n				  <td ><label style='width:90px;text-align:right;display:inline-block;'>")
			.append(field.getAlias()).append(":</label></td>\n")
			.append("				  <td><label style='color:red;display:inline-block;'>").append(limited).append(",双击删除图片)</label>").append(profix).append("</td>\n			  </tr>\n")
			.append("			  <tr><td ></td><td><img id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("_view'  ondblclick='removeUploaded(this)' src='${").append(entry).append(".").append(field.getName()).append("Url}'  width='100px'   />")
			.append("<input id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("'  name='").append(field.getName()).append("'  value='${").append(entry).append(".").append(field.getName()).append("}'  type='hidden' /></td></tr>\n")
			.append("			  <tr><td ></td><td><div id='").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("_progress' class='progressbar'><div></div></div></td></tr>\n")
			.append("			  <tr><td ></td><td><a href='javascript:;' class='fileupload'>选择文件<input type='file' id='").append(context.getBaseContext().getEntity()).append("_").append(img.getName()).append("_upload' /></a></td></tr>\n")
			.append("			</table>\n   </div>\n");
		
		}
		super.updateFieldElements = update.toString();
		
		
		
		StringBuilder validate = new StringBuilder();
		if(img.isMultiple()){
		    validate.append("		var value = \"\";\n");
		    validate.append("		$(\"#").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("views\").children(\"img\").each(function(index,item){\n");
		    validate.append("			value = value + item.id + \",\";\n");
		    validate.append("		});\n");
		    validate.append("		value = value.substring(0,value.length - 1);\n");
		    validate.append("		$(\"#").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("\").attr(\"value\",value);\n");
		}
		if(!field.isNullable()){
			validate.append("		if(!($(\"#").append(context.getBaseContext().getEntity()).append("_").append(field.getName()).append("\").attr(\"value\"))){\n");
			validate.append("			$.messager.alert('错误', '请上传").append(field.getAlias()).append("', 'error');\n");
			validate.append("			return false;\n");
			validate.append("		}\n");
		}
		super.createValdiatesBeforeSubmit = validate.toString();
		super.updateValdiatesBeforeSubmit = validate.toString();
		
		
		
		StringBuilder scripts = new StringBuilder();
		String temp;
		String uploadParam = this.getUploadParam(context, img);
		if(img.isMultiple()){
			temp = "mutiUploadify.tmp";
		}else{
			temp = "uploadify.tmp";
		}
		PlaceHodler hodler = new PlaceHodler();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("field", img.getName());
		properties.put("entity", context.getBaseContext().getEntity());
		properties.put("maxsize", img.getMaxSize() == null ? "20480" :  img.getMaxSize().toString());
		properties.put("size", uploadParam);
		scripts.append(hodler.hodle(context.getTempBase() + temp,properties)).append("\n");
		super.createExpandScripts = scripts.toString();
		super.updateExpandScripts = scripts.toString();
		
	}
	
	
	protected String getSizeLimited(ImageField img){
		StringBuilder create = new StringBuilder();
		if(img.getWidth() != null){
			create.append(" 宽:").append(img.getWidth());
		}else if(img.getMaxWidth() != null){
			create.append(" 限宽:").append(img.getMaxWidth());
		}
		if(img.getHeight() != null){
			create.append(" 高:").append(img.getHeight());
		}else if(img.getMaxHeight() != null){
			create.append(" 限高:").append(img.getMaxHeight());
		}
		if(img.getSize() != null){
			create.append(" ").append(img.getSize()).append(" KB ");
		}else if(img.getMaxSize() != null){
			create.append(" 最大").append(img.getMaxSize()).append(" KB ");
		}
		return create.toString();
	}
	
	protected String getUploadParam(ViewContext context,ImageField img){
		StringBuilder size = new StringBuilder("param=module:");
		size.append(context.getBaseContext().getModule().toLowerCase().trim());
		if (img.getWidth() != null) {
			size.append(",width:").append(img.getWidth());
		}  if (img.getMaxWidth() != null) {
			size.append(",maxWidth:").append(img.getMaxWidth());
		}
		if (img.getHeight() != null) {
			size.append(",height:").append(img.getHeight());
		}else if (img.getMaxHeight() != null) {
			size.append(",maxHeight:").append(img.getMaxHeight());
		}
		if (img.getSize() != null) {
			size.append(",size:").append(img.getSize());
		}else if (img.getMaxSize() != null) {
			size.append(",maxSize:").append(img.getMaxSize());
		} 
		return size.toString();
	}

}
