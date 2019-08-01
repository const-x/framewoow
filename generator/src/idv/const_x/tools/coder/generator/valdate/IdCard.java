package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * creditcard:                   必须输入合法的身份证号
 * @author const.x
 */
public class IdCard extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "idcard";
	}

	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "idcard:" +message;
		}
		return null;
	}
}
