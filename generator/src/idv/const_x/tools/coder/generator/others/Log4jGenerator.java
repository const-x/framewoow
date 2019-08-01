package idv.const_x.tools.coder.generator.others;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idv.const_x.file.PlaceHodler;
import idv.const_x.utils.FileUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.IGenerator;

public class Log4jGenerator implements IGenerator {
	
	@Override
	public void clear(Context context) throws Exception {
		String filepath = context.getLogBasepath() + File.separator + context.getModule().toLowerCase() +  File.separator + "log4j.properties";
	    File file = new File(filepath);
	    if(file.exists()){
	    	file.delete();
	    }
	}
	
	@Override
	public List<File> generator(Context context){
		List<File> files = new ArrayList<File>(1);
		String filepath = context.getLogBasepath() + File.separator + context.getModule().toLowerCase() +  File.separator + "log4j.properties";
		File file = FileUtils.createFile(filepath);
		files.add(file);
		
		String templateFile = context.getTemplateBasepath() +File.separator + "others"+File.separator + "log4j.tmp";
		PlaceHodler hodler = new PlaceHodler();
		Map<String, String> repalce = new HashMap<String, String>();
		repalce.put("package", context.getBasepackage());
		repalce.put("modulelower", context.getModule().toLowerCase());
		repalce.put("project", "tms");
		hodler.hodle(templateFile, file, repalce);
		return files;
	}
	





	
}
