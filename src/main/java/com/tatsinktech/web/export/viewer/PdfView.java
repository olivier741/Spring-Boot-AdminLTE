package com.tatsinktech.web.export.viewer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tatsinktech.web.model.register.Register;
import java.lang.reflect.Field;
import org.apache.poi.ss.usermodel.Row;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");

        Field[] fields = Register.class.getDeclaredFields();

        int sizeHead = fields.length + 4;

        List<Register> listReg = (List<Register>) model.get("registers");

        document.add(new Paragraph("Generated Register " + LocalDate.now()));

        PdfPTable table = new PdfPTable(sizeHead);
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setPadding(5);

        int i = 1;
        for (Field field : fields) {
            // write table header
            cell.setPhrase(new Phrase(field.getName(), font));
            table.addCell(cell);
            i++;
        }

        // write table header
        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("CreateTime", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("UpdateTime", font));
        table.addCell(cell);


  for (Register reg : listReg) {
            table.addCell(reg.getId().toString());
            table.addCell(reg.getTransactionId());
            table.addCell(reg.getMsisdn());
            table.addCell(String.valueOf(reg.isAutoextend()));
            table.addCell(reg.getRegTime().toString());
            table.addCell(reg.getRenewTime().toString());
            table.addCell(reg.getExpireTime().toString());
            table.addCell(reg.getUnregTime().toString());  
            table.addCell(reg.getCancelTime().toString());
            table.addCell(String.valueOf(reg.getNumberReg()));
            table.addCell(reg.getProduct().getProductCode());
            table.addCell(reg.getDescription());
            table.addCell(reg.getCreateTime().toString());
            table.addCell(reg.getUpdateTime().toString());       
        }
        document.add(table);

    }
}
