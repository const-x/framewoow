package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * mobile: "手机"
 * @author const.x
 */
public class Mobile extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "mobile";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "mobile:" +message;
		}
		return null;
	}

}
