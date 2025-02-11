package rw.ac.rca.submission.onlinesubmission.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import rw.ac.rca.submission.onlinesubmission.models.Assignment;
import rw.ac.rca.submission.onlinesubmission.models.Submission;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class ReportService {

    public ByteArrayOutputStream generateAssignmentReport(Assignment assignment) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Assignment Report: " + assignment.getTitle(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Details
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Course Code: " + assignment.getCourseCode()));
        document.add(new Paragraph("Due Date: " + assignment.getDueDate().toString()));
        document.add(new Paragraph("Total Submissions: " + assignment.getSubmissions().size()));

        // Submissions Table
        PdfPTable table = new PdfPTable(3);
        table.addCell("Student Name");
        table.addCell("Submission Date");
        table.addCell("File Name");

        for (Submission submission : assignment.getSubmissions()) {
            table.addCell(submission.getStudent().getFirstName() + " " + submission.getStudent().getLastName());
            table.addCell(submission.getSubmissionDate().toString());
            table.addCell(submission.getFileName());
        }

        document.add(table);
        document.close();

        return outputStream;
    }
}