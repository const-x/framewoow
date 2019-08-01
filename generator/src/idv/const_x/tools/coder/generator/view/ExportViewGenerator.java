package idv.const_x.tools.coder.generator.view;


import java.io.File;


import idv.const_x.utils.FileUtils;
import idv.const_x.file.PlaceHodler;
import idv.const_x.tools.coder.generator.Context;

public class ExportViewGenerator {
   
	public File generator(ViewContext context) {
	
		PlaceHodler hodler = new PlaceHodler();
		File file = FileUtils.createFile(context.getSaveBase() + "export.jsp");
		hodler.hodle(context.getTempBase() + "export.tmp",file , context.getRepalce());
		return  file;
	}
	
	public void clear(Context context) {
		String saveToFile = context.getViewBasepath() +File.separator+ context.getModule().toLowerCase() + File.separator + context.getComponent().toLowerCase() + File.separator;
		saveToFile = saveToFile + "export.jsp";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}
}
