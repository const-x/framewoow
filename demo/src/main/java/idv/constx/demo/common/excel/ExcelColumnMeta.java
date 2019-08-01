package idv.constx.demo.common.excel;
/**
 * 
 * 
 *
 * @author const.x
 */
public class ExcelColumnMeta {
	
	private String field;
	
	private String columnName;
	
	private Integer index;
	
	private IColumn column;
	
	private AbsCellRenderer renderer;
	
	/**
	 * 
	 * @param field      源字段名
	 * @param columnName excel列名
	 *
	 * @author const.x
	 * @createDate 2015年6月3日
	 */
	public  ExcelColumnMeta(String field,String columnName){
		this.field = field;
		this.columnName = columnName;
	}
	
	
	/**
	 * 
	 * 
	 * @param column 自定义字段
	  * @param columnName excel列名
	 *
	 * @author const.x
	 * @createDate 2015年6月3日
	 */
	public  ExcelColumnMeta(String columnName,IColumn column){
		this.column = column;
		this.columnName = columnName;
	}
	
	

	/**
	 * @param index excel列索引
	 *
	 * @author const.x
	 * @createDate 2015年6月3日
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}


	/**
	 * @return the field
	 *
	 * @author const.x
	 * @createDate 2015年6月3日
	 */
	public String getField() {
		return field;
	}

	/**
	 * @return the columnName
	 *
	 * @author const.x
	 * @createDate 2015年6月3日
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @return the index
	 *
	 * @author const.x
	 * @createDate 2015年6月3日
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @return the column
	 *
	 * @author const.x
	 * @createDate 2015年6月3日
	 */
	public IColumn getColumn() {
		return column;
	}

	/**
	 * @return the renderer
	 *
	 * @author const.x
	 * @createDate 2015年6月3日
	 */
	public AbsCellRenderer getRenderer() {
		return renderer;
	}


	/**
	 * @param 单元格渲染器
	 *
	 * @author const.x
	 * @createDate 2015年6月4日
	 */
	public void setRenderer(AbsCellRenderer renderer) {
		this.renderer = renderer;
	}
	
	
	
	
	
	
	

}
