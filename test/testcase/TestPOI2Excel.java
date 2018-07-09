package testcase;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestPOI2Excel {
	@Test
	public void testWrite03Excel() throws Exception{
//		1、创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
//		2、创建工作表
		HSSFSheet sheet = workbook.createSheet("hello world");//制定表名
//		3、创建行：从0开始
		HSSFRow row = sheet.createRow(3);
//		4、创建单元格
		HSSFCell cell = row.createCell(3);
		cell.setCellValue("Hello World");
		
//		5.输出到硬盘
		FileOutputStream outputStream = new FileOutputStream("E:/log/excel/test.xls");
		workbook.write(outputStream);
//		6.关闭
		workbook.close();
		outputStream.close();
	}
	/**
	 * 通用03 07版本
	 */
	@Test
	public void testRead03Excel() throws Exception{
		String FileName = "E:\\log\\excel\\test.xls";
		if(FileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
		
			boolean is03Excel = FileName.matches("^.+\\.(?i)(xls)$");
			
			FileInputStream inputStream = new FileInputStream(FileName);
	//		1、读取工作簿
			Workbook workbook = is03Excel ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
	//		2、读取工作表
			Sheet sheet = workbook.getSheetAt(0);//
	//		3、读取行：从0开始
			Row row = sheet.getRow(3);
	//		4、读取单元格
			Cell cell = row.getCell(3);
			
	//		5.输入
			System.out.println(cell.getStringCellValue());
	//		6.关闭
			workbook.close();
			inputStream.close();
		}
	}
	/**
	 * 样式
	 */
	@Test
	public void testExcelStyle() throws Exception{
//		1、创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
//		1.1合并
		CellRangeAddress cellRangeAddress = new CellRangeAddress(3, 3, 3, 4);
//		1.2样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//		1.3创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		font.setFontHeightInPoints((short) 16);//字体大小
//			加载字体
		style.setFont(font);
//		1.4单元格背景
//			设计背景填充模式
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//以前景色为准
//			设置填充背景色
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);
//			设置填充前景色
		style.setFillForegroundColor(HSSFColor.RED.index);
		
//		2、创建工作表
		HSSFSheet sheet = workbook.createSheet("hello world");//制定表名
//		2.1合并
		sheet.addMergedRegion(cellRangeAddress);
		
//		3、创建行：从0开始
		HSSFRow row = sheet.createRow(3);
		
//		4、创建单元格
		HSSFCell cell = row.createCell(3);
//		4.1加样式
		cell.setCellStyle(style);
		cell.setCellValue("Hello World!");
		
//		5.输出到硬盘
		FileOutputStream outputStream = new FileOutputStream("E:/log/excel/test.xls");
		workbook.write(outputStream);
//		6.关闭
		workbook.close();
		outputStream.close();
	}
}
