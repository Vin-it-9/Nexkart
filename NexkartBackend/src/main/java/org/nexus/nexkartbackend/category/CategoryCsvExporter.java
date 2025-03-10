package org.nexus.nexkartbackend.category;


import java.io.IOException;
import java.util.List;

import org.nexus.nexkartbackend.Exporter.AbstractExporter;
import org.nexus.nexkartbackend.entity.Category;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


public class CategoryCsvExporter extends AbstractExporter {
    public void export(List<Category> listCategories, jakarta.servlet.http.HttpServletResponse response)
            throws IOException {

        super.setResponseHeader(response, "text/csv", ".csv", "categories_");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Category ID", "Category Name"};
        String[] fieldMapping = {"id", "name"};

        csvWriter.writeHeader(csvHeader);

        for (Category category : listCategories) {
            category.setName(category.getName().replace("--", "  "));
            csvWriter.write(category, fieldMapping);
        }

        csvWriter.close();
    }
}
