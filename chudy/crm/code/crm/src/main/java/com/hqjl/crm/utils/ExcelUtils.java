package com.hqjl.crm.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.JsonNode;

public class ExcelUtils {

    public static byte[] generateExcel(String[] head, List<Object[]> datas, boolean isXlsx) throws IOException {
        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        Workbook workbook = null;
        if (isXlsx) {// 如果excel是xlsx文件
            workbook = new XSSFWorkbook();
        } else {// 如果excel是xls�?
            workbook = new HSSFWorkbook();
        }

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(DatetimeUtil.STANDARD_DATETIME_PATTERN));

        Sheet sheet = workbook.createSheet();
        int r = 0;

        Row row = sheet.createRow(r);
        for (int i = 0; i < head.length; i++) {
            row.createCell(i).setCellValue(head[i]);
        }

        for (Object[] val : datas) {
            r++;
            row = sheet.createRow(r);

            for (int c = 0; c < val.length; c++) {
                Cell cell = row.createCell(c);

                if (val[c] instanceof Date) {
                    cell.setCellStyle(dateStyle);
                    cell.setCellValue((Date) val[c]);
                } else if (val[c] instanceof Timestamp) {
                    cell.setCellStyle(dateStyle);
                    cell.setCellValue((Timestamp) val[c]);
                } else if (val[c] instanceof Calendar) {
                    cell.setCellStyle(dateStyle);
                    cell.setCellValue((Calendar) val[c]);
                } else if (val[c] instanceof Integer) {
                    cell.setCellValue((Integer) val[c]);
                } else if (val[c] instanceof Double) {
                    cell.setCellValue((Double) val[c]);
                } else if (val[c] instanceof Float) {
                    cell.setCellValue((Float) val[c]);
                } else if (val[c] instanceof Double) {
                    cell.setCellValue((Double) val[c]);
                } else if (val[c] instanceof Boolean) {
                    cell.setCellValue((Boolean) val[c]);
                } else {
                    cell.setCellValue(StringUtil.formatNull((String) val[c]));
                }

            }

        }

        workbook.write(os1);

        return os1.toByteArray();
    }

    public static byte[] generateExcel(ResultSet rs, boolean isXlsx) throws IOException, SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int num = md.getColumnCount();
        String[] header = new String[num];
        for (int i = 1; i <= num; i++) {
            String cName = md.getColumnName(i);
            header[i - 1] = cName;
        }
        List<Object[]> values = new ArrayList<Object[]>();

        while (rs.next()) {
            Object[] value = new Object[num];
            for (int i = 1; i <= num; i++) {
                value[i - 1] = rs.getObject(i);
            }
            values.add(value);
        }
        byte[] result = generateExcel(header, values, isXlsx);

        return result;
    }

    public static byte[] generateObjectToExcel(String[] displayName, String[] header, List<Object> list, boolean isXlsx)
            throws IOException {
        List<Object[]> values = new ArrayList<Object[]>();
        for (Object o : list) {
            JsonNode node = JsonUtil.objectToJsonNode(o);
            Object[] data = new Object[header.length];
            for (int i = 0; i < header.length; i++) {
                JsonNode d = JsonUtil.getByJPath(node, header[i]);
                Object obj = JsonUtil.nodeToObject(d);
                data[i] = obj;
            }
            values.add(data);
        }

        byte[] result = generateExcel(displayName, values, isXlsx);

        return result;
    }

    public static List<Object[]> ExcelToStringArray(byte[] content) throws IOException, InvalidFormatException {
        InputStream is = new ByteArrayInputStream(content);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(DatetimeUtil.STANDARD_DATETIME_PATTERN));

        List<Object[]> list = new ArrayList<Object[]>();
        Object[] val = null;

        int len = 0;
        if (null != sheet) {
            for (Row row : sheet) {

                if (row.equals(sheet.getRow(0))) {
                    len = row.getLastCellNum();
                    continue;
                }
                val = new Object[len];

                for (int c = 0; c < len; c++) {
                    Cell cell = row.getCell(c);

                    String valStr = "";
                    if (null != cell) {
                        switch (cell.getCellType()) {// 判断内容类型

                        case Cell.CELL_TYPE_NUMERIC: // 数字类型
                            if (DateUtil.isCellDateFormatted(cell)) {// 判断是否是日�?
                                cell.setCellStyle(dateStyle);
                                valStr = String.valueOf(cell.getDateCellValue());
                            } else {
                                valStr = String.valueOf(cell.getNumericCellValue()).replaceAll("\\.0*$", "");
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN: // 布尔类型
                            valStr = String.valueOf(cell.getBooleanCellValue());
                            break;
                        default:
                            valStr = cell.getRichStringCellValue().getString();
                        }
                    }
                    val[c] = valStr;
                }

                list.add(val);
            }
        }

        return list;
    }

}
