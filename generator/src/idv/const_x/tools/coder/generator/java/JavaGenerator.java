package idv.const_x.tools.coder.generator.java;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.IGenerator;

public class JavaGenerator implements IGenerator {

	public List<File> generator(Context context) {
		JavaContext ctx = new JavaContext(context);


		ctx.setRepalce("package", context.getBasepackage());
		ctx.setRepalce("author", context.getAuther());
		ctx.setRepalce("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		List<File> files = new ArrayList();
	
		if(context.isMapperAndDao()){
			files.add(new DaoGenerator().generator(ctx));
		}
		if(context.isOtherCodes()){
			files.addAll(new ServiceGenerator().generator(ctx));
//			files.add(new ControllerGenerator().generator(ctx));
		}
		if(context.isEntityCodes()){
			files.addAll(new POJOGenerator().generator(ctx));
		}
		return files;
		
	}
	
	@Override
	public void clear(Context context) throws Exception {
		new DaoGenerator().clear(context);
		new ServiceGenerator().clear(context);
//		new ControllerGenerator().clear(context);
		new POJOGenerator().clear(context);
	}

}
