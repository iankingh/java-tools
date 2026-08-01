package com.ian.tools.other;

import io.github.iankingh.javatools.excel.ExcelTemplateRenderer;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @deprecated Use {@link ExcelTemplateRenderer}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class ExcelUtil {
    private ExcelUtil() {}

    /**
     * @deprecated Prefer {@code ${key}} placeholders.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Map<String, String> convertDataMapkey(Map<String, String> dataMap) {
        Map<String, String> result = new LinkedHashMap<>();
        dataMap.forEach((key, value) -> result.put("#" + key, value));
        return result;
    }

    /**
     * @deprecated Use {@link ExcelTemplateRenderer#render(InputStream, Map)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Workbook ExcelWriter(
            InputStream inputStream,
            Map<String, String> dataMap,
            List<Map<String, String>> dataList)
            throws IOException {
        Workbook workbook = ExcelTemplateRenderer.render(inputStream, dataMap);
        return replaceExcelData(workbook, dataMap, dataList);
    }

    /**
     * @deprecated Use the focused methods on {@link ExcelTemplateRenderer}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Workbook replaceExcelData(
            Workbook workbook, Map<String, String> dataMap, List<Map<String, String>> dataList) {
        ExcelTemplateRenderer.replacePlaceholders(workbook, dataMap);
        if (dataList == null || dataList.isEmpty()) {
            return workbook;
        }
        Sheet sheet = workbook.getSheetAt(0);
        Map<String, String> firstRowValues = dataList.getFirst();
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.STRING
                        && firstRowValues.containsKey(cell.getStringCellValue())) {
                    ExcelTemplateRenderer.repeatTemplateRow(sheet, row.getRowNum(), dataList);
                    return workbook;
                }
            }
        }
        throw new IllegalArgumentException("No template row matches the provided data keys");
    }
}
