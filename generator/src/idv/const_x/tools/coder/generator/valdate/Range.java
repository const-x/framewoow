package idv.const_x.tools.coder.generator.valdate;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * range:[5,10]   输入值必须介于 5 和 10 之间
 * @author const.x
 */
public class Range extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "range["+min + "," +max + "]";
	}

	private String max = "";
	private String min = "";

	public Range(double min,double max){
		BigDecimal de = new  BigDecimal(min);
		this.min = de.toString();
		de = new  BigDecimal(max);
		this.max = de.toString();
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "range:" +message;
		}
		return null;
	}

}
