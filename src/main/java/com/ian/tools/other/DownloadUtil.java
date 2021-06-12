package com.ian.tools.other;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

/**
 * Excel 工具類別
 */
@Slf4j
public class ExcelUtil {

	private final static String EXCEL2003 = "xls";

	

	/**
	 * 
	 * @param dataMap
	 * @return
	 */
	public static Map<String, String> convertDataMapkey(Map<String, String> dataMap) {
		Map<String, String> reMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : dataMap.entrySet()) {
			reMap.put("#" + entry.getKey(), entry.getValue());
		}
		return reMap;

	}

	/**
	 * 
	 * @param inputStream
	 * @param dataMap
	 * @param dataList
	 * @return
	 * @throws IOException
	 */
	public static Workbook ExcelWriter(InputStream inputStream, Map<String, String> dataMap,
			List<Map<String, String>> dataList) throws IOException {
		log.debug(ESAPIUtil.vaildLog("ExcelWriter dataMap " + dataMap));
		log.debug(ESAPIUtil.vaildLog("ExcelWriter dataList " + dataList));
		Workbook workbook = new HSSFWorkbook(inputStream);
		// 取代workbook Sheet表 跟 dataMap 一樣的Key
		replaceExcelData(workbook, dataMap, dataList);
		return workbook;
	}

	/**
	 * 取代Workbook 跟 dataMap 一樣的Key
	 * 
	 * @param workbook
	 * @param dataMap
	 * @param dataList
	 * @return
	 */
	public static Workbook replaceExcelData(Workbook workbook, Map<String, String> dataMap,
			List<Map<String, String>> dataList) {
		Map forMap = new HashMap();
		if (null != dataList && !dataList.isEmpty()) {
			forMap = dataList.get(0);
		}
		//設定dataList 的起始點 避免跑多次
		int forMapindex = 0;
		// 取得第一個 sheet
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row) {
				String key = String.valueOf(cell).trim();
				if (dataMap.containsKey(key)) {
					cell.setCellValue(null == dataMap.get(key) ? "" : dataMap.get(key).toString());
				}
				// 如果是list 則用下面的方法換掉
				else if (forMap.containsKey(key) && forMapindex != row.getRowNum()) {
					convertDataToRow(sheet, dataList, row.getRowNum());
					forMapindex = row.getRowNum();

				}
			}
		}
		return workbook;
	}

	/**
	 * 
	 * 將數據轉換成行
	 * 
	 * @param sheet
	 * @param dataList
	 * @param rowNum
	 */
	private static void convertDataToRow(Sheet sheet, List<Map<String, String>> dataList, int rowNum) {
	    
	    log.debug("convertDataToRow dataList ");
		// 取得 原始要取代的row #LSTLTD #MEMO #CODDBCR #AMTTRN #BAL #CHKNUM #DATA16 #TRNBRH
		// #FILLER2 #TIME
		Row RowOriginal = sheet.getRow(rowNum);

		for (Iterator<Map<String, String>> it = dataList.iterator(); it.hasNext();) {
			Map<String, String> dataMap = it.next();
			if (dataMap == null) {
				continue;
			}
			// 輸出行數據
			Row row = sheet.createRow(rowNum++);
			int cellNum = 0;
			Cell cell;
			for (Entry<String, String> entry : dataMap.entrySet()) {
				// 取得原始要取代的row
				String key = String.valueOf(RowOriginal.getCell(cellNum)).trim();
				// 如果跟list 的map 一樣才取代
				if (dataMap.containsKey(key)) {
					// 原始要取代的row 的cell 的style
					CellStyle cellStyle = RowOriginal.getCell(cellNum).getCellStyle();
					cell = row.createCell(cellNum++);
					cell.setCellValue(null == dataMap.get(key) ? "" : dataMap.get(key).toString());
					cell.setCellStyle(cellStyle);
				}
			}
		}

	}

}