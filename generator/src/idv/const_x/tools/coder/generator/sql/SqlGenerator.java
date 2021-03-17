package idv.const_x.tools.coder.generator.sql;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import idv.const_x.file.FileOutWriter;
import idv.const_x.jdbc.DBType;
import idv.const_x.utils.FileUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.IGenerator;


public class SqlGenerator implements IGenerator{

	public List<File> generator(Context context) {
		List<File> files = new ArrayList(1);
		if(!context.isSqls()){
			return files;
		}
		
		String filepath = context.getSqlBasepath() + File.separator + context.getModule().toLowerCase()  +"_init.sql";
		File file = FileUtils.createFile(filepath);
		FileOutWriter writer = new FileOutWriter(filepath,false);
		if(context.getDbType() == DBType.ORACLE){
//			writer.write(new OraclePermissionGenerator().generator(context));
			writer.write(new OracleTableGenerator().generator(context));
		}else{
//			writer.write(new MysqlPermissionGenerator().generator(context));
			writer.write(new MysqlTableGenerator().generator(context));
		}
		
//		writer.write(new MetaGenerator().generator(context));
		
		writer.close();
		
		files.add(file);
		return files;
	}


	@Override
	public void clear(Context context) throws Exception {
		String filepath = context.getSqlBasepath() + File.separator + context.getModule().toLowerCase() +  File.separator +context.getComponent().toLowerCase() +"_init.sql";
	    File file = new File(filepath);
	    if(file.exists()){
	    	file.delete();
	    }
	}

}
