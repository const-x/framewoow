package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * number:true                 必须输入合法的数字(负数，小数)
 * @author const.x
 */
public class Number extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "number";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "number:" +message;
		}
		return null;
	}

}
