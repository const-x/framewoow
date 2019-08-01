package idv.const_x.tools.coder.init;

import java.io.File;

import idv.const_x.file.search.FileLineReader;
import idv.const_x.file.search.FileLineRepalceHandler;
import idv.const_x.utils.FileUtils;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProjectIniter {

	public static void main(String args[]) throws Exception {
		String basePath = "E:\\workspace\\YT3P_BMS\\";
		String baseContent = "idv.constx.demo";
		String baseRepalce = "com.yt3p.bms";
		
		moveFile(basePath, baseContent, baseRepalce);
		modifyFiles(basePath, baseContent, baseRepalce);
		
		
	}
	
	public static void moveFile(String path,String baseContent,String baseRepalce) throws Exception{
		String origiPath = path ;
		String[] origis =  baseContent.trim().split("\\.");
		for (String string : origis) {
			origiPath += (File.separator+string ); 
		}
		String newPath = path ;
		String[] news =  baseRepalce.trim().split("\\.");
		for (String string : news) {
			newPath += (File.separator+string ); 
		}
		FileUtils.copy(origiPath,newPath);
		FileUtils.delete(path + File.separator + origis[0]);
		System.out.println("已完成文件夹重命名");
	}
	
	

	

	private static void modifyFiles(String basePath,String baseContent,String baseRepalce) throws Exception{
		try{
			
			FileFilter JAVA = new FileNameExtensionFilter("JAVA", "java");
			FileFilter JSP = new FileNameExtensionFilter("JSP", "jsp");
			FileFilter ALL = null;
			
			FileLineReader reader = new FileLineReader();
			FileLineRepalceHandler handler = new FileLineRepalceHandler();
			reader.setHandler(handler);
			
			handler.setReplace(baseRepalce);
			handler.setContent(baseContent);
			
			reader.findFiles(basePath + "src\\main\\java", JAVA);
			reader.findFiles(basePath + "src\\main\\resources", ALL);
			reader.findFiles(basePath + "src\\main\\webapp\\WEB-INF\\views", JSP);
			System.out.println("项目初始化成功!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
