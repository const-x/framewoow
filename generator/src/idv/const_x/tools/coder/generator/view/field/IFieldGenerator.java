package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.view.ViewContext;

public interface IFieldGenerator {

	
	void init(ViewContext context,Field field);
	
	String getCreateFieldElements();
	
	String getCreateExpandScripts();
	
	String getCreateValdiatesBeforeSubmit();
	
	
	
    String getUpdateFieldElements();
	 
	String getUpdateExpandScripts();
	
	String getUpdateValdiatesBeforeSubmit();
	
	
}
