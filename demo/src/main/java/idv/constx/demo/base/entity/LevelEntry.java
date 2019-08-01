package idv.constx.demo.base.entity;

/**
 * 等级数据
 * 
 *
 * @author const.x
 */
public abstract class LevelEntry  extends IDEntity{
	
	private static final long serialVersionUID = 1L;
	
	// 数据等级
	private Integer level = null;
	
	/**
	 * 获取数据等级
	 * 
	 * @return Integer 数据等级
	 */
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * 设置数据等级
	 * 
	 * @param value 数据等级
	 */
	public void setLevel(Integer value) {
		this.setAttrbute(FIELD_LEVEL, value);
	}

	/**  字段名:数据等级 */
	public static final String FIELD_LEVEL= "level";
}
