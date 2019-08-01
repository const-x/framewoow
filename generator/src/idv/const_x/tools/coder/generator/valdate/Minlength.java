package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * minlength:5               输入长度最少是5的字符串(汉字算一个字符)
 * @author const.x
 */
public class Minlength extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "minlength["+minlength+"]";
	}

	private int minlength = 0;

	public Minlength(int value){
		this.minlength = value;
	}

	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "minlength:" +message;
		}
		return null;
	}
}
