package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * equalTo:"#field"          输入值必须和#field相同
 * @author const.x
 */
public class EqualTo extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "equalTo:"+field;
	}

	private String field = "";

	public EqualTo(String field){
		this.field = "'#"+field+"'";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "equalTo:" +message;
		}
		return null;
	}
	
}
