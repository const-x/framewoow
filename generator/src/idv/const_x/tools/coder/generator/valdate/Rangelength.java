package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * rangelength:[5,10] 输入长度必须介于 5 和 10 之间的字符串")(汉字算一个字符)
 * 
 * @author const.x
 */
public class Rangelength extends AbsValidateStyles {

	@Override
	public String getStyle() {
		return "length["+min + "," +max + "]";
	}

	private int min = 0;
	private int max = 0;

	public Rangelength(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "rangelength:" +message;
		}
		return null;
	}

}
