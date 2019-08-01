package idv.const_x.tools.coder.generator.valdate;
/**
 * jquery验证
 * @author const.x
 *
 */
public abstract class AbsValidateStyles {
  
	public abstract String getStyle();
	
	public abstract String getMessage();
	
	protected String message;
	
	/**
	 * 设置错误提示信息 可用{0},{1}..代替输入参数
	 * @param message
	 */
	public void setMessage(String message){
		this.message = "'"+message+"'";
	}

	
	
	
	
}
