package idv.constx.demo.base.entity;


public enum AuditStatusEnum implements IEnum{

	

	/** 0:收回（动作） 、 待提交（状态）*/
	CONFIRMING(0, "待提交"),
	/** 10:提交（动作） 、待审批（状态）*/
	PENDING(10, "待审批"),
	/** 20:审批（动作） 、已通过（状态） */
	PASS(20, "已通过"),
	/** 30:驳回（动作） 、已驳回（状态）*/
	UN_PASS(30, "已驳回");
	
	private int value;
	private String name;

	/**
	 * @return the value
	 * 
	 * @author const.x
	 * @createDate 2014年8月25日
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the name
	 * 
	 * @author const.x
	 * @createDate 2014年8月25日
	 */
	public String getName() {
		return name;
	}

	AuditStatusEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static AuditStatusEnum getAuditStatusByInt(int value) {
		AuditStatusEnum[] values = AuditStatusEnum.values();
		for(AuditStatusEnum enumValue : values){
			if(enumValue.getValue() == value){
				return enumValue;
			}
		}
		return null;
	}

}
