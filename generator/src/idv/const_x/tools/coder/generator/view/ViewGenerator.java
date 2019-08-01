package idv.const_x.tools.coder.generator.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.IGenerator;

public class ViewGenerator implements IGenerator  {

	public List<File>  generator(Context context) {
		List<File> files = new ArrayList();
		if(!context.isViews()){
			return files;
		}
		
		ViewContext ctx = new ViewContext(context);
		
		String baseTmpPath;
//		if(context.isTree()){
//			baseTmpPath = context.getTemplateBasepath() +File.separator +"views"+File.separator + "tree"+File.separator;
//		}else{
//			baseTmpPath = context.getTemplateBasepath() +File.separator + "views"+File.separator + File.separator  ;
//		}
		baseTmpPath = context.getTemplateBasepath() +File.separator + "views"+File.separator + File.separator  ;
		
		String saveToFile = context.getViewBasepath() +File.separator+ context.getModule().toLowerCase() + File.separator + context.getComponent().toLowerCase() + File.separator;
		ctx.setSaveBase(saveToFile);
		ctx.setTempBase(baseTmpPath);
		
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
		
		ctx.setRepalce("auther", context.getAuther());
		
		files.add(new CreateViewGenerator().generator(ctx));
		files.add(new IndexViewGenerator().generator(ctx));
		files.add(new UpdateViewGenerator().generator(ctx));
		if(context.isNeedAudit()){
			files.addAll(new AuditViewGenerator().generator(ctx));
		}
		if(context.hasExport()){
			files.add(new ExportViewGenerator().generator(ctx));
		}
		files.addAll(new RefViewGenerator().generator(ctx));
		return files;
	}

	@Override
	public void clear(Context context) throws Exception {
		new CreateViewGenerator().clear(context);
		new IndexViewGenerator().clear(context);
		new UpdateViewGenerator().clear(context);
		new AuditViewGenerator().clear(context);
		
	}

}
