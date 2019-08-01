package idv.const_x.tools.coder.generator.view;


import java.io.File;
import java.util.ArrayList;
import java.util.List;






import idv.const_x.utils.FileUtils;
import idv.const_x.file.PlaceHodler;
import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.RichTextField;
import idv.const_x.tools.coder.generator.view.field.FieldGeneratorFactory;
import idv.const_x.tools.coder.generator.view.field.IFieldGenerator;

public class CreateViewGenerator {
   
	public File generator(ViewContext context) {
		List<Field> fields = context.getBaseContext().getFields();
		StringBuilder create = new StringBuilder();
		StringBuilder createbigs = new StringBuilder();
		StringBuilder scripts = new StringBuilder();
		StringBuilder validate = new StringBuilder();
		
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
				create.append(generator.getCreateFieldElements());
				validate.append(generator.getCreateValdiatesBeforeSubmit());
				scripts.append(generator.getCreateExpandScripts());
			}
		}
		
		for(Field field : bigFields){
			IFieldGenerator generator = FieldGeneratorFactory.getFieldGenerator(field);
			if(generator != null){
				generator.init(context, field);
				createbigs.append(generator.getCreateFieldElements());
				validate.append(generator.getCreateValdiatesBeforeSubmit());
				scripts.append(generator.getCreateExpandScripts());
			}
		}
		
		context.setRepalce("create", create.toString());
		context.setRepalce("createbigs", createbigs.toString());
		context.setRepalce("scripts", scripts.toString());
		context.setRepalce("validate", validate.toString());
		
		PlaceHodler hodler = new PlaceHodler();
		File file = FileUtils.createFile(context.getSaveBase() + "create.jsp");
		hodler.hodle(context.getTempBase() + "create.tmp",file , context.getRepalce());
		return  file;
	}

	public void clear(Context context) {
		String saveToFile = context.getViewBasepath() +File.separator+ context.getModule().toLowerCase() + File.separator + context.getComponent().toLowerCase() + File.separator;
		saveToFile = saveToFile + "create.jsp";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}
}
