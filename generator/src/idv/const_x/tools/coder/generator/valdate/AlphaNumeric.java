package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * alphanumeric: "字母、数字、下划线"
 * @author const.x
 */
public class AlphaNumeric extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "alphanumeric";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "alphanumeric:" +message;
		}
		return null;
	}

}
