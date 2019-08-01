package idv.const_x.file.search;


import java.io.File;
import java.io.FileOutputStream;

public class FileLineRepalceHandler extends FileLineSearchHandler {

	private StringBuilder fileContent;

	private String replace;

	private boolean foundedInCurFile = false;

	public void setReplace(String replace) {
		this.replace = replace;
	}

	/**
	 * @see idv.const_x.file.search.FileLineSearchHandler#endScale(java.io.File[])
	 *
	 * @author const.x
	 * @createDate 2014年8月18日
	 */
	@Override
	public void endScale(File[] file) {
		super.endScale(file);
	}

	@Override
	public boolean handleLine(File file, String preLine, String currLine, String nextLine, int lineNum) {
		boolean founded = super.searchLine(file, preLine, currLine, nextLine, lineNum);
		if (founded) {
			this.foundedInCurFile = true;
			if(this.searchInMutlLine){
				
			}else{
				String cur = currLine.replace(this.content, this.replace);
				this.fileContent.append(cur);
				this.fileContent.append("\r\n");
			}
		}else{
			this.fileContent.append(currLine);
			this.fileContent.append("\r\n");
		}
		return true;
	}

	@Override
	public void endScaleAFile(File file) {
		if (foundedInCurFile) {
			try {
				FileOutputStream fs = new FileOutputStream(file);
				fs.write(fileContent.toString().getBytes());
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		super.endScaleAFile(file);
	}

	@Override
	public boolean startScaleAFile(File file) {
		this.foundedInCurFile = false;
		this.fileContent = new StringBuilder();
		return super.startScaleAFile(file);
	}

}
