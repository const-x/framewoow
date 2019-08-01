package idv.const_x.tools.coder.generator.valdate;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * max:5                        输入值不能大于5
 * @author const.x
 */
public class Max extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "max["+max+"]";
	}

	private String max = "";

	public Max(double value){
		BigDecimal de = new  BigDecimal(value);
		this.max = de.toString();
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "max:" +message;
		}
		return null;
	}

}
