package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * lettersonly: "必须是字母"
 * @author const.x
 */
public class LettersOnly extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "lettersonly";
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "lettersonly:" +message;
		}
		return null;
	}
}
