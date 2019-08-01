package idv.constx.demo.common.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 单元格渲染器
 * 
 *
 * @author const.x
 */
public abstract class AbsCellRenderer<T> {
	

	
	/**
	 * 需要写入到单元格数据
	 * 
	 * @param entity  当前实体
	 * @param value    需渲染的值
	 * @param field    需渲染的字段名
	 * @param row      当前excel行索引
	 * @return
	 *
	 * @author const.x
	 * @createDate 2015年6月4日
	 */
	public abstract Object contentRender(final T entity,Object value,final int row);
	
	/**
	 *  子类可覆盖此方法以实现对单元格样式的处理
	 * 
	 * @param entity  当前实体
	 * @param value    当前单元格值
	 * @param style    当前单元格的样式对象
	 * @param row      当前excel行索引
	 *
	 * @author const.x
	 * @createDate 2015年6月4日
	 */
	public HSSFCellStyle cellStyleRender(final T entity,final Object value,final HSSFWorkbook book,HSSFCellStyle style,final int row){
		return style;
	}
	
	public XSSFCellStyle cellStyleRender2007(final T entity,final Object value,final XSSFWorkbook book,XSSFCellStyle style,final int row){
		return style;
	}

}
