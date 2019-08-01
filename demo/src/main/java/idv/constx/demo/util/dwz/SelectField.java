package idv.constx.demo.util.dwz;

public class SelectField {
   private String alias;
   
   private String name;
   
   private boolean selected = false;

public SelectField( String alias,String name) {
	this.alias = alias;
	this.name = name;
}
/**
 * 待导出字段
 * @param alias 字段中文名
 * @param name  字段名
 * @param selected 是否默认选中
 */
public SelectField( String alias,String name,boolean selected) {
	this.alias = alias;
	this.name = name;
	this.selected = selected;
}

public String getAlias() {
	return alias;
}

public void setAlias(String alias) {
	this.alias = alias;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public boolean isSelected() {
	return selected;
}

public void setSelected(boolean selected) {
	this.selected = selected;
}
   
   
   
}
