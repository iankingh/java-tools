package com.ian.javatools;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Excel解析器
 */
@Slf4j
public class ExcelParser {

    /**
     * Excel文件
     */
    private File excelFile;

    private Workbook workbook;

    public ExcelParser(String filePath) {
        this.excelFile = new File(filePath);
    }

    public void ReadExcel() {
        try (FileInputStream fileInputStream = new FileInputStream(excelFile)) {
            workbook = new XSSFWorkbook(fileInputStream);

        } catch (FileNotFoundException e) {
            log.debug("File not found: " + excelFile.getAbsolutePath());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<Integer, Object> getSheetToMap(Map<Integer, Integer> sheetNum) {
        Map<Integer, Object> sheetMap = new LinkedHashMap<>();
        if (sheetNum == null) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetMap.put(i, workbook.getSheetAt(i));
            }
        } else {
            for (Map.Entry<Integer, Integer> entry : sheetNum.entrySet()) {
                sheetMap.put(entry.getKey(), workbook.getSheetAt(entry.getValue()));
            }

        }
        return sheetMap;
    }



    public Map<Integer, Object> getAnyRow2Title(Sheet sheet,int TitleRowIndex ) {
        Map<Integer, Object> TitleMap = new LinkedHashMap<>();
        Row row = sheet.getRow(TitleRowIndex);
        getCell(sheet,row,TitleMap);
        return TitleMap;
    }


   /**
    * 将某个工作表的某行转换为除去标题的Map实现类的List集合，并返回
    * @param sheet 工作表
    *                 @param row 行号
    *                               @return
    */
   private List<Map<Integer,Object>> getRow2ListWithMap(Sheet sheet, int TitleRowIndex) {
       List<Map<Integer, Object>> tableRows = new ArrayList<>();
       int rows = sheet.getPhysicalNumberOfRows();
       for (int i = TitleRowIndex + 1; i < rows; i++) {
           Map<Integer, Object> rowMap = new LinkedHashMap<>();
           Row row = sheet.getRow(i);
           if (row == null) {
               log.debug("row is null");
           } else {
               Map<Integer, Object> RowMap = new LinkedHashMap<>();
               getCell(sheet, row, RowMap);
               tableRows.add(RowMap);
           }
       }
           return tableRows;
   }

    private void getCell(Sheet sheet, Row row, Map<Integer, Object> titleMap) {
       if (row == null) {
           log.debug("row is null");
           return;
       }



   }

   public List<ExcelBean> GetExcelBeanList() {
       List<ExcelBean> excelBeanList = new ArrayList<>();
       Map<Integer, Object> sheetMap = getSheetToMap(null);
       for (Map.Entry<Integer, Object> entry : sheetMap.entrySet()) {
           ExcelBean excelBean = new ExcelBean();
           excelBean.setSheetName(workbook.getSheetName(entry.getKey()));
           Sheet sheet = (Sheet) entry.getValue();
           Map<Integer, Object> titleMap = getAnyRow2Title(sheet, 0);
           excelBean.setTitle((String[]) titleMap.values().toArray());
           List<Map<Integer, Object>> tableRows = getRow2ListWithMap(sheet, 0);
           excelBean.setContent((String[][]) tableRows.toArray());
           excelBeanList.add(excelBean);
       }
       return excelBeanList;
   }




    public void Close () {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
