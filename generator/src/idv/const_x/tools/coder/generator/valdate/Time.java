package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * email:true                    必须输入正确格式的电子邮件
 * @author const.x
 */
public class Time extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "time";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "time:" +message;
		}
		return null;
	}

}
