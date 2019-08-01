package idv.const_x.tools.coder;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;






import idv.const_x.file.search.FileLineReader;
import idv.const_x.file.search.IFileReadHandler;


/**
 * 根据ＶＯ字段生成Field信息
 * @author xieyalong
 *
 */
public class VOFieldReader {
	
	
	public static void main(String[] args){
		String path = "d:\\Users\\bbgds\\Desktop";
		try {
			fieldsReader(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String field; 
	private static String javatype; 
	private static String dbtype; 
	private static String comment; 
	
	
	
	public static void fieldsReader(String basepath) throws IOException{
		FileLineReader reader = new FileLineReader();
		reader.setEncoding("UTF-8");
		reader.setHandler(new IFileReadHandler(){

			@Override
			public void endScale(File[] file) {
				
			}

			@Override
			public void endScaleAFile(File fi) {
			}

			@Override
			public boolean handleLine(File file, String preLine,
					String currLine, String nextLine, int lineNum) {
				currLine = currLine.trim();
				if(StringUtils.isBlank(currLine)){
					return true;
				}
				if(currLine.startsWith("/*") || currLine.startsWith("//")){
					if(StringUtils.isNotBlank(preLine) && StringUtils.isNotBlank(nextLine) && preLine.startsWith("/*") && nextLine.endsWith("*/")){
						comment = currLine;
					}else{
						getComment(currLine);
					}
				}else if(currLine.contains("/*") || currLine.contains("//")){
					int index = currLine.indexOf("/");
					String field = currLine.substring(0,index -1);
					String com = currLine.substring(index);
					getComment(com);
					getField(field);
				}else{
					getField(currLine);
				}
				handler();
				return true;
			}

			@Override
			public boolean scaleException(File file, Throwable e) {
				return true;
			}

			@Override
			public boolean startScale(File[] file) {
				return true;
			}

			@Override
			public boolean startScaleAFile(File file) {
				return true;
			}

			
		});
		reader.findFiles(basepath, null);
	}
	
	private static void getComment(String data){
		data = data.trim();
		if(data.startsWith("/**")){
			comment = data.substring(3).trim();
			comment = comment.substring(0, comment.indexOf("*/")).trim();
		}else if(data.startsWith("/*") ){
			comment = data.substring(2).trim();
			comment = comment.substring(0, comment.indexOf("*/")).trim();
		}else if(data.startsWith("//")  ){
			comment = data.substring(2).trim();
		}
	}
	
	private static void getField(String data){
		String[] datas = data.split(" ");
		if(datas.length == 2){
			javatype = datas[0].trim();
			field = datas[1].trim();
			if(field.endsWith(";")){
				field = field.substring(0,field.length() - 1);
			}
		}else if(datas.length >= 3){
			javatype = datas[1].trim();
			field = datas[2].trim();
			if(field.endsWith(";")){
				field = field.substring(0,field.length() - 1);
			}
		}
		if(javatype != null){
			if(javatype.equalsIgnoreCase("int") || javatype.equalsIgnoreCase("Integer") 
					||javatype.equalsIgnoreCase("double") ||javatype.equalsIgnoreCase("long") ){
				dbtype = "DECIMAL";
			}else{
				dbtype = "VARCHAR";
			}
		}
		
		
	}
	
	private static int counter = 0;
	
	private static void handler(){
		if(StringUtils.isNotBlank(javatype) || StringUtils.isNotBlank(field)){
			StringBuilder sb = new StringBuilder();

			String fieldvar  = "field" + counter;
			
			String java = field;
			int index = java.indexOf("_");
		    if(index != -1){
		    	java = java.substring(index+1);
		    }
		  
			sb.append("   Field ").append(fieldvar).append(" = new Field(\"").append(java).append("\", SimpleTypeEnum.");
			  if(javatype.equalsIgnoreCase("int") || javatype.equalsIgnoreCase("Integer") ){
			    	sb.append("INTEGER");
				}else if(javatype.equalsIgnoreCase("double") ){
					sb.append("DOUBLE");
				}else if(javatype.equalsIgnoreCase("float") ){
					sb.append("FLOAT");
				}else if(javatype.equalsIgnoreCase("long") ){
					sb.append("LONG");
				}else{
					sb.append("STRING");
				}
			
			  sb.append(", \"").append(comment).append("\");\n")
			.append("   ").append(fieldvar).append(".setColumn(\"").append("TOC_"+field.toUpperCase()).append("\");\n")
			.append("   context.addField(").append(fieldvar).append(");\n");
			
			System.out.println(sb.toString());
			counter++;

			
			field = null;
			javatype = null;
			dbtype = null;
			comment = null;
		}
	}
}
