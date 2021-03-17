package idv.const_x.jdbc.table.meta;

public class EnumType  implements Type {
	
	
	private String[] enumNames;
	private String[] alias;
	private String[] values;
	
	private String className;
	
	public EnumType(String[] enumNames,String[] alias,String[] values) throws Exception{
		if(enumNames.length != alias.length || enumNames.length != values.length ||  alias.length != values.length ){
			throw new Exception("枚举名称，显示名称，值的长度必须一致");
		}
		this.enumNames = enumNames;
		this.values = values;
		this.alias = alias;
	}
	

	public String getClassName() {
		return className;
	}

	/**
	 * 如果指定了枚举值对应的类名 则沿用指定的类，否则 在本模块下自动创建相关的枚举类
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	public String[] getEnumNames() {
		return enumNames;
	}


	public String[] getAlias() {
		return alias;
	}


	public String[] getValues() {
		return values;
	}

	@Override
	public boolean isPremiryKey() {
		return false;
	}

	@Override
	public String getJavaType() {
		return "Integer";
	}

	@Override
	public String getJdbcType() {
		return "TINYINT";
	}

	@Override
	public int getLength() {
		return 4;
	}

	@Override
	public int getscale() {
		return -1;
	}

	@Override
	public boolean isSimpleType() {
		return true;
	}


}
