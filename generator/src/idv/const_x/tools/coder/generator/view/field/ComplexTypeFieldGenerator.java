package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.view.ViewContext;

public class ComplexTypeFieldGenerator  extends AbsFieldGenerator {

	@Override
	public void init(ViewContext context, Field field) {
		StringBuilder create = new StringBuilder();
		String profix = super.getProfix(context, field);
		ComplexType ctype = (ComplexType) field.getType();
		
		if(ctype.getRelationship() == ComplexType.ONE_TO_ONE){
			
			create.append("		<tr>\n").append("			<td>").append(field.getAlias()).append(":</td>\n")
			.append("			<td>\n").append("			   <div class=\"lookupField\">\n")
			.append("				<input class='easyui-combobox'  name='").append(field.getName()).append(".").append(ctype.getRefNameField()).append("' ")
			.append("  data-options=\"hasDownArrow:false,").append(field.isNullable() ? "":"required:true,").append("mode:'remote',forceSelect:true,textField:'").append(ctype.getRefNameField()).append("',valueField:'").append(ctype.getRefNameField()).append("'\" ")
			.append("\n			      url='<%=basePath %>/").append(context.getBaseContext().getComponent().toLowerCase()).append("/lookup2").append(field.getName()).append("' ")
			.append("  lookupGroup='").append(field.getName()).append("' lookupfield='").append(ctype.getRefNameField()).append("' />\n ")
			.append("				<a class='easyui-linkbutton' iconCls='icon-search' href='javascript:void(0)' lookupGroup='").append(field.getName())
			.append("' url='<%=basePath %>/").append(context.getBaseContext().getComponent().toLowerCase()).append("/lookup2").append(field.getName()).append("' onclick='lookup(this)'></a>\n")
			.append("			    <input  class='easyui-combobox' name='").append(field.getName()).append(".").append(ctype.getRefIDField()).append("' lookupGroup='").append(field.getName()).append("' lookupfield='").append(ctype.getRefIDField()).append("' type='hidden'/>\n")
			.append("			   </div>\n			</td>\n			").append(profix).append("\n		</tr>\n");
			
			super.createFieldElements = create.toString();
			
			String entry = context.getBaseContext().getEntity().toLowerCase();
			create = new StringBuilder();
			
			create.append("		<tr>\n").append("			<td>").append(field.getAlias()).append(":</td>\n")
			.append("			<td>\n").append("			   <div class=\"lookupField\">\n")
			.append("				<input class='easyui-combobox' ")
			.append(" value=\"${ empty ").append(entry).append(".").append(field.getName()).append(" ? '' : ").append(entry).append(".").append(field.getName()).append(".").append(ctype.getRefNameField()).append(" }\"")
			.append("  data-options=\"hasDownArrow:false,").append(field.isNullable() ? "":"required:true,").append("mode:'remote',forceSelect:true,textField:'").append(ctype.getRefNameField()).append("',valueField:'").append(ctype.getRefNameField()).append("',")
			.append("${ empty ").append(entry).append(".").append(field.getName()).append(" ? '' : 'select:true' }\"")
			.append("\n			      url='<%=basePath %>/").append(context.getBaseContext().getComponent().toLowerCase()).append("/lookup2").append(field.getName()).append("' ")
			.append("  lookupGroup='").append(field.getName()).append("' lookupfield='").append(ctype.getRefNameField()).append("' />\n ")
			.append("				<a class='easyui-linkbutton' iconCls='icon-search' href='javascript:void(0)' lookupGroup='").append(field.getName())
			.append("' url='<%=basePath %>/").append(context.getBaseContext().getComponent().toLowerCase()).append("/lookup2").append(field.getName()).append("' onclick='lookup(this)'></a>\n")
			.append("			    <input class='easyui-combobox'  name='").append(field.getName()).append(".").append(ctype.getRefIDField()).append("' lookupGroup='").append(field.getName()).append("' lookupfield='").append(ctype.getRefIDField())
			.append("' value=\"${ empty ").append(entry).append(".").append(field.getName()).append(" ? '' : ").append(entry).append(".").append(field.getName()).append(".").append(ctype.getRefIDField()).append(" }\" type='hidden'/>\n")
			.append("			   </div>\n				</td>\n			").append(profix).append("\n		</tr>\n");
			
			super.updateFieldElements = create.toString();
		}else{
			create.append("		<tr>\n").append("			<td>").append(field.getAlias()).append(":</td>\n")
			.append("			<td>\n").append("			   <div class=\"lookupField\">\n")
			.append("				<input class='easyui-combobox'  ")
			.append("  data-options=\"hasDownArrow:false,").append(field.isNullable() ? "":"required:true,").append("multiline:true,height:24,width:300\" editable='false'")
			.append("  lookupGroup='").append(field.getName()).append("' lookupfield='").append(ctype.getRefNameField()).append("' />\n ")
			.append("				<a class='easyui-linkbutton' iconCls='icon-search' href='javascript:void(0)' lookupGroup='").append(field.getName())
			.append("' url='<%=basePath %>/").append(context.getBaseContext().getComponent().toLowerCase()).append("/lookup2").append(field.getName()).append("/-1' onclick='lookup(this)'></a>\n")
			.append("			    <div  lookupGroup='").append(field.getName()).append("'  style='display:none'></div>\n ")
			.append("			   </div>\n			</td>\n			").append(profix).append("\n		</tr>\n");
			
			super.createFieldElements = create.toString();
			
			String entry = context.getBaseContext().getEntity().toLowerCase();
			create = new StringBuilder();
			
			create.append("		<tr>\n").append("			<td>").append(field.getAlias()).append(":</td>\n")
			.append("			<td>\n").append("			   <div class=\"lookupField\">\n")
			.append("				<input class='easyui-combobox' name='").append(field.getName()).append(".").append(ctype.getRefNameField()).append("' ")
			.append(" value=\"${ empty ").append(entry).append(".").append(field.getName()).append("Str ? '' : ").append(entry).append(".").append(field.getName()).append("Str }\"")
			.append("  data-options=\"hasDownArrow:false,").append(field.isNullable() ? "":"required:true,").append("multiline:true,height:24,width:300\" editable='false'")
			.append("  lookupGroup='").append(field.getName()).append("' lookupfield='").append(ctype.getRefNameField()).append("' />\n ")
			.append("				<a class='easyui-linkbutton' iconCls='icon-search' href='javascript:void(0)' lookupGroup='").append(field.getName())
			.append("' url='<%=basePath %>/").append(context.getBaseContext().getComponent().toLowerCase()).append("/lookup2").append(field.getName()).append("/${ empty viper.").append(field.getName()).append("Ids ? \"-1\" : viper.").append(field.getName()).append("Ids}' onclick='lookup(this)'></a>\n")
			.append("			    <div  lookupGroup='").append(field.getName()).append("'  style='display:none'>\n ")
			.append("					<c:if test='${not empty ").append(entry).append(".").append(field.getName()).append("}'>\n")
			.append("			            <c:set var='ind' value='${0}'/>\n")
			.append("					    <c:forEach var='item' items='${").append(entry).append(".").append(field.getName()).append("}'>\n")
			.append("					      <input type='hidden' name='").append(field.getName()).append("[${ind}].").append(ctype.getRefIDField()).append("' value='${item.").append(ctype.getRefIDField()).append("}' />\n")
			.append("					      <input type='hidden' name='").append(field.getName()).append("[${ind}].").append(ctype.getRefNameField()).append("' value='${item.").append(ctype.getRefNameField()).append("}' />\n")
			.append("					      <c:set var='ind' value='${ind + 1 }'/>\n")
			.append("					    </c:forEach>\n")
			.append("					</c:if>\n")
			.append("			    </div>\n")
			.append("			   </div>\n				</td>\n			").append(profix).append("\n		</tr>\n");
			
			super.updateFieldElements = create.toString();
		}

		
	}

}
