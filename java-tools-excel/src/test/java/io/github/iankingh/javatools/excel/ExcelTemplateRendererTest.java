package io.github.iankingh.javatools.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

class ExcelTemplateRendererTest {
    @Test
    void rendersScalarPlaceholdersFromAStream() throws Exception {
        byte[] template;
        try (var workbook = new XSSFWorkbook();
                var output = new ByteArrayOutputStream()) {
            var row = workbook.createSheet("template").createRow(0);
            row.createCell(0).setCellValue("${name}");
            row.createCell(1).setCellValue("count");
            row.createCell(2).setCellValue("${empty}");
            workbook.write(output);
            template = output.toByteArray();
        }

        try (var input = new ByteArrayInputStream(template);
                var rendered =
                        ExcelTemplateRenderer.render(
                                input, Map.of("name", "Ian", "count", 2, "empty", ""))) {
            var row = rendered.getSheetAt(0).getRow(0);
            assertEquals("Ian", row.getCell(0).getStringCellValue());
            assertEquals(2.0, row.getCell(1).getNumericCellValue());
            assertEquals("", row.getCell(2).getStringCellValue());
        }
    }

    @Test
    void repeatsTemplateRowsAndPreservesStyles() throws Exception {
        try (var workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet();
            var template = sheet.createRow(1);
            var style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            template.createCell(0).setCellValue("${name}");
            template.getCell(0).setCellStyle(style);
            template.createCell(1).setCellValue("${amount}");
            template.createCell(2).setCellFormula("B2*2");
            sheet.createRow(2).createCell(0).setCellValue("footer");

            ExcelTemplateRenderer.repeatTemplateRow(
                    sheet,
                    1,
                    List.of(
                            Map.of("name", "one", "amount", 1),
                            Map.of("name", "two", "amount", 2)));

            assertEquals("one", sheet.getRow(1).getCell(0).getStringCellValue());
            assertEquals("two", sheet.getRow(2).getCell(0).getStringCellValue());
            assertEquals(2.0, sheet.getRow(2).getCell(1).getNumericCellValue());
            assertEquals("B3*2", sheet.getRow(2).getCell(2).getCellFormula());
            assertEquals(style, sheet.getRow(2).getCell(0).getCellStyle());
            assertEquals("footer", sheet.getRow(3).getCell(0).getStringCellValue());
        }
    }

    @Test
    void removesTemplateRowForEmptyData() throws Exception {
        try (var workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet();
            sheet.createRow(0).createCell(0).setCellValue("${name}");
            sheet.createRow(1).createCell(0).setCellValue("footer");

            ExcelTemplateRenderer.repeatTemplateRow(sheet, 0, List.of());

            assertEquals("footer", sheet.getRow(0).getCell(0).getStringCellValue());
            assertNull(sheet.getRow(1));
        }
    }

    @Test
    void handlesNonStringCellsAndValidatesArguments() throws Exception {
        try (var workbook = new XSSFWorkbook()) {
            var row = workbook.createSheet().createRow(0);
            row.createCell(0, CellType.BOOLEAN).setCellValue(true);
            ExcelTemplateRenderer.replacePlaceholders(workbook, Map.of("unused", "value"));
            assertEquals(true, row.getCell(0).getBooleanCellValue());

            assertThrows(
                    NullPointerException.class,
                    () ->
                            ExcelTemplateRenderer.repeatTemplateRow(
                                    workbook.getSheetAt(0), 5, List.of()));
            assertThrows(
                    NullPointerException.class,
                    () -> ExcelTemplateRenderer.replacePlaceholders(workbook, null));
        }
    }
}
