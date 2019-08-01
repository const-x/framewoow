package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * digits:true                    必须输入整数
 * @author const.x
 */
public class Digits extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "digits";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "digits:" +message;
		}
		return null;
	}

}
