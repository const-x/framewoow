package idv.const_x.tools.coder.generator;

import java.io.File;
import java.util.List;

public interface IGenerator {
  
	public List<File> generator(Context context) throws Exception;
	
	public void clear(Context context) throws Exception;
}
