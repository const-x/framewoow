package idv.const_x.tools.coder.generator.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.utils.FileUtils;
import idv.const_x.file.PlaceHodler;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.jdbc.table.meta.Type;

public class AuditViewGenerator {
   
	public List<File> generator(ViewContext context) {
		List<File> files = new ArrayList<File>(3);
		
		PlaceHodler hodler = new PlaceHodler();
		File file = FileUtils.createFile(context.getSaveBase() + "submit.jsp");
		hodler.hodle(context.getTempBase() + "submit.tmp",file , context.getRepalce());
		files.add(file);
		
		file = FileUtils.createFile(context.getSaveBase() + "auditindex.jsp");
		hodler.hodle(context.getTempBase() + "auditindex.tmp",file , context.getRepalce());
		files.add(file);
		
		file = FileUtils.createFile(context.getSaveBase() + "audit.jsp");
		hodler.hodle(context.getTempBase() + "audit.tmp",file , context.getRepalce());
		files.add(file);
		
		return  files;
	}
	
	public void clear(Context context) {
		String saveToFile = context.getViewBasepath() +File.separator+ context.getModule().toLowerCase() + File.separator + context.getComponent().toLowerCase() + File.separator;
		saveToFile = saveToFile + "index.jsp";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}
}
