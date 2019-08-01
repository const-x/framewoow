package idv.const_x.file.search;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * 
 * @since 6.3
 * @version 2014-04-23 10:35:15
 * @author const.x
 */
public class FileLineReader {
	


	private String encoding = "UTF-8";
	
	
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	/**
	 * swing 文件过滤器 到 java io 文件过滤器的 适配
	 * 
	 * @since 6.3
	 * @version 2014-04-23 10:25:28
	 * @author const.x
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

	private IFileReadHandler handler;

	public void findFiles(FileFilter[] filters) throws IOException {
		this.findFiles(null, filters);
	}

	public void findFiles(FileFilter filter) throws IOException {
		this.findFiles(null, filter == null? null : new FileFilter[] { filter });
	}

	
	public void findFiles(String basePath,FileFilter ... filters) throws IOException {

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
			FileFilterAdapter filterAdapt = null;
			if(swfilter != null){
				filterAdapt = new FileFilterAdapter(swfilter);
			}
			File[] files = chooser.getSelectedFiles();
			if(this.handler.startScale(files)){
				for (File file : files) {
					if (!this.findLines(file, filterAdapt)) {
						break;
					}
				}
			}
			this.handler.endScale(files);
		}
	}
	

	private boolean findLines(File file, FileFilterAdapter fliter) throws IOException {
		if (file.isDirectory()) {
			File[] files;
			if(fliter != null){
				files = file.listFiles(fliter);
			}else{
				files = file.listFiles();
			}

			for (File ch : files) {
				if (!this.findLines(ch, fliter)) {
					return false;
				}
			}
		} else {
			return this.findLineInFile(file);
		}
		return true;
	}


	public boolean findLineInFile(File file) {
		int linenum = 0;
		String preLine = null;
		String currLine = null;
		String nextLine = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
			if(this.handler.startScaleAFile(file)){
				while (true) {
					String line = reader.readLine();
					preLine = currLine;
					currLine = nextLine;
					nextLine = line;
					if (currLine == null) {
						continue;
					}
					linenum++;
					if (!this.handler.handleLine(file, preLine, currLine, nextLine, linenum)) {
						break;
					}
					if (line == null) {
						break;
					}
				}
				this.handler.endScaleAFile(file);
			}
		} catch (IOException ex) {
			return this.handler.scaleException(file, ex);
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}


	public void setHandler(IFileReadHandler handler) {
		this.handler = handler;
	}

}
