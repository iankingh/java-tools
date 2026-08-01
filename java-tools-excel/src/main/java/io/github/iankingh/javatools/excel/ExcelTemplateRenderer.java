package io.github.iankingh.javatools.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;

/** Renders exact placeholders in Excel workbooks while preserving cell styles. */
public final class ExcelTemplateRenderer {
    private ExcelTemplateRenderer() {}

    /**
     * Opens a workbook and replaces scalar placeholders.
     *
     * <p>The caller owns both the input stream and returned workbook.
     */
    public static Workbook render(InputStream template, Map<String, ?> values) throws IOException {
        Objects.requireNonNull(template, "template");
        Workbook workbook = WorkbookFactory.create(template);
        replacePlaceholders(workbook, values);
        return workbook;
    }

    /** Replaces exact keys and {@code ${key}} placeholders in every sheet. */
    public static void replacePlaceholders(Workbook workbook, Map<String, ?> values) {
        Objects.requireNonNull(workbook, "workbook");
        Objects.requireNonNull(values, "values");
        for (Sheet sheet : workbook) {
            for (Row row : sheet) {
                replacePlaceholders(row, values);
            }
        }
    }

    /**
     * Repeats one template row for each data item, shifting following rows as needed.
     *
     * <p>String cells may contain an exact key or a {@code ${key}} placeholder.
     */
    public static void repeatTemplateRow(
            Sheet sheet, int templateRowIndex, List<? extends Map<String, ?>> rows) {
        Objects.requireNonNull(sheet, "sheet");
        Objects.requireNonNull(rows, "rows");
        Row template =
                Objects.requireNonNull(sheet.getRow(templateRowIndex), "Template row not found");
        if (rows.isEmpty()) {
            sheet.removeRow(template);
            if (templateRowIndex < sheet.getLastRowNum()) {
                sheet.shiftRows(templateRowIndex + 1, sheet.getLastRowNum(), -1);
            }
            return;
        }

        int additionalRows = rows.size() - 1;
        if (additionalRows > 0 && templateRowIndex < sheet.getLastRowNum()) {
            sheet.shiftRows(
                    templateRowIndex + 1, sheet.getLastRowNum(), additionalRows, true, false);
        }
        for (int index = 1; index < rows.size(); index++) {
            copyRow(template, sheet.createRow(templateRowIndex + index));
        }
        for (int index = 0; index < rows.size(); index++) {
            replacePlaceholders(
                    sheet.getRow(templateRowIndex + index),
                    Objects.requireNonNull(rows.get(index), "Row values"));
        }
    }

    private static void replacePlaceholders(Row row, Map<String, ?> values) {
        for (Cell cell : row) {
            if (cell.getCellType() != CellType.STRING) {
                continue;
            }
            String text = cell.getStringCellValue();
            String key = values.containsKey(text) ? text : placeholderKey(text);
            if (key != null && values.containsKey(key)) {
                setValue(cell, values.get(key));
            }
        }
    }

    private static String placeholderKey(String value) {
        return value.startsWith("${") && value.endsWith("}")
                ? value.substring(2, value.length() - 1)
                : null;
    }

    private static void copyRow(Row source, Row target) {
        CellCopyPolicy policy = new CellCopyPolicy();
        if (source instanceof XSSFRow xssfSource && target instanceof XSSFRow xssfTarget) {
            xssfTarget.copyRowFrom(xssfSource, policy);
        } else if (source instanceof HSSFRow hssfSource && target instanceof HSSFRow hssfTarget) {
            hssfTarget.copyRowFrom(hssfSource, policy);
        } else {
            throw new IllegalArgumentException(
                    "Source and target rows must use the same POI format");
        }
    }

    private static void setValue(Cell cell, Object value) {
        if (value == null) {
            cell.setBlank();
        } else if (value instanceof Boolean booleanValue) {
            cell.setCellValue(booleanValue);
        } else if (value instanceof Number number) {
            cell.setCellValue(number.doubleValue());
        } else if (value instanceof Date date) {
            cell.setCellValue(date);
        } else if (value instanceof Calendar calendar) {
            cell.setCellValue(calendar);
        } else {
            cell.setCellValue(value.toString());
        }
    }
}
