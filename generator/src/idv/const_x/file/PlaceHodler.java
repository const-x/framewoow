package idv.const_x.file;

import idv.const_x.file.search.FileLineReader;
import idv.const_x.file.search.IFileReadHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * 注入参数替换工具
 * 
 * @author const.x
 */
public class PlaceHodler {
	
	
	public static void main(String[] args){
		PlaceHodler hodler = new PlaceHodler();
		String templateFile = "f://template.java";
		String saveToFile = "f://aim.java";
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("repalce", "repalcesuccessful");
		properties.put("repalce2", "repalcesuccessful");
		hodler.hodle(templateFile, saveToFile, properties);
	}

	FileOutWriter wrtier;
	Map<String, String> properties;
	
	private String profix = "@{";
	private String endfix = "}";
	
	
	public void hodle(String templateFile, String saveToFile, Map<String, String> properties) {
		File src = new File(templateFile);
		wrtier = new FileOutWriter(saveToFile, true);
		this.properties = properties;
		FileLineReader reader = new FileLineReader();
		reader.setHandler(new IFileReadHandler() {
			@Override
			public void endScale(File[] file) {
			}

			@Override
			public void endScaleAFile(File fi) {
				PlaceHodler.this.wrtier.close();
			}

			@Override
			public boolean handleLine(File file, String lastLine, String currLine, String nextLine, int lineNum) {
				for (Entry<String, String> entry : PlaceHodler.this.properties.entrySet()) {
					if(currLine != null && !currLine.equals("")){
						String property = profix + entry.getKey() + endfix;
						String value = entry.getValue();
						if(value == null){
							value = "";
						}
						currLine=currLine.replace(property, value);
					}
				}
				wrtier.write(currLine);
				wrtier.write("\n");
				return true;
			}

			@Override
			public boolean scaleException(File file, Throwable e) {
				e.printStackTrace();
				return false;
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
		reader.findLineInFile(src);
	}
	

	public void hodle(String templateFile, File saveToFile, Map<String, String> properties) {
		File src = new File(templateFile);
		wrtier = new FileOutWriter(saveToFile, true);
		this.properties = properties;
		FileLineReader reader = new FileLineReader();
		reader.setHandler(new IFileReadHandler() {
			@Override
			public void endScale(File[] file) {
			}

			@Override
			public void endScaleAFile(File fi) {
				PlaceHodler.this.wrtier.close();
			}

			@Override
			public boolean handleLine(File file, String lastLine, String currLine, String nextLine, int lineNum) {
				for (Entry<String, String> entry : PlaceHodler.this.properties.entrySet()) {
					if(currLine != null && !currLine.equals("")){
						String property = "@{" + entry.getKey() + "}";
						String value = entry.getValue();
						if(value == null){
							value = "";
						}
						currLine=currLine.replace(property, value);
					}
				}
				wrtier.write(currLine);
				wrtier.write("\n");
				return true;
			}

			@Override
			public boolean scaleException(File file, Throwable e) {
				e.printStackTrace();
				return false;
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
		reader.findLineInFile(src);
	}
	
	private StringBuilder buidler;
	
	public String hodle(String templateFile,  Map<String, String> properties) {
		File src = new File(templateFile);
		buidler = new StringBuilder();
		this.properties = properties;
		FileLineReader reader = new FileLineReader();
		reader.setHandler(new IFileReadHandler() {
			@Override
			public void endScale(File[] file) {
			}

			@Override
			public void endScaleAFile(File fi) {
			}

			@Override
			public boolean handleLine(File file, String lastLine, String currLine, String nextLine, int lineNum) {
				for (Entry<String, String> entry : PlaceHodler.this.properties.entrySet()) {
					if(currLine != null && !currLine.equals("")){
						String property = "@{" + entry.getKey() + "}";
						String value = entry.getValue();
						if(value == null){
							value = "";
						}
						currLine=currLine.replace(property, value);
					}
				}
				buidler.append(currLine);
				buidler.append("\n");
				return true;
			}

			@Override
			public boolean scaleException(File file, Throwable e) {
				e.printStackTrace();
				return false;
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
		reader.findLineInFile(src);
		return buidler.toString();
		
	}

}
