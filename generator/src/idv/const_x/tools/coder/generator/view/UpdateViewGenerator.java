package idv.const_x.tools.coder.generator.view;


import java.io.File;
import java.util.ArrayList;
import java.util.List;




import idv.const_x.file.PlaceHodler;
import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.RichTextField;
import idv.const_x.tools.coder.generator.view.field.FieldGeneratorFactory;
import idv.const_x.tools.coder.generator.view.field.IFieldGenerator;
import idv.const_x.utils.FileUtils;

public class UpdateViewGenerator {
   
	public File generator(ViewContext context) {
		List<Field> fields = context.getBaseContext().getFields();
		StringBuilder update = new StringBuilder();
		StringBuilder updatebigs = new StringBuilder();
		StringBuilder scripts = new StringBuilder();
		StringBuilder validate = new StringBuilder();
		String entry = context.getBaseContext().getEntity().toLowerCase();
		
		if(context.getBaseContext().isTree()){
			update.append("        <input type=\"hidden\" name=\"parent.").append("id")
			.append("\" value=\"${ empty  ").append(entry).append(".parent ? null : ").append(entry).append(".parent.id }\"/>\n");
		}
		
		update.append("<%\n").append(context.getBaseContext().getEntity()).append("View ").append("view = (").append(context.getBaseContext().getEntity())
		.append("View) request.getAttribute(\"").append(entry).append("\");\n").append("%>\n");
		
		List<Field> bigFields =  new ArrayList<Field>();
		for(Field field : fields){
			Type type = field.getType();
			if(field instanceof ImageField || field instanceof RichTextField || type.getLength() > 64){
				bigFields.add(field);
				continue;
			}
			IFieldGenerator generator = FieldGeneratorFactory.getFieldGenerator(field);
			if(generator != null){
				generator.init(context, field);
				update.append(generator.getUpdateFieldElements());
				validate.append(generator.getUpdateValdiatesBeforeSubmit());
				scripts.append(generator.getUpdateExpandScripts());
			}
		}
		
		for(Field field : bigFields){
			IFieldGenerator generator = FieldGeneratorFactory.getFieldGenerator(field);
			if(generator != null){
				generator.init(context, field);
				updatebigs.append(generator.getUpdateFieldElements());
				validate.append(generator.getUpdateValdiatesBeforeSubmit());
				scripts.append(generator.getUpdateExpandScripts());
			}
		}
		
		context.setRepalce("update", update.toString());
		context.setRepalce("updatebigs", updatebigs.toString());
		context.setRepalce("scripts", scripts.toString());
		context.setRepalce("validate", validate.toString());
		
		PlaceHodler hodler = new PlaceHodler();
		File file = FileUtils.createFile(context.getSaveBase() + "update.jsp");
		hodler.hodle(context.getTempBase() + "update.tmp",file , context.getRepalce());
		return  file;
	}
	
	public void clear(Context context) {
		String saveToFile = context.getViewBasepath() +File.separator+ context.getModule().toLowerCase() + File.separator + context.getComponent().toLowerCase() + File.separator;
		saveToFile = saveToFile + "update.jsp";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}
}
