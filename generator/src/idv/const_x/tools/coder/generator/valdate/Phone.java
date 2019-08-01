package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * phone: "数字、空格、括号"
 * @author const.x
 */
public class Phone extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "phone";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "phone:" +message;
		}
		return null;
	}

}
