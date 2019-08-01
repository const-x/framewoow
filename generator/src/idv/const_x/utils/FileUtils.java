package idv.const_x.utils;


import idv.const_x.file.FileOutWriter;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


public class FileUtils {



	public static void openFile(File file) throws IOException {
		Desktop.getDesktop().open(file);
	}

	public static void openFile(String filename) throws IOException {
		File file = new File(filename);
		openFile(file);
	}
	
	public static File createFile(String filePath) {
		  File file = new File(filePath);
	      if (!file.exists()) {
	    	  file.getParentFile().mkdirs();
	          try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	      }
	      return file;
	  }


	   public static void copyFile(String source,String aim,boolean repalce) throws Exception{
		   FileOutWriter writer = new FileOutWriter(aim,repalce);
		   File file = new File(source);
		   InputStream input = new FileInputStream(file);
		   writer.write(input);
		   writer.close();
	   }
	   
	   public static void delete(String source) throws Exception{
		   File dir = new File(source);
		   if (dir.isDirectory()) {
	           String[] children = dir.list();
	           //递归删除目录中的子目录下
	           for (int i=0; i<children.length; i++) {
	               delete( source + File.separator + children[i]);
	           }
	       }
	       // 目录此时为空，可以删除
	       dir.delete();
	   }
		
		public static void copy(String src, String aim) throws IOException {
			File filePath = new File(src);
			DataInputStream read;
			DataOutputStream write;
			if (filePath.isDirectory()) {
				File[] list = filePath.listFiles();
				for (int i = 0; i < list.length; i++) {
					String newPath = src + File.separator + list[i].getName();
					String newCopyPath = aim + File.separator+ list[i].getName();
					copy(newPath, newCopyPath);
				}
			} else if (filePath.isFile()) {
				read = new DataInputStream(new BufferedInputStream(new FileInputStream(src)));
				File file = new File(aim);
				file.getParentFile().mkdirs();
				write = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(aim)));
				byte[] buf = new byte[1024 * 512];
				while (read.read(buf) != -1) {
					write.write(buf);
				}
				read.close();
				write.close();
			}
		}
	
	
	public static String readFile(String filePath) throws IOException{
		File file = new File(filePath);
		FileReader reader = new FileReader(file);
		
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[1024];
		while(reader.read(buffer) != -1){
			builder.append(buffer);
		}
		reader.close();
		return builder.toString();
	}
	

	
   //-查找文件----------------------------------------------------------------------------
	public static List<File> findFiles(FileFilter[] filters) throws IOException {
		return findFiles(null, filters);
	}

	public static List<File> findFiles(String basePath, FileFilter[] filters) throws IOException {
        List<File> res = new ArrayList<File>();
		JFileChooser chooser = new JFileChooser(basePath);
		if (filters != null) {
			for (FileFilter filter : filters) {
				chooser.setFileFilter(filter);
			}
		}
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setMultiSelectionEnabled(true);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			FileFilter swfilter = chooser.getFileFilter();
			FileUtils util = new FileUtils();
			FileFilterAdapter filterAdapt = util.new FileFilterAdapter(swfilter);
			File[] files = chooser.getSelectedFiles();
			findFile(files,res,filterAdapt);
		}
		return res;
	}
	
	/**
	 * swing 文件过滤器 到 java io 文件过滤器的 适配
    */
	class FileFilterAdapter implements java.io.FileFilter {

		private javax.swing.filechooser.FileFilter swfilter;

		public FileFilterAdapter(javax.swing.filechooser.FileFilter swfilter) {
			this.swfilter = swfilter;
		}

		@Override
		public boolean accept(File pathname) {
			return this.swfilter.accept(pathname);
		}

	}
	
	
	private static void findFile(File[] files,List<File> res,FileFilterAdapter fliter){
		for(File file : files){
			if(file.isFile()){
				res.add(file);
			}else{
				  File[] chFiles = file.listFiles(fliter);
				  findFile(chFiles,res,fliter);
			}
		}
		
	}
	
	//-end 查找文件-------------------------------------------------------------------------

	
}
