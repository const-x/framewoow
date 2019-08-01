package idv.const_x.jdbc.table.meta;

public class SimpleType  implements Type {
	private boolean premiryKey = false;
	private String javaType;
	private String jdbcType;
	private int length = 32;
	private int scale = -1;

	
	public static SimpleType clone(Type type){
		SimpleType clone = new SimpleType();
		clone.setJavaType(type.getJavaType());
		clone.setJdbcType(type.getJdbcType());
		clone.setLength(type.getLength());
		clone.setPremiryKey(type.isPremiryKey());
		clone.setScale(type.getscale());
		return clone;
	}
	


	public void setPremiryKey(boolean premiryKey) {
		this.premiryKey = premiryKey;
	}

	
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	
	public void setLength(int length) {
		this.length = length;
	}


	public void setScale(int scale) {
		this.scale = scale;
	}



	@Override
	public boolean isPremiryKey() {
		return premiryKey;
	}

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
	public int getscale() {
		return scale;
	}

	@Override
	public boolean isSimpleType() {
		return true;
	}


}
