package com.tatsinktech.web.export.viewer;


import com.tatsinktech.web.model.register.Register;
import java.lang.reflect.Field;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

public class ExcelView extends AbstractXlsxView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,HttpServletRequest request,HttpServletResponse response) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xlsx\"");

        @SuppressWarnings("unchecked")
        List<Register> listReg = (List<Register>) model.get("registers");
        
        Field[] fields = Register.class.getDeclaredFields();
        int sizeHead = fields.length + 4;
        
          Sheet sheet = workbook.createSheet("Registes");

        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex);

        for (int in = 0; in < sizeHead; in++) {
            sheet.setColumnWidth(in, 2500);
        }

        CellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);

        Cell cell = row.createCell(0);
        cell.setCellValue("#");
        cell.setCellStyle(style);

        int i = 1;
        for (Field field : fields) {
            cell = row.createCell(i);
            cell.setCellValue(field.getName());
            cell.setCellStyle(style);
            i++;
        }

        cell = row.createCell(i);
        cell.setCellValue("Description");
        cell.setCellStyle(style);

        cell = row.createCell(i + 1);
        cell.setCellValue("createTime");
        cell.setCellStyle(style);

        cell = row.createCell(i + 2);
        cell.setCellValue("updateTime");
        cell.setCellStyle(style);

        for (Register reg : listReg) {

            rowIndex++;
            row = sheet.createRow(rowIndex);

            cell = row.createCell(0);
            cell.setCellValue(reg.getId().toString());
            
            cell = row.createCell(1);
            cell.setCellValue(reg.getTransactionId());
            
            cell = row.createCell(2);
            cell.setCellValue(reg.getMsisdn());
            
            cell = row.createCell(3);
            cell.setCellValue(String.valueOf(reg.isAutoextend()));
            
            cell = row.createCell(4);
            cell.setCellValue(reg.getRegTime().toString());
            
            cell = row.createCell(5);
            cell.setCellValue(reg.getRenewTime().toString());
            
            cell = row.createCell(6);
            cell.setCellValue(reg.getExpireTime().toString());
            
            cell = row.createCell(7);
            cell.setCellValue(reg.getUnregTime().toString());
            
            cell = row.createCell(8);
            cell.setCellValue(reg.getCancelTime().toString());
            
            cell = row.createCell(9);
            cell.setCellValue(String.valueOf(reg.getNumberReg()));
            
            cell = row.createCell(10);
            cell.setCellValue(reg.getProduct().getProductCode());
            
            cell = row.createCell(11);
            cell.setCellValue(reg.getDescription());
            
            cell = row.createCell(12);
            cell.setCellValue(reg.getCreateTime().toString());
            
            cell = row.createCell(13);
            cell.setCellValue(reg.getUpdateTime().toString());

        }
        
       

    }

}
