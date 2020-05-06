package com.tatsinktech.web.export.viewer;

import com.tatsinktech.web.model.register.Register;
import java.lang.reflect.Field;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CsvView extends AbstractCsvView {

    @Override
    protected void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");

        Field[] fields = Register.class.getDeclaredFields();

        int sizeHead = fields.length + 4;

        List<Register> listReg = (List<Register>) model.get("registers");

        CsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] header = new String[sizeHead];
        header[0] = "id";
        int i = 1;
        for (Field field : fields) {
            header[i] = field.getName();
            i++;
        }
        header[i] = "description";
        header[i + 1] = "createTime";
        header[i + 2] = "updateTime";

        beanWriter.writeHeader(header);

        for (Register reg : listReg) {

            beanWriter.write(reg, header);
        }

        beanWriter.close();

    }
}
