package org.nexus.nexkartbackend.Exporter;


import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.nexus.nexkartbackend.entity.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserPdfExporter extends AbstractExporter {

    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        ServletOutputStream outputStream = response.getOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Font fontTitle = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("List of Users", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.0f, 3.5f, 1.5f });

            writeTableHeader(table);
            writeTableData(table, listUsers);

            document.add(table);
        } catch (DocumentException e) {
            throw new IOException(e);
        } finally {
            document.close();
            outputStream.close();
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        Font font = new Font(Font.HELVETICA, 12, Font.BOLD);

        cell.setPhrase(new Phrase("User ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Roles", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Enabled", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<User> listUsers) {
        Font font = new Font(Font.HELVETICA, 12);

        for (User user : listUsers) {
            table.addCell(new Phrase(String.valueOf(user.getId()), font));
            table.addCell(new Phrase(user.getEmail(), font));
            table.addCell(new Phrase(user.getFirstName(), font));
            table.addCell(new Phrase(user.getLastName(), font));
            table.addCell(new Phrase(user.getRoles().toString(), font));
            table.addCell(new Phrase(String.valueOf(user.isEnabled()), font));
        }
    }
}