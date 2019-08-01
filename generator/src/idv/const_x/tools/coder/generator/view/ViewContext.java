package idv.const_x.tools.coder.generator.view;

import idv.const_x.tools.coder.generator.Context;

import java.util.HashMap;
import java.util.Map;

public class ViewContext {

	private String tempBase;

	private String saveBase;

	private Context context;

	private Map<String, String> repalce = new HashMap<String, String>();

	public ViewContext(Context context) {
		this.context = context;
	}

	public Context getBaseContext() {
		return context;
	}

	public Map<String, String> getRepalce() {
		return repalce;
	}

	public void setRepalce(String property, String value) {
		this.repalce.put(property, value);
	}

	public String getTempBase() {
		return tempBase;
	}

	public void setTempBase(String tempBase) {
		this.tempBase = tempBase;
	}

	public String getSaveBase() {
		return saveBase;
	}

	public void setSaveBase(String saveBase) {
		this.saveBase = saveBase;
	}

}
