package idv.constx.demo.security.entity;

import idv.constx.demo.base.entity.IEnum;


public enum UserTypeEnum implements IEnum {
   

	/** 1:系统级*/
	SYSTEM(1, "系统级"),
	/** 2:商户级 */
	MERCHANT(2, "商户级"),
	/** 3:门店级*/
	STORES(3, "门店级");

	private int value;
	private String name;


	public int getValue() {
		return value;
	}


	public String getName() {
		return name;
	}

	UserTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static UserTypeEnum getEnumByInt(int value){
		switch(value){
			case 1 : return UserTypeEnum.SYSTEM;
			case 2 : return UserTypeEnum.MERCHANT;
			case 3 : return UserTypeEnum.STORES;
			default: return null;
		}
		
	}
}
