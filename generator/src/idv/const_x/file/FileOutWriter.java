package idv.const_x.file;

import idv.const_x.utils.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.CharBuffer;


/**
 * 快速生成文件
 * 
 * @since 6.3
 * @version 2014-03-05 14:26:55
 * @author const.x
 */
public class FileOutWriter {

	private File file;

	private String filepath;
	
	private String encoding = "UTF-8";

	private Writer out;
	
	private boolean replace;

	
	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	/**
	 * @param fileName 文件路径
	 * @param replace 是否覆盖原文件内容 否则追加到文件尾
	 */
	public FileOutWriter(String fileName, boolean replace) {
		this.filepath = fileName;
		this.file = FileUtils.createFile(this.filepath);
		this.replace = replace;
	}
	

	/**
	 * @param fileName 文件路径
	 * @param replace 是否覆盖原文件内容 否则追加到文件尾
	 */
	public FileOutWriter(File file, boolean replace) {
		this.filepath = file.getPath();
		this.file = file;
		this.replace = replace;
	}


	public void write(String str) {
		if (this.out == null) {
			this.file.setWritable(true);
			try {
				this.out  = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(this.file, !this.replace),encoding));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		try {
			this.out.write(str);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void write(InputStream input) {
		this.write(input, encoding);  
	}

	public void write(InputStream input,String encoding) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(input,encoding)); 
			String str; 
			while((str=br.readLine())!=null){ 
			   this.write(str); 
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public void write(Readable input) {
		try {
			CharBuffer buffer = CharBuffer.allocate(1024);
			while(input.read(buffer) != -1){
				buffer.flip();
				this.write(buffer.toString());
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public void writeLine(String str) {
		if(!str.endsWith("\r\n") && !str.endsWith("\n\r")){
			str += "\r\n";			
		}
		this.write(str);
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.close();
	}

	public void close() {
		try {
			if (this.out != null) {
				this.out.flush();
				this.out.close();
				this.out = null;
				this.file = null;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
