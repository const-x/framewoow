package idv.const_x.tools.coder.daojia.template.example;

import idv.const_x.jdbc.table.meta.Type;

public class ExampleFactory {
    private static  String templateBasepath;
	static {
		templateBasepath = ExampleFactory.class.getResource("/").getPath();
    	templateBasepath = templateBasepath.replace("bin", "src");
    	templateBasepath = templateBasepath + "\\idv\\const_x\\tools\\coder\\daojia\\template\\example\\";
	}
	
	public static String getExampleTmpFile(Type type){
	   if(type.getJavaType().equalsIgnoreCase("long") || type.getJavaType().equalsIgnoreCase("double")
			 ||  type.getJavaType().equalsIgnoreCase("int") || type.getJavaType().equalsIgnoreCase("integer")
			 ||  type.getJavaType().equalsIgnoreCase("float") ){
		   return templateBasepath + "Example4Number.tmp";
	   }	
	   return null;
	}
	
}
