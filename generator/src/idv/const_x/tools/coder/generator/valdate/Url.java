package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * url:true                        必须输入正确格式的网址
 * @author const.x
 */
public class Url extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "url";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "url:" +message;
		}
		return null;
	}

}
