package idv.const_x.tools.coder.generator.java;

import idv.const_x.file.PlaceHodler;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.utils.FileUtils;







import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DaoGenerator  {
   
	public File generator(JavaContext context){
		PlaceHodler hodler = new PlaceHodler();
		Context ctx = context.getBaseContext();
		String templateFile;
		Map<String, String> repalce = context.getRepalce();
		
		if(context.isTree()){
			templateFile = ctx.getTemplateBasepath() +File.separator + "dao"+File.separator + File.separator+"TreeDao.tmp";
		}else{
			templateFile = ctx.getTemplateBasepath() +File.separator + "dao"+File.separator + File.separator+"Dao.tmp";
		}
		String saveToFile = ctx.getJavaBasepath()+File.separator + ctx.getModule().toLowerCase() +File.separator+ "dao" + File.separator+ ctx.getEntity() +"Dao.java";
		File file = FileUtils.createFile(saveToFile);
		
		
		StringBuilder refImports = new StringBuilder();
		StringBuilder ref = new StringBuilder();
		PlaceHodler refHodler = new PlaceHodler();
		String refTemp = ctx.getTemplateBasepath() +File.separator + "dao"+File.separator + File.separator+"RefDao.tmp";
		Set<String> refClasses = new HashSet<String>();
		for(Field field : ctx.getFields()){
			if(!field.getType().isSimpleType()){
				ComplexType type = (ComplexType)field.getType();
				if(type.getRelationship() != ComplexType.ONE_TO_ONE){
					repalce.put("refClass", type.getJavaType());
					repalce.put("refalias", field.getAlias());
					repalce.put("refname", CamelCaseUtils.toFristUpper(field.getName()));
					ref.append(refHodler.hodle(refTemp, repalce)).append("\n");
					String refClass = type.getRefClass();
					if(!refClasses.contains(refClass)){
						refClasses.add(refClass);
						refImports.append("import ").append(refClass).append(";\n");
					}
				}
			}
		}
		repalce.put("refImport", refImports.toString());
		repalce.put("refs", ref.toString());
		
		if(ctx.isNeedAudit()){
			String imports = "import "+ctx.getBasepackage()+".base.entity.OperationHistoryEntity;\n";
			repalce.put("auditImport", imports);
			
			StringBuilder audit = new StringBuilder();
			PlaceHodler hodler2 = new PlaceHodler();
			String expandsTemp = ctx.getTemplateBasepath() +File.separator + "dao"+File.separator + File.separator+"AuditDao.tmp";
			audit.append(hodler2.hodle(expandsTemp, repalce)).append("\n");
			repalce.put("audit", audit.toString());
		}else{
			repalce.put("auditImport", "");
			repalce.put("audit", "");
		}
		
		
		hodler.hodle(templateFile, file, repalce);
		

		return file;
		
	}

	public void clear(Context context) {
		String saveToFile = context.getJavaBasepath() +File.separator+ context.getModule().toLowerCase()+File.separator + "dao" + File.separator+ context.getEntity() +"Dao.java";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}


	
}
