package cc.xushuli.tableConfigurationParser.prototype;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import cc.xushuli.tableConfigurationParser.util.DateUtil;

public class TableCarrier implements TableBody {

	private List<List<String>> sheetValues;
	private Workbook book;

	public TableCarrier(String xlsFile) {
		this(new File(xlsFile));
	}

	public TableCarrier(File xlsFile){
		String fileName = xlsFile.getName();
	    FileInputStream fis = null;
	    try {
	    	try {
	    		fis = new FileInputStream(xlsFile);
	    		book = new HSSFWorkbook(fis);
			} catch (Exception e) {
				fis = new FileInputStream(xlsFile);
				book = new XSSFWorkbook(fis);
			}
	    }catch(Exception e){
	    	throw new RuntimeException(e);
	    }finally {
	        try {
	        	if( null != fis ){
	        		fis.close();
	        	}
			} catch (IOException e) {
		    	throw new RuntimeException(e);
			}
	    }
	    parseExcel();
	}

	public List<List<String>> getSheetValues() {
		// TODO Auto-generated method stub
		return this.sheetValues;
	}

	private void parseExcel() {
		List<List<String>> sheetValues = new ArrayList<List<String>>();

		Sheet sheet = book.getSheetAt(0);// XSSFSheet(0);
		int rows = sheet.getPhysicalNumberOfRows();
		for (int r = 0; r <= rows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			int cells = row.getPhysicalNumberOfCells();
			List<String> rowValues = new ArrayList<String>();

			for (int c = 0; c < cells; c++) {
				Cell cell = row.getCell(c);
				if (cell == null) {
					continue;
				}
				String value = null;

				switch (cell.getCellType()) {

				case Cell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						double d = cell.getNumericCellValue();
						Date date = HSSFDateUtil.getJavaDate(d);
						value = DateUtil.Date2String(date, "yyyyMMdd");
					} else {
						DecimalFormat formatCell = (DecimalFormat) NumberFormat
								.getPercentInstance();
						formatCell
								.applyPattern("######################################.##################################");
						double strCell = cell.getNumericCellValue();
						value = formatCell.format(strCell);
					}
					break;

				case Cell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;

				case Cell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue() + "";
					break;
				case Cell.CELL_TYPE_FORMULA:
					FormulaEvaluator evaluator = book.getCreationHelper()
							.createFormulaEvaluator();
					evaluator.evaluateFormulaCell(cell);
					CellValue cellValue = evaluator.evaluate(cell);
					value = cellValue.getNumberValue() + "";
					break;
				default:
					value = cell.getStringCellValue() + "";
				}
				rowValues.add(value.trim());
			}
			if (!CollectionUtils.isEmpty(rowValues)) {
				sheetValues.add(rowValues);
			}
		}

		try {
			book.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.sheetValues = sheetValues;
	}

}
