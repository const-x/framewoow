package idv.constx.demo.security.entity;

import idv.constx.demo.base.entity.IEnum;

/**
 * 模块类型枚举
 * 
 * @author const.x
 */
public enum ModuleTypeEnum implements IEnum {
	/**
	 * 根节点
	 */
	ROOT("根节点", 0),

	/**
	 * 业务操作
	 */
	OPERATION("业务操作", 2),
	/**
	 * 业务模块
	 */
	BUSINESS("业务模块", 1);

	private int value = 0;

	private String name = "";

	ModuleTypeEnum(String name, int value) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}


	public static ModuleTypeEnum getTypeByInt(Integer type) {
		ModuleTypeEnum[] values = ModuleTypeEnum.values();
		for (ModuleTypeEnum enumValue : values) {
			if (enumValue.getValue() == type) {
				return enumValue;
			}
		}
		return null;
	}

}
