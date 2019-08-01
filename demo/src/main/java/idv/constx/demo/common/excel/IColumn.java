package idv.constx.demo.common.excel;



/**
 * 自定义列
 * 
 *
 * @author const.x
 */
public interface IColumn <T> {

	/**
	 * 需要写入到单元格的自定义数据
	 * 
	 * @param entrity 当前实体
	 * @param row     当前excel行索引
	 * @return
	 *
	 * @author const.x
	 * @createDate 2015年6月1日
	 */
	public Object getContent(T entrity,int row);
	
	
}
