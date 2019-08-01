package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * 必输字段
 * @author const.x
 */
public class Required extends AbsValidateStyles{
  
	
	
	@Override
	public String getStyle() {
		return "required";
	}
   
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "required:" +message;
		}
		return null;
	}
}
