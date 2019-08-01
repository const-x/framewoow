package idv.constx.demo.common;

import java.math.BigDecimal;

public class CaculateUtil {
	 
	
	public static Double add(Integer scale,Number ... a){
		BigDecimal b = new BigDecimal(0);
		for (Number number : a) {
			if(number != null){
				b.add(new BigDecimal(number.doubleValue()));
			}
		}
		return roundHalfUp(b,scale);
	}
	

    public static  Double muti(Integer scale,Number ... a){
    	BigDecimal b = new BigDecimal(1);
		for (Number number : a) {
			if(number != null){
				b.multiply(new BigDecimal(number.doubleValue()));
			}else{
				return roundHalfUp(0.0,scale);
			}
		}
		return roundHalfUp(b,scale);
	}
    
    
    public static Double roundHalfUp(BigDecimal value, Integer scale) {
		if(value == null){
			return null;
		}
		if(scale == null){
			scale = 2;
		}
		double f1 = value.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

    
	public static Double roundHalfUp(Double value, Integer scale) {
		if(value == null){
			return null;
		}
		String d = value + "";
		if(scale == null){
			scale = 2;
		}
		if(d.length()-d.indexOf(".")-1 > scale){
			BigDecimal b = new BigDecimal(value);
			return roundHalfUp(b,scale);
		}
		return  value;
	}
	

	
   
}
