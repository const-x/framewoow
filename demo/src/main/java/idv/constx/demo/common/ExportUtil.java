package idv.constx.demo.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;



/**
 * 报表工具类
 * @author li.jiazhi 2014/12/26
 *
 */
public class ExportUtil {
	
	
	/**
	 * 创建String类型的单元格
	 * @param row
	 * @param index
	 * @param value
	 * @param s
	 */
	public static void createCell(HSSFRow row, int index, String value,
			HSSFCellStyle s) {
		HSSFCell cell = row.createCell(index);// 创建一列
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		if (value != null) {
			cell.setCellValue(value);
		}

		cell.setCellStyle(s);

	}
	
	/**
	 * 创建数值类型的单元格
	 * @param row
	 * @param index
	 * @param value
	 */
	public static void createNumCell(HSSFRow row, int index, Float value) {
		HSSFCell cell = row.createCell(index);// 创建一列
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		if (value != null) {
			cell.setCellValue(value);
		}
	}

	
	/**
	 * 创建tab页
	 * @param workbook
	 * @param name
	 * @return
	 */
	public static HSSFSheet createSheet(HSSFWorkbook workbook, String name) {
		name = name.replace('/', '&');
		name = name.replace('\\', '&');
		name = name.replace('*', '&');
		name = name.replace('?', '&');
		name = name.replace('[', '&');
		name = name.replace(']', '&');
		return workbook.createSheet(name);
	}

	
	/*
	 * 创建报表头部内容
	 */
	public static void createHeadCell(HSSFWorkbook workbook, HSSFRow row, int index,
			String value, Short color) {
		if (color == null) {
			color = HSSFColor.YELLOW.index;
		}
		CellStyle cellStyle = workbook.createCellStyle();
		Font headfont = workbook.createFont();
		headfont.setColor(Font.COLOR_NORMAL);
		headfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(headfont);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		cellStyle.setBorderBottom(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderLeft(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderRight(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderTop(CellStyle.SOLID_FOREGROUND);
		HSSFCell cell = row.createCell(index);// 创建一列
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}

}
