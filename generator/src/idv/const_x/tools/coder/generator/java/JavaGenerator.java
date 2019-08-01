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
		String moduleFristlower =  context.getModule().substring(0, 1).toLowerCase() + context.getModule().substring(1);
		String entryFristlower =  context.getComponent().substring(0, 1).toLowerCase() + context.getComponent().substring(1);
		String entityFristlower =  context.getEntity().substring(0, 1).toLowerCase() + context.getEntity().substring(1);
		
		ctx.setRepalce("modulelower", context.getModule().toLowerCase());
		ctx.setRepalce("moduleAlias", context.getModuleAlias());
		ctx.setRepalce("moduleFristUpper", context.getModule());
		ctx.setRepalce("moduleFristLower", moduleFristlower);
		
		ctx.setRepalce("componentlower", context.getComponent().toLowerCase());
		ctx.setRepalce("componentAlias", context.getComponentAlias());
		ctx.setRepalce("componentFristUpper", context.getComponent());
		ctx.setRepalce("componentFristLower", entryFristlower);
		
		ctx.setRepalce("entrylower", context.getEntity().toLowerCase());
		ctx.setRepalce("entityAlias", context.getEntityAlias());
		ctx.setRepalce("entryFristUpper",  context.getEntity());
		ctx.setRepalce("entryFristlower", entityFristlower);
		
		ctx.setRepalce("package", context.getBasepackage());
		ctx.setRepalce("author", context.getAuther());
		ctx.setRepalce("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		List<File> files = new ArrayList();
	
		if(context.isMapperAndDao()){
			files.add(new DaoGenerator().generator(ctx));
		}
		if(context.isOtherCodes()){
			files.addAll(new ServiceGenerator().generator(ctx));
			files.add(new ControllerGenerator().generator(ctx));
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
		new ControllerGenerator().clear(context);
		new POJOGenerator().clear(context);
	}

}
