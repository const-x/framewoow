package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.view.ViewContext;


public abstract class AbsFieldGenerator  implements IFieldGenerator{

	protected String createFieldElements = "";
	protected String createExpandScripts = "";
	protected String createValdiatesBeforeSubmit = "";
	
	protected String updateFieldElements = "";
	protected String updateExpandScripts = "";
	protected String updateValdiatesBeforeSubmit = "";
	
	static String REQUERIED_STAR = "<td><span class='starL'></span></td>";
	
	
	protected String getProfix(ViewContext context,Field field){
		String profix = field.isNullable() ? "":REQUERIED_STAR;
		return profix;
	}
	

	@Override
	public String getCreateFieldElements() {
		return createFieldElements;
	}


	@Override
	public String getCreateExpandScripts() {
		return createExpandScripts;
	}

	@Override
	public String getCreateValdiatesBeforeSubmit() {
		return createValdiatesBeforeSubmit;
	}

	@Override
	public String getUpdateFieldElements() {
		return updateFieldElements;
	}


	@Override
	public String getUpdateExpandScripts() {
		return updateExpandScripts;
	}

	@Override
	public String getUpdateValdiatesBeforeSubmit() {
		return updateValdiatesBeforeSubmit;
	}

}
