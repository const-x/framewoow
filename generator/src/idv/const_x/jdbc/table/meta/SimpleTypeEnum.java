package idv.const_x.jdbc.table.meta;

public enum SimpleTypeEnum implements Type {

	/**
	 * 主键
	 */
	ID("Long", "DECIMAL", 19, true),
	
	/**
	 * 字符
	 */
	STRING("String", "VARCHAR", 16, false),

	/**
	 * 字符
	 */
	STRING_32("String", "VARCHAR", 32, false),

	/**
	 * 字符
	 */
	STRING_64("String", "VARCHAR", 64, false),

	/**
	 * 字符
	 */
	STRING_128("String", "VARCHAR", 128, false),

	/**
	 * 长字符
	 */
	STRING_256("String", "VARCHAR", 256, false),

	/**
	 * 整形数字
	 */
	INTEGER("Integer", "DECIMAL", 10, false),

	/**
	 * 整形数字
	 */
	LONG("Long", "DECIMAL", 19, false),

	/**
	 * 浮点数字
	 */
	FLOAT("Float", "DECIMAL", 12, 2, false),

	/**
	 * 浮点数字
	 */
	DOUBLE("Double", "DECIMAL", 22, 2, false),

	/**
	 * 日期
	 */
	DATE("String", "CHAR", 10, false),

	/**
	 * 日期（带时间）
	 */
	DATETIME("String", "CHAR", 19, false),

	/**
	 * 时间
	 */
	TIME("String", "CHAR", 10, false),

	/**
	 * 时间戳
	 */
	TIMESTAMP("Long", "TIMESTAMP", 19, false),

	/**
	 * 布尔型
	 */
	BOOLEAN("Integer", "DECIMAL", 1, false),

	/**
	 * 图片（与ImageField配套使用）
	 */
	IMAGE("String", "VARCHAR", 64, false),

	/**
	 * 富文本
	 */
	RICHTEXT("String", "VARCHAR", 1024, false),

	/**
	 * 电话
	 */
	PHONE("String", "VARCHAR", 15, false),
	
	/**
	 * 手机
	 */
	MOBILE("String", "VARCHAR", 11, false),

	/**
	 * 身份证
	 */
	IDCARD("String", "VARCHAR", 11, false),

	/**
	 * 邮箱
	 */
	EMAIL("String", "VARCHAR", 32, false);

	SimpleTypeEnum(String javaType, String jdbcType, int length,
			boolean premiryKey) {
		this.javaType = javaType;
		this.jdbcType = jdbcType;
		this.length = length;
		this.premiryKey = premiryKey;
	}

	SimpleTypeEnum(String javaType, String jdbcType, int length, int scale,
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
