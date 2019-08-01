package idv.constx.demo.common;

import java.math.BigDecimal;

/**
 * 通用工具类方法
 * @author bbgcs001
 *
 */
public class CommonUtil {

	
	
	/**
	 * 精度设置
	 * @param value
	 * @param scale
	 * @param roundingMode  
	 * @return
	 */
	public static double round(double value, int scale, int roundingMode)
	{
		
		BigDecimal d = new BigDecimal(value);
		d = d.setScale(scale, roundingMode);
		double v = d.doubleValue();
		d=null;
		
		return v;
		
	}
	
	
	
	
}
