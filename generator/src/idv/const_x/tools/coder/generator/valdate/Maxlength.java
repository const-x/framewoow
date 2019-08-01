package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * maxlength:5               输入长度最多是5的字符串(汉字算一个字符)
 * @author const.x
 */
public class Maxlength extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "maxlength["+maxlength+"]";
	}

	private int maxlength = 0;

	public Maxlength(int i){
		this.maxlength = i;
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "maxlength:" +message;
		}
		return null;
	}

}
