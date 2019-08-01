package idv.constx.demo.common.excel;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import idv.constx.demo.common.exception.ExceptionUtils;

/**
 * 实体excel生成器
 * 
 *
 * @author xieyalong
 */
public class ExcelCreater2007 <T>  {
	
	private int maxRowPreSheet = 30000;

	private List<Object> source;
	
	private Collection<ExcelColumnMeta> columns;
	
	private List<String> colNames;
	
	private ExcelColumnMeta sheetField;

	private XSSFWorkbook book;
	
	private String defaultSheetName = "Sheet1";
	
	private Map<String,XSSFSheet> sheetMap = new HashMap<String,XSSFSheet>();
	
	private Map<String,Integer> sheetRowIndexMap= new HashMap<String,Integer>();
	
	private Map<String,Integer> sheetPageIndexMap= new HashMap<String,Integer>();
	
	private Map<String,AbsCellRenderer<T>> cellRenderers= new HashMap<String,AbsCellRenderer<T>>();
	
	private XSSFCellStyle stringCellStyle;
	
	private XSSFCellStyle numCellStyle;
	
	private XSSFCellStyle headCellStyle;
	
    /**
	 * 生成实体对应的excel
     * 
     * @param entities   实体
     * @param clazz      实体类名
     * @param fields     需要显示的实体属性名称(为空则显示所有属性)
     * @param renderers  自定义的列(可为空)
     * @return
     *
     * @author xieyalong
     * @createDate 2015年6月1日
     */
	public XSSFWorkbook create(Collection<ExcelColumnMeta> columns,Collection<T> entities, Class<T> clazz){
		this.book = new XSSFWorkbook();
		this.columns = columns;
		this.initStyle();
		this.initSource(columns,clazz);
		if(entities == null || entities.size() == 0){
			XSSFSheet sheet = this.getSheet(defaultSheetName);
			this.createHead(sheet);
			XSSFRow row = sheet.createRow(1);
			XSSFCell cell = row.createCell(0);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(this.stringCellStyle);
			cell.setCellValue("所有数据插入成功！");
		}
		for(T entity:entities){
		    try {
				this.write(entity, clazz);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return book;
	}
	
	private void initStyle(){
		this.stringCellStyle = book.createCellStyle();
		stringCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		stringCellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 水平居左
		
		this.numCellStyle = book.createCellStyle();
		numCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		numCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);// 水平居右
		
		this.headCellStyle= book.createCellStyle();
		Font headfont = book.createFont();
		headfont.setColor(Font.COLOR_NORMAL);
		headfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headCellStyle.setFont(headfont);
		headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		headCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		headCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headCellStyle.setBorderBottom(CellStyle.SOLID_FOREGROUND);
		headCellStyle.setBorderLeft(CellStyle.SOLID_FOREGROUND);
		headCellStyle.setBorderRight(CellStyle.SOLID_FOREGROUND);
		headCellStyle.setBorderTop(CellStyle.SOLID_FOREGROUND);
	}
	
	private void write(T  entity,Class<T>  clazz) throws IllegalArgumentException, IllegalAccessException{
		String sheetName = null;
		if(this.sheetField == null){
			sheetName = defaultSheetName;
		}else{
			Object sheetValue = null;
			if(sheetField.getColumn() != null){
				sheetValue = sheetField.getColumn().getContent(entity, 1).toString();
			}else{
				String[] fields = sheetField.getField().split("\\.");
				sheetValue = getFieldValue(entity,fields);
			}
			AbsCellRenderer<T> renderer = this.cellRenderers.get(sheetField.getColumnName());
			if(renderer != null){
				sheetName = renderer.contentRender(entity,sheetValue,1).toString();
			}else{
				sheetName = sheetValue.toString();
			}
			
		}
		XSSFSheet sheet =  this.getSheet(sheetName);
		int rowIndex = this.sheetRowIndexMap.get(sheetName);
		this.sheetRowIndexMap.put(sheetName,rowIndex + 1);
		XSSFRow row = sheet.createRow(rowIndex);
		for (int i = 0; i < source.size(); i++) {
			Object col = source.get(i);
			Object value = null;
			String colName = this.colNames.get(i);
			if(col instanceof IColumn){
				IColumn<T>  renderer = (IColumn<T>)col;
				value = renderer.getContent(entity, rowIndex);
			}else{
				String[] fields = ((String)col).split("\\.");
				value = getFieldValue(entity,fields);
			}
			AbsCellRenderer<T> renderer = this.cellRenderers.get(colName);
			if(renderer != null){
				value = renderer.contentRender(entity,value, rowIndex);
			}
			
			XSSFCell cell = row.createCell(i);
			XSSFCellStyle style = null;
			if(value instanceof Integer || value instanceof Double || value instanceof Float ){
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
				style=this.numCellStyle;
			}else{
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				style=this.stringCellStyle;
			}
			if(renderer != null){
				style = renderer.cellStyleRender2007(entity, value,book, style,rowIndex);
			}
			cell.setCellStyle(style);
			if(value != null){
				String valueStr = value.toString();
				cell.setCellValue(valueStr);
			}
		}

	}
	
	
	private XSSFSheet getSheet(String name){
		XSSFSheet sheet;
		if(!this.sheetMap.containsKey(name)){
			sheet = this.book.createSheet(name);
			this.sheetMap.put(name, sheet);
			createHead(sheet);
			this.sheetRowIndexMap.put(name, 1);
			this.sheetPageIndexMap.put(name, 1);
		}
		if(this.sheetRowIndexMap.get(name) > maxRowPreSheet){
			int page = sheetPageIndexMap.get(name) + 1;
			sheet = this.book.createSheet(name +"(" +page+")");
			this.sheetPageIndexMap.put(name, page);
			this.sheetMap.put(name, sheet);
			createHead(sheet);
			this.sheetRowIndexMap.put(name, 1);
		}
		
		return sheetMap.get(name);
	}
	
	private void createHead(XSSFSheet sheet){
		XSSFRow row = sheet.createRow(0);
		row.setHeight((short)400);
		int counter = 0;
		for (ExcelColumnMeta col: columns) {
			XSSFCell cell = row.createCell(counter);// 创建一列
			cell.setCellStyle(this.headCellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(col.getColumnName());
			sheet.setColumnWidth(counter, col.getColumnName().length() * 512 + 512); 
			counter ++ ;
		}
		
	}
	
	
	
	
	private void initSource(Collection<ExcelColumnMeta> columns,Class<T> clazz) {
		this.source = new LinkedList<Object>();
		this.colNames = new LinkedList<String>();
		for(ExcelColumnMeta col : columns){
			Object obj = col.getColumn();
			if( obj == null){
				String column = col.getField();
				obj = column;
			}
			Integer index = col.getIndex();
			if(index == null){
				this.source.add(obj);
				this.colNames.add(col.getColumnName());
			}else{
				this.source.add(index, obj);
				this.colNames.add(index,col.getColumnName());
			}
			if(col.getRenderer() != null){
				cellRenderers.put(col.getColumnName(), col.getRenderer());
			}
		}
	}

	private Map<String,Field> classFields = new HashMap<String,Field>();
  
	private Field getField(Class<?>  clazz,String name) {
		String key = name + "@"+clazz;
		if(classFields.containsKey(key)){
			return classFields.get(key);
		}
		
		for (Field field : clazz.getDeclaredFields()) {
			if(field.getName().equals(name)){
				classFields.put(key, field);
				return field;
			}
		}
		for (Class<?> superClass = clazz.getSuperclass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			for (Field field : superClass.getDeclaredFields()) {
				if(field.getName().equals(name)){
					classFields.put(key, field);
					return field;
				}
			}
		}
		return null;
	}

	
    private Object getFieldValue(Object obj, String[] metaNames){
    	Object value = null;
    	String name = metaNames[0];
    	Field field = this.getField(obj.getClass(), name);
		if (field != null) {
			try {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(value == null){
			return value;
		}
    	
		if(metaNames.length > 1){
			String[] sub = new String[metaNames.length - 1];
			for (int i = 0; i < sub.length; i++) {
				sub[i] = metaNames[i+1];
			}
			return getFieldValue(value,sub);
		}
		return value;
    }
	

	
	/**
	 * 设置单据名称
	 *
	 * @author xieyalong
	 * @createDate 2015年6月3日
	 */
	public void setDefaultSheetName(String defaultSheetName) {
		this.defaultSheetName = defaultSheetName;
	}



	

	/**
	 * @return 文本型单元格样式
	 *
	 * @author xieyalong
	 * @createDate 2015年6月3日
	 */
	public XSSFCellStyle getStringCellStyle() {
		return stringCellStyle;
	}

	/**
	 * @return 数值型单元格样式
	 *
	 * @author xieyalong
	 * @createDate 2015年6月3日
	 */
	public XSSFCellStyle getNumCellStyle() {
		return numCellStyle;
	}

	/**
	 * @return 表头单元格样式
	 *
	 * @author xieyalong
	 * @createDate 2015年6月3日
	 */
	public XSSFCellStyle getHeadCellStyle() {
		return headCellStyle;
	}

	/**
	    * @param sheetField 分表格字段（如门店excel，按照商户分别创个不同的sheet页）
	 */
	public void setSheetField(ExcelColumnMeta sheetField) {
		this.sheetField = sheetField;
	}

	/**
	 * @param 表单最大行数,不能超过65536行
	 *
	 * @author xieyalong
	 * @createDate 2015年6月4日
	 */
	public void setMaxRowPreSheet(int maxRowPreSheet) {
		if(maxRowPreSheet > 65536){
			ExceptionUtils.throwBizException("表单最大行数不能超过65536行");
		}
		this.maxRowPreSheet = maxRowPreSheet;
	}


	
	
	

	
}
