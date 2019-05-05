package com.example.demo.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.demo.kafka.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.List;

/**
 * @ClassName ExcelWriterFactory
 * @Description
 * @Author jackson
 * @Date 2019/5/5 11:04
 * @Version 1.0
 **/
public class ExcelWriterFactory extends ExcelWriter {

    private static final Logger logger = LoggerFactory.getLogger(ExcelWriterFactory.class);

    private OutputStream outputStream;
    private int sheetNo = 1;

    public ExcelWriterFactory(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        super(outputStream,typeEnum);
        this.outputStream = outputStream;
    }

    public ExcelWriterFactory write(List<? extends BaseRowModel> list, String sheetName,
                                    Class<? extends BaseRowModel> clazz) {
        this.sheetNo++;
        try{
            Sheet sheet = new Sheet(sheetNo,0,clazz);
            sheet.setSheetName(sheetName);
            this.write(list,sheet);
        } catch(Exception e) {
            logger.error("write excel with sheets error:{}",e);
            try{
                outputStream.flush();
            } catch(Exception ex) {
                logger.error("close outputstream error:{}",ex);
            }
        }
        return this;
    }

    @Override
    public void finish() {
        super.finish();
        try{
            outputStream.flush();
        } catch(Exception e) {
            logger.error("close outputstream error:{}",e);
        }
    }
}
