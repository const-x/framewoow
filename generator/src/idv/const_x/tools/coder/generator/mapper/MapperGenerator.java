package idv.const_x.tools.coder.generator.mapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import idv.const_x.file.FileOutWriter;
import idv.const_x.jdbc.DBType;
import idv.const_x.utils.FileUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.IGenerator;
import idv.const_x.tools.coder.generator.sql.MysqlPermissionGenerator;
import idv.const_x.tools.coder.generator.sql.MysqlTableGenerator;
import idv.const_x.tools.coder.generator.sql.OraclePermissionGenerator;
import idv.const_x.tools.coder.generator.sql.OracleTableGenerator;

public class MapperGenerator implements IGenerator {
	
	@Override
	public void clear(Context context) throws Exception {
		String filepath = context.getMapperBasepath() + File.separator+ File.separator + context.getTable().toLowerCase()+ "-sqlmap.xml";
	    File file = new File(filepath);
	    if(file.exists()){
	    	file.delete();
	    }
	}
	
  
	public List<File> generator(Context context){
		List<File> files = new ArrayList(1);
		if(!context.isMapperAndDao()){
			return files;
		}
		String filepath = context.getMapperBasepath() + File.separator+ File.separator + context.getTable().toLowerCase()+ "-sqlmap.xml";
		File file = FileUtils.createFile(filepath);


		FileOutWriter writer = new FileOutWriter(filepath,true);
		this.createHead(writer, context);
		writer.write("<!-- 返回内容定义 -->\n");
		writer.write(new ResultMapGenerator().generator(context));
		writer.write("\n<!-- select内容 -->\n");
		writer.write(new SelectFieldGenerator().generator(context));
		writer.write(new OperatorGenerator().generator(context));
		this.createFoot(writer);
		writer.close();
		
		files.add(file);
		return files;
	}
	


	private void createHead(FileOutWriter writer,Context context){
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		writer.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		writer.write("<mapper namespace=\"");
		writer.write(context.getRepalce().get("entryFristUpper"));
		writer.write("\" >\n");
	}
	
	private void createFoot(FileOutWriter writer){
		writer.write("</mapper>");
	}



	
}
