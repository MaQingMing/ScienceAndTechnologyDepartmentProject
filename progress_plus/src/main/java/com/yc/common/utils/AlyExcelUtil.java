package com.yc.common.utils;

import com.google.common.collect.Lists;
import com.yc.exception.CustomException;
import com.yc.exception.MyExcelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Excel 解析
 */

@Slf4j
public class AlyExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlyExcelUtil.class);

    /**
     * 获取表格数据
     *
     * @param inputStream
     * @return
     */
    public static List<List<Map<String, Object>>> excelToShopIdList(InputStream inputStream) {
        List<List<Map<String, Object>>> sheets = new ArrayList<>();
        Workbook workbook = null;
        //读取多个sheet
        int index = 0;
        try {
            // 根据传递过来的文件输入流创建一个workbook对象（对应Excel中的工作簿）
            workbook = WorkbookFactory.create(inputStream);
            // 创建完成，关闭输入流
            inputStream.close();
            index = workbook.getNumberOfSheets();
            for (int i = 0; i < index; i++) {
                List<Map<String, Object>> list = new ArrayList<>();
                //获取工作表对象，即第一个工作表 （工作簿里面有很多张工作表，这里取第一张工作表）
                Sheet sheet = workbook.getSheetAt(i);
                //获取工作表的总行数
                int rowLength = sheet.getLastRowNum() + 1;
                Map<String, Object> map;
                //获取工作表第一行数据
                Row row = sheet.getRow(0);
                //获取工作表总列数的长度
                int colLength = row.getLastCellNum();
                // 创建一个单元格对象
                Cell cell = null;
                // 双重循环 因为一个单元格由行和列的索引下表构成
                for (int a = 2; a < rowLength; a++) {
                    if (!isRowEmpty(sheet.getRow(a))) {
                        throw new CustomException("0", "在第" + (a + 1) + "行,有空行信息,请检查格式是否正确!");
                    }
                    // 创建一个学生实体类对象
                    map = new HashMap<>();
                    for (int b = 0; b < colLength; b++) {
                        cell = sheet.getRow(a).getCell(b);
                        // 分别取出第 a行 b列的单元格数据
                        // 设置单元格的类型是String类型
                        String stringCellValue = null;
                        if (cell == null || cell.toString().equals("")) {
                            stringCellValue = "";
                        } else {
                            stringCellValue = getString(cell);

                        }
                        /*if (cell != null) {
                            stringCellValue = getString(cell);
                        }*/
                        if (StringUtils.isNotEmpty(stringCellValue) && StringUtils.isNotBlank(stringCellValue) && !"null".equals(stringCellValue)) {
                            stringCellValue = stringCellValue.trim().replaceAll(" ", "");
                            map.put(String.valueOf(b), stringCellValue);
                        }
                    }
                    if (map != null && !map.isEmpty()) {
                        list.add(a - 2, map);
                    }
                }
                sheets.add(list);
            }

        } catch (MyExcelException | IOException e) {
            e.printStackTrace();
            LOGGER.error("parse excel file error :", e);
        }

        return sheets;
    }


    /**
     * 获取表格数据,去除表格为空判断
     *
     * @param inputStream
     * @return
     */
    public static List<List<Map<String, Object>>> excelToShopIdList1(InputStream inputStream) {
        List<List<Map<String, Object>>> sheets = new ArrayList<>();
        Workbook workbook = null;
        //读取多个sheet
        int index = 0;
        try {
            // 根据传递过来的文件输入流创建一个workbook对象（对应Excel中的工作簿）
            workbook = WorkbookFactory.create(inputStream);
            // 创建完成，关闭输入流
            inputStream.close();
            index = workbook.getNumberOfSheets();
            for (int i = 0; i < index; i++) {
                List<Map<String, Object>> list = new ArrayList<>();
                //获取工作表对象，即第一个工作表 （工作簿里面有很多张工作表，这里取第一张工作表）
                Sheet sheet = workbook.getSheetAt(i);
                //获取工作表的总行数
                int rowLength = sheet.getLastRowNum() + 1;
                Map<String, Object> map;
                //获取工作表第一行数据
                Row row = sheet.getRow(0);
                int colLength = 0;
                //获取工作表总列数的长度
                if (row != null) {
                    colLength = row.getLastCellNum();
                }
                // 创建一个单元格对象
                Cell cell = null;
                // 双重循环 因为一个单元格由行和列的索引下表构成
                for (int a = 0; a < rowLength; a++) {
                    // 创建一个实体类对象
                    map = new HashMap<>();
                    for (int b = 0; b < colLength; b++) {
                        cell = sheet.getRow(a).getCell(b);
                        // 分别取出第 a行 b列的单元格数据
                        // 设置单元格的类型是String类型
                        String stringCellValue = null;
                        if (cell == null || cell.toString().equals("")) {
                            stringCellValue = "";
                        } else {
                            stringCellValue = getString(cell);
                        }

                        if (StringUtils.isNotEmpty(stringCellValue) && StringUtils.isNotBlank(stringCellValue) && !"null".equals(stringCellValue)) {
                            stringCellValue = stringCellValue.trim().replaceAll(" ", "");
                            map.put(String.valueOf(b), stringCellValue);
                        }
                    }
                    if (map != null && !map.isEmpty()) {
                        list.add(a, map);
                    }
                }
                sheets.add(list);
            }
        } catch (MyExcelException | IOException e) {
            e.printStackTrace();
            LOGGER.error("parse excel file error :", e);
        }
        return sheets;
    }

    /**
     * 判断 行是否为空
     *
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        if (null == row) {
            return false;
        }
        //第一个列位置
        int firstCellNum = row.getFirstCellNum();
        //最后一列位置
        int lastCellNum = row.getLastCellNum();
        //空列数量
        int nullCellNum = 0;
        for (int c = firstCellNum; c < lastCellNum; c++) {
            Cell cell = row.getCell(c);
            String cellValue;
            if(cell == null){
                break;
            }
            if (cell.toString().equals("")) {
                cellValue = "";
            } else {
                cellValue = getString(cell);
            }

            if (StringUtils.isEmpty(cellValue)) {
                nullCellNum++;
            }
        }
        //所有列都为空
        if (nullCellNum == (lastCellNum - firstCellNum)) {
            return false;
        }
        return true;
    }

    //判断excel中数据的类型
    public static String getString(Cell cell) {
        String cellValue = "";
        //判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: //数字
                cellValue = realStringValueOfDouble(cell.getNumericCellValue());
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: //空值
            case ERROR: //故障
                break;
            default:
                break;
        }
        return cellValue.trim();
    }

    /*
    double转string
     */
    public static String realStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        boolean b = doubleStr.contains("E");
        int indexOfPoint = doubleStr.indexOf('.');
        if (b) {
            int indexOfE = doubleStr.indexOf('E');
            BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint + BigInteger.ONE.intValue(), indexOfE));
            int pow = Integer.parseInt(doubleStr.substring(indexOfE + BigInteger.ONE.intValue()));
            int xsLen = xs.toByteArray().length;
            int scale = xsLen - pow > 0 ? xsLen - pow : 0;
            final String format = "%." + scale + "f";
            doubleStr = String.format(format, d);
        } else {
            java.util.regex.Pattern p = Pattern.compile(".0$");
            java.util.regex.Matcher m = p.matcher(doubleStr);
            if (m.find()) {
                doubleStr = doubleStr.replace(".0", "");
            }
        }
        return doubleStr;
    }

    /**
     * 获取表头信息
     *
     * @param filePath    文件名
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static List<String> getExcelHeaders(String filePath, InputStream inputStream) throws Exception {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        //获取 excel 第一行数据（表头）
        Row row = sheet.getRow(0);
        //存放表头信息
        List<String> list = new ArrayList<>();
        //算下有多少列
        int colCount = sheet.getRow(0).getLastCellNum();
        for (int j = 0; j < colCount; j++) {
            Cell cell = row.getCell(j);
            String cellValue = cell.getStringCellValue().trim();
            list.add(cellValue);
        }
        return list;
    }


    /**
     * 将MultipartFile转换为File
     *
     * @param multiFile
     * @return
     */
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        //若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

