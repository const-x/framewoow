package idv.const_x.file.search;


import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class FileLineSearchHandler implements IFileReadHandler {

	protected String content;

	protected Boolean searchInMutlLine = false;

	private int filecounter = 0;
	private int filefounded = 0;
	private int totalfounded = 0;

	/**
	 * @param searchInMutlLine the searchInMutlLine to set
	 * 
	 * @author const.x
	 * @createDate 2014年8月18日
	 */
	public void setSearchInMutlLine(Boolean searchInMutlLine) {
		this.searchInMutlLine = searchInMutlLine;
	}

	

	private boolean hasFoundinCurFile = false;

	private boolean isFileNamePrinted = false;
	
	private boolean preLinePrinted = false;

	@Override
	public void endScale(File[] file) {
		System.out.println("共扫描" + this.filecounter + "个文件 ,找到" + this.filefounded + "个文件共" +totalfounded+ "处包含查找项的内容");
	}

	@Override
	public void endScaleAFile(File file) {
		if (this.hasFoundinCurFile) {
			System.out.println();
			this.filefounded++;
		}
		this.hasFoundinCurFile = false;
		this.isFileNamePrinted = false;
	}

	private String getWappedLineNum(int lineNum) {
		String line = String.valueOf(lineNum);
		int i = 5 - line.length();
		StringBuilder sb = new StringBuilder(line);
		for (int j = 0; j < i; j++) {
			sb.append(" ");
		}
		sb.append(":  ");
		return sb.toString();
	}

	@Override
	public boolean handleLine(File file, String preLine, String currLine, String nextLine, int lineNum) {
	    if(this.searchLine(file, preLine, currLine, nextLine, lineNum)){
	    	totalfounded++;
	    }
		return true;
	}
	
	protected boolean searchLine(File file, String preLine, String currLine, String nextLine, int lineNum){
		boolean foundedInLine = this.searchInSignLine(preLine, currLine, nextLine);
		boolean foundedInMutLIne = false;
		if(this.searchInMutlLine){
			foundedInMutLIne = this.searchInMutlLine(preLine, currLine, nextLine);
		}
		
		if (foundedInLine || foundedInMutLIne) {
			this.hasFoundinCurFile = true;
			if (!this.isFileNamePrinted) {
				System.out.println(file);
				this.isFileNamePrinted = true;
			}
		}else{
			preLinePrinted = false;
		}
		
		if(foundedInMutLIne){
			if(!preLinePrinted){
				System.out.println(this.getWappedLineNum(lineNum - 1) + preLine);
			}
			System.out.println(this.getWappedLineNum(lineNum) + currLine);
			preLinePrinted = true;
		}else if(foundedInLine){
			System.out.println(this.getWappedLineNum(lineNum) +  currLine);
			preLinePrinted = true;
		}

		return foundedInLine || foundedInMutLIne;
	}

	@Override
	public boolean scaleException(File file, Throwable e) {
		System.out.println(e.getMessage());
		return true;
	}

	private boolean searchInMutlLine(String preLine, String currLine, String nextLine) {
		if (StringUtils.isNotBlank(preLine) &&  StringUtils.isNotBlank(currLine)) {
			preLine = preLine.length() > content.length() - 1 ? preLine.substring(preLine.length() - content.length() + 1):preLine;
			currLine = currLine.length() > content.length() - 1 ? currLine.substring(0,content.length() - 1):currLine;
			String line = preLine + currLine;
			if (line.contains(this.content)) {
				return true;
			}
		}
		return false;
	}

	private boolean searchInSignLine(String preLine, String currLine, String nextLine) {
		if (currLine.contains(this.content)) {
			return true;
		}
		return false;
	}

	public void setContent(String content) {
		this.content = content;
	}



	@Override
	public boolean startScale(File[] file) {
		System.out.println("开始扫描......");
		System.out.println();
		return true;
	}

	@Override
	public boolean startScaleAFile(File file) {
		this.filecounter++;
		return true;
	}

}
