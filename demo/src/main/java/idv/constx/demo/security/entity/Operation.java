package idv.constx.demo.security.entity;


/**
 * 模块下的业务操作
 * 
 * 
 * 
 * @author const.x
 */

public class Operation extends Module {

	
	private static final long serialVersionUID = 9067475765668915674L;

	/**
	 *
	 * @author const.x
	 * @createDate 2014年8月20日
	 */
	@Override
	public void setType(Integer type) {
		super.setType(ModuleTypeEnum.OPERATION.getValue());
	}

	
}
