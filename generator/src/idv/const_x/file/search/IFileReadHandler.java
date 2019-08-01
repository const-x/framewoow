package idv.const_x.file.search;

import java.io.File;

public interface IFileReadHandler {

	void endScale(File[] file);

	void endScaleAFile(File fi);

	/**
	 * @param preLine 上一行内容
	 * @param currLine 当期行内容
	 * @param nextLine 下一行内容
	 * @return boolean
	 */
	boolean handleLine(File file, String preLine, String currLine, String nextLine, int lineNum);

	/**
	 * 
	 * @param file
	 * @param e
	 * @return 是否继续
	 */
	boolean scaleException(File file, Throwable e);

	/**
	 * 
	 * @param file
	 * @return 是否继续
	 */
	boolean startScale(File[] file);
    
	/**
	 * 
	 * @param file
	 * @return 是否继续
	 */
	boolean startScaleAFile(File file);
}
