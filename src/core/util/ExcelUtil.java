package core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import nsfw.user.entity.User;
import nsfw.user.service.UserService;
import nsfw.user.service.impl.UserServiceImpl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	/**
	 * 单元格样式
	 */
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//			字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints(fontSize);
		style.setFont(font);
		return style;
	}
	/**
	 * 导出用户列表
	 * @param userList
	 * @param outputStream
	 */
	public static void exportUserExcel(List<User> userList,
			ServletOutputStream outputStream) {
		try {
			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
			//1.2、头标题样式
			HSSFCellStyle headStyle = createCellStyle(workbook, (short) 16);
			
			//1.3、列标题样式
			HSSFCellStyle lieStyle = createCellStyle(workbook, (short) 13);
			
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("用户列表");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			//设置默认列宽
			sheet.setDefaultColumnWidth(25);
			
			//3、创建行
			//3.1、创建头标题行；并且设置头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
//			加载样式
			cell1.setCellStyle(headStyle);
			cell1.setCellValue("用户列表");
			
			//3.2、创建列标题行；并且设置列标题
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = {"用户列表","账号", "所属部门", "性别", "手机号码", "电子邮箱", "生日"};
			for(int i=0; i<titles.length; i++){
				HSSFCell cell2 = row2.createCell(i);
//			加载样式
				cell2.setCellStyle(lieStyle);
				cell2.setCellValue(titles[i]);
			}
			
			
			//4、操作单元格；将用户列表写入excel
			if(userList != null){
				for(int j=0; j<userList.size(); j++){
					HSSFRow row = sheet.createRow(j+2);
					
					HSSFCell cell10 = row.createCell(0);
					cell10.setCellValue(userList.get(j).getName());
					
					HSSFCell cell11 = row.createCell(1);
					cell11.setCellValue(userList.get(j).getAccount());
					
					HSSFCell cell12 = row.createCell(2);
					cell12.setCellValue(userList.get(j).getDept());
					
					HSSFCell cell13 = row.createCell(3);
					cell13.setCellValue(userList.get(j).isGender()?"男":"女");
					
					HSSFCell cell14 = row.createCell(4);
					cell14.setCellValue(userList.get(j).getMobile());
					
					HSSFCell cell15 = row.createCell(5);
					cell15.setCellValue(userList.get(j).getEmail());
					
					HSSFCell cell16 = row.createCell(6);
					if(userList.get(j).getBirthday() != null){
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date date = userList.get(j).getBirthday();
						String sDate = format.format(date);
						try {
							date = format.parse(sDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						cell16.setCellValue(date);
					}
				}
			}
			//
			//5、输出
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导入用户列表
	 */
	public static void importUserExcel(File userExcel, String userExcelFileName) {
		UserService userService = new UserServiceImpl();
		try {
			FileInputStream fileInputStream = new FileInputStream(userExcel);
			boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
//		1、读取工作簿
			Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream) : new XSSFWorkbook(fileInputStream);
//		2、读取工作表
			Sheet sheet = workbook.getSheetAt(0);
//		3、读取行
			if(sheet.getPhysicalNumberOfRows() > 2){
				User user = null;
				for(int k=2; k<sheet.getPhysicalNumberOfRows(); k++){
//		4、读取单元格
					Row row = sheet.getRow(k);
					user = new User();
//					用户名：
					Cell cell0 = row.getCell(0);
					user.setName(cell0.getStringCellValue());
//					账号：
					Cell cell1 = row.getCell(1);
					user.setAccount(cell1.getStringCellValue());
//					所属部门：
					Cell cell2 = row.getCell(2);
					user.setDept(cell2.getStringCellValue());
//					性别：
					Cell cell3 = row.getCell(3);
					user.setGender(cell3.getStringCellValue().equals("男"));
//					手机号：
					Cell cell4 = row.getCell(4);
					String mobile = "";
					
					try {
						mobile = cell4.getStringCellValue();
					} catch (Exception e) {
						double dMobile = cell4.getNumericCellValue();
						mobile = BigDecimal.valueOf(dMobile).toString();//大数据转正常数据
					}
					user.setMobile(mobile);
//					电子邮箱：
					Cell cell5 = row.getCell(5);
					if(cell5.getStringCellValue() != null){
						user.setEmail(cell5.getStringCellValue());
					}
//					生日：
					Cell cell6 = row.getCell(6);
					if(cell6.getDateCellValue() != null){
						user.setBirthday(cell6.getDateCellValue());
					}
//					默认用户密码
					user.setPassword("123456");
					user.setState(user.USER_STATE_VALID);
//		5、保存用户
					userService.save(user);
				}
				
			}
			workbook.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
