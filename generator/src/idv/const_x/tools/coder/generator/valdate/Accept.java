package idv.const_x.tools.coder.generator.valdate;

import org.apache.commons.lang3.StringUtils;

/**
 * accept:                       输入拥有合法后缀名的字符串（上传文件的后缀）
 * @author const.x
 */
public class Accept extends AbsValidateStyles{
	

	@Override
	public String getStyle() {
		return "accept["+accept+"]";
	}
	
	private String accept = "";

	public Accept(String accept){
		this.accept =  accept;
	}

	@Override
	public String getMessage() {
		if(StringUtils.isNotBlank(message)){
			return "accept:" + message;
		}
		return null;
	}

	
}
