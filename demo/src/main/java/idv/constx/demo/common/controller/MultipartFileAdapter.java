package idv.constx.demo.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.fileupload.FileItem;

public class MultipartFileAdapter implements MultipartFile {
	
	private FileItem item;
	
    public MultipartFileAdapter(FileItem item){
    	this.item = item;
    }
    
	@Override
	public String getName() {
		return item.getName();
	}

	@Override
	public String getOriginalFilename() {
		return "";
	}

	@Override
	public String getContentType() {
		return item.getContentType();
	}

	@Override
	public boolean isEmpty() {
		return item == null;
	}

	@Override
	public long getSize() {
		return item.getSize();
	}

	@Override
	public byte[] getBytes() throws IOException {
		return item.get();
	}

	
	
	@Override
	public InputStream getInputStream() throws IOException {
		return item.getInputStream();
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
			try {
				item.write(dest);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
