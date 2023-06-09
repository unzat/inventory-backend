package com.unzatech.inventory.util;

import com.unzatech.inventory.model.Category;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class CategoryExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Category> category;

    public CategoryExcelExporter(List<Category> categories){
        this.category = categories;
        workbook = new XSSFWorkbook();

    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Resultado");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Descripcion", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
    }

    private void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Category resul: category) {
            Row row = sheet.createRow(rowCount++);
            int columCount=0;
            createCell(row, columCount++, String.valueOf(resul.getId()), style);
            createCell(row, columCount++, resul.getName(), style);
            createCell(row, columCount++, resul.getDescription(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        workbook.close();

        servletOutputStream.close();
    }
}
