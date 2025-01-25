package com.example.home_accountant.service;

import com.example.home_accountant.model.Expense;
import com.example.home_accountant.model.User;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateExpenseReport(List<Expense> expenses, User user, double totalAmount) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            document.add(new Paragraph("Expense Report")
                    .setBold()
                    .setFontSize(20)
                    .setMarginBottom(10));

            document.add(new Paragraph("User: " + user.getEmail())
                    .setFontSize(12)
                    .setMarginBottom(10));

            Table table = new Table(new float[]{2, 2, 2, 4});
            table.addHeaderCell("Date");
            table.addHeaderCell("Category");
            table.addHeaderCell("Amount");
            table.addHeaderCell("Note");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Expense expense : expenses) {
                table.addCell(expense.getDate().format(formatter));
                table.addCell(expense.getCategory());
                table.addCell(String.valueOf(expense.getAmount()));
                table.addCell(expense.getNote() != null ? expense.getNote() : "");
            }

            document.add(table);

            document.add(new Paragraph("Total Amount: " + totalAmount)
                    .setBold()
                    .setFontSize(14)
                    .setMarginTop(10));

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }
}
