package idv.const_x.jdbc.table.meta;

public class ComplexType implements Type {
	
	public static String getRelationshipName(int i){
		switch(i){
		case 1: return "ONE_TO_ONE";
		case 2: return "ONE_TO_MANY";
		case 3: return "MANY_TO_MANY";
		default:return null;
		}
	}
	
	/**一对一关系*/
	public static final int ONE_TO_ONE = 1;
	/**一对多关系*/
	public static final int ONE_TO_MANY = 2;
	/**多对多关系*/
	public static final int MANY_TO_MANY = 3;
	
	private boolean nullable = true;
	private int relationship = ONE_TO_ONE;
	private String ref  ="";
	private String javaType = "";
	private String defaultValue=null;
	private String refTable="";
	private String refModule="";
	private String refNameField  ="";
	private String refIDField  ="";
	private String refNameColumn  ="";
	private String refIDColumn  ="";

	
	
	
	public ComplexType(String refclassName) {
		this.ref = refclassName.trim();
		String[] packs = ref.split("\\.");
		this.javaType = packs[packs.length - 1];
		this.refModule = packs[packs.length - 3];
		this.refTable = (refModule + "_" + javaType).toLowerCase();
		this.refNameField = "name";
		this.refIDField = "id";
		this.refNameColumn = "NAME";
		this.refIDColumn = "ID";
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	


	public String getRefModule() {
		return refModule;
	}


	@Override
	public boolean isPremiryKey() {
		return false;
	}

	@Override
	public String getJavaType() {
		return javaType;
	}

	@Override
	public String getJdbcType() {
		return "DECIMAL";
	}

	@Override
	public int getLength() {
		return 20;
	}

	@Override
	public boolean isSimpleType() {
		return false;
	}

	public String getRefClass() {
		return ref;
	}

	@Override
	public int getscale() {
		return -1;
	}

	public String getRefTable() {
		return refTable;
	}


	public String getRefNameField() {
		return refNameField;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public void setRefNameField(String refNameField) {
		this.refNameField = refNameField;
	}

	public String getRefIDField() {
		return refIDField;
	}

	public void setRefIDField(String refIDField) {
		this.refIDField = refIDField;
	}

	public int getRelationship() {
		return relationship;
	}

	public void setRelationship(int relationship) {
		this.relationship = relationship;
	}

	public String getRefNameColumn() {
		return refNameColumn;
	}

	public void setRefNameColumn(String refNameColumn) {
		this.refNameColumn = refNameColumn.toUpperCase();
	}

	public String getRefIDColumn() {
		return refIDColumn;
	}

	public void setRefIDColumn(String refIDColumn) {
		this.refIDColumn = refIDColumn.toUpperCase();
	}

	
	
	

}
