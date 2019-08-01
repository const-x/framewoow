package idv.constx.demo.common.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取excel文件
 * 
 * @since 6.3
 * @version 2014-03-05 14:38:18
 * @author const.x
 */
public class ExcelReader {

	private static final String EXCEL_TYPE_XLS = "xls",
			EXCEL_TYPE_XLSX = "xlsx";

	private Workbook book;

	private int currentIndex = -1;

	private Sheet sheet;

	private boolean hasNext = false;
	private Object[] values = null;
	

	/**
	 * 
	 * @param in
	 * @param type   xls|xlsx
	 * @throws Exception
	 */
	public ExcelReader(InputStream in,String type) throws Exception {
		if (type.equalsIgnoreCase(EXCEL_TYPE_XLS)) {
			this.book = new HSSFWorkbook(in);
		} else {
			this.book = new XSSFWorkbook(in);
		}
		this.loadSheet(null);
	}

	/**
	 * 读取页签
	 * @param sheetname
	 */
	public void loadSheet(String sheetname) {
		this.loadSheet(sheetname, 0);
	}

	
	/**
	 * 从指定行开始读取数据
	 * @param sheetname
	 * @param begin 
	 */
	public void loadSheet(String sheetname, int begin) {
		if (sheetname == null) {
			this.sheet = this.book.getSheetAt(0);
		} else {
			this.sheet = this.book.getSheet(sheetname);
		}
		currentIndex = begin;
		hasNext = true;
		values = null;
	}
	
	/**
	 * 是否有下一行
	 * @return
	 */
	public boolean hasNext() {
		if(hasNext){
			this.currentIndex++;
			Row nextRow = this.sheet.getRow(this.currentIndex);
			if (nextRow == null || nextRow.getLastCellNum() == -1) {
				hasNext = false;
			}else{
				int end = nextRow.getLastCellNum();
				values = new Object[end];
				boolean isAllEmpty = true;
				for (int i = 0; i < end; i++) {
					Cell cell = nextRow.getCell(i);
					if (cell == null) {
						values[i] = null;
					} else {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							values[i] = cell.getStringCellValue();
						} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
							values[i] = cell.getBooleanCellValue();
						} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							DecimalFormat df = new DecimalFormat("0");//使用DecimalFormat类对科学计数法格式的数字进行格式化
							values[i] = df.format(cell.getNumericCellValue());
						}else{
							values[i] = cell.getStringCellValue();
						}
						if(values[i] != null){
							isAllEmpty = false;
						}
					}
				}
				if(isAllEmpty){
					hasNext = false;
				}
			}
		}
		return hasNext;
	}
	


	/**
	 * 读取下一行内容
	 * @return
	 */
	public Object[] next() {
		return values;
	}
	
	public void updateCell(int rowIndex, int colIndex, String value) {
		this.updateCell(rowIndex, colIndex, value, null, null, null);
	}

	public void updateCell(int rowIndex, int colIndex, String value,
			Short bgcolor, Short fontColor, Boolean boldness) {
		Row row = this.sheet.getRow(rowIndex);
		if (row == null) {
			row = this.sheet.createRow(colIndex);
		}
		Cell cell = row.getCell(colIndex);
		if (cell == null) {
			cell = row.createCell(colIndex);
			if (bgcolor != null) {
				cell.getCellStyle().setFillForegroundColor(bgcolor);
			}
			if (fontColor != null || boldness != null) {
				cell.getCellStyle().setFont(this.getFont(fontColor, boldness));
			}
		}
		cell.setCellValue(value);
	}

	private Map<String, Font> fonts = new HashMap<String, Font>();

	private Font getFont(Short fontColor, Boolean boldness) {
		String key = fontColor.toString() + boldness;
		if (!fonts.containsKey(key)) {
			Font font = book.createFont();
			if (fontColor != null) {
				font.setColor(fontColor);
			}
			if (boldness != null && boldness) {
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			}
			fonts.put(key, font);
			return font;
		}
		return fonts.get(key);
	}


	public int getCurrentIndex() {
		return currentIndex;
	}

	public void flush(OutputStream out) throws IOException{
		this.book.write(out);
	}
	

}
