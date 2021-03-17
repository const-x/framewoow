package idv.const_x.tools.coder.generator.java;

import java.util.HashMap;
import java.util.Map;

import idv.const_x.tools.coder.generator.Context;
import idv.const_x.jdbc.table.meta.Field;

public class JavaContext {
	
	private Context context;

    //是否为层级结构
	private boolean isTree = false;
	
	public JavaContext(Context context){
		this.context = context;
		this.isTree = context.isTree();
	}


	public Context getBaseContext() {
		return context;
	}



	/**
	 * @return the isTree
	 *
	 * @author const.x
	 * @createDate 2014年8月27日
	 */
	public boolean isTree() {
		return isTree;
	}


	public Map<String, String> getRepalce() {
		return context.getRepalce();
	}

	public void setRepalce(String property,String value) {
		this.context.getRepalce().put(property, value);
	}

}
