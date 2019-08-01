package idv.const_x.tools.coder.daojia;

import idv.const_x.jdbc.table.meta.Type;

public enum DaojiaSimpleTypeEnum implements Type {

	/**
	 * 主键
	 */
	ID("Long", "BIGINT", 20, true),
	
	/**
	 * 整形数字
	 */
	LONG("Long", "BIGINT", 20, false);
	
	

	DaojiaSimpleTypeEnum(String javaType, String jdbcType, int length,
			boolean premiryKey) {
		this.javaType = javaType;
		this.jdbcType = jdbcType;
		this.length = length;
		this.premiryKey = premiryKey;
	}

	DaojiaSimpleTypeEnum(String javaType, String jdbcType, int length, int scale,
			boolean premiryKey) {
		this.javaType = javaType;
		this.jdbcType = jdbcType;
		this.length = length;
		this.scale = scale;
		this.premiryKey = premiryKey;
	}

	private boolean premiryKey = false;
	private String javaType;
	private String jdbcType;
	private int length = -1;
	private int scale = -1;

	@Override
	public String getJavaType() {
		return javaType;
	}

	@Override
	public String getJdbcType() {
		return jdbcType;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public boolean isSimpleType() {
		return true;
	}

	@Override
	public boolean isPremiryKey() {
		return premiryKey;
	}

	@Override
	public int getscale() {
		return scale;
	}

}
