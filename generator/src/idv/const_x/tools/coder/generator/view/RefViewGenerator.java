package idv.const_x.tools.coder.generator.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import idv.const_x.tools.coder.generator.Field;
import idv.const_x.utils.FileUtils;
import idv.const_x.file.PlaceHodler;
import idv.const_x.jdbc.table.meta.ComplexType;

public class RefViewGenerator {
   
	public List<File> generator(ViewContext context) {
		List<File> files = new ArrayList<File>();
		PlaceHodler hodler = new PlaceHodler();
		
		for(Field field : context.getBaseContext().getFields()){
			if(!field.getType().isSimpleType() && field.isEditable()){
				ComplexType type = (ComplexType)field.getType();
				context.setRepalce("field", field.getName());
				context.setRepalce("refNameField", type.getRefNameField());
				context.setRepalce("refIDField", type.getRefIDField());
				context.setRepalce("fieldAlias", field.getAlias());
				File file = FileUtils.createFile(context.getSaveBase() + "lookup_" + field.getName() + ".jsp");
				String temp = context.getTempBase() + "lookup.tmp";
				if(type.getRelationship() != ComplexType.ONE_TO_ONE){
					temp = context.getTempBase() + "batchlookup.tmp";
				}
				hodler.hodle(temp,file , context.getRepalce());
				files.add(file);
			}
		}
		return  files;
	}
	
	public void clear(ViewContext context) {
		
		for(Field field : context.getBaseContext().getFields()){
			if(!field.getType().isSimpleType() && field.isEditable()){
				File file = FileUtils.createFile(context.getSaveBase() + "lookup_" + field.getName() + ".jsp");
				if (file.exists()) {
					file.delete();
				}
			}
		}
		
		
	}
}
