package idv.const_x.tools.coder.generator.valdate;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * min:10                       输入值不能小于10
 * @author const.x
 */
public class Min extends AbsValidateStyles{

	@Override
	public String getStyle() {
		return "min["+min+"]";
	}

	private String min = "";

	public Min(double value){
		BigDecimal de = new  BigDecimal(value);
		this.min = de.toString();
	}
	
	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "min:" +message;
		}
		return null;
	}

}
