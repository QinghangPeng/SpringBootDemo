package com.example.demo.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName ExcelExportUtil 利用alibaba的easyexcel导出excel
 * @Description
 * @Author jackson
 * @Date 2019/5/5 10:31
 * @Version 1.0
 **/
public class ExcelExportUtil {

    /**
     *  导出excel 一个sheet表
     * @param response  HttpServletResponse
     * @param list 数据list
     * @param title 导出文件名
     * @param sheetName 导出文件的sheet名
     * @param clazz 映射实体类
     * @throws IOException
     */
    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list, String title,
                                  String sheetName,Class<? extends BaseRowModel> clazz) throws IOException {
        String fileName = URLEncoder.encode(title, "UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        ExcelWriter writer = new ExcelWriter(response.getOutputStream(),ExcelTypeEnum.XLS);
        Sheet sheet = new Sheet(1,0,clazz);
        sheet.setSheetName(sheetName);
        writer.write(list,sheet);
        writer.finish();
    }

    /**
     *  导出excel 多个sheet表
     * @param response HttpServletResponse
     * @param list 数据list
     * @param title 导出文件名
     * @param sheetName 导出文件的sheet名
     * @param clazz 映射实体类
     * @return
     * @throws IOException
     */
    public static ExcelWriterFactory writeExcelWithSheets(HttpServletResponse response, List<? extends BaseRowModel> list, String title,
                                            String sheetName,Class<? extends BaseRowModel> clazz) throws IOException{
        String fileName = URLEncoder.encode(title, "UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        ExcelWriterFactory writer = new ExcelWriterFactory(response.getOutputStream(),ExcelTypeEnum.XLS);
        Sheet sheet = new Sheet(1,0,clazz);
        sheet.setSheetName(sheetName);
        writer.write(list,sheet);
        return writer;
    }
}
