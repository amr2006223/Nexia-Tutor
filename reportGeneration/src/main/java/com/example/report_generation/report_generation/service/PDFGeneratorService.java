package com.example.report_generation.report_generation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.awt.Color;
import java.io.IOException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;


import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import jakarta.servlet.http.HttpServletResponse;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.repository.UserRepository;



@Service
public class PDFGeneratorService {
    private final String pdfFilePath = "src\\main\\resources\\pdf\\";
    @Autowired
    private PDFUtils pdfUtils;
    @Autowired
    private UserRepository userRepository;

    public void downloadDocument(HttpServletResponse response, User user) throws IOException {
        String userPdfPath = getUserPdfPath(user);
        PdfReader reader = null;
        Document document = null;

        try {
            reader = new PdfReader(userPdfPath);
            document = new Document();
            PdfCopy copy = new PdfCopy(document, response.getOutputStream());
            document.open();
            copyPages(copy, reader);

            System.out.println("Existing PDF content copied successfully!");
        } catch (IOException e) {
            handleException(response, e);
        } finally {
            closeResources(reader, document);
        }
    }

    public boolean generateDocumentInServer(User user) throws IOException {
        // User user = _userService.getUserById(id, userFilePath);
        if (user == null) {
            return false;
        }
        try {
            User databaseUser = userRepository.findById(user.getId()).orElse(null);
            user.setUsername(databaseUser.getUsername());
            writeDocument(user);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    

    private void writeDocument(User user) throws DocumentException, FileNotFoundException {
        String currentDate = pdfUtils.formatUserDate(user);
        String result = pdfUtils.determineUserResult(user);

        Document document = pdfUtils.createDocument();
        PdfWriter writer = openPDFWriter(document, user.getId(), currentDate);

        document.open();
        pdfUtils.addTitle(document);
        pdfUtils.addDate(document, currentDate);
        pdfUtils.addReportFor(document, user.getUsername());
        pdfUtils.addResult(document, result);
        pdfUtils.addMainParagraph(document);
        pdfUtils.addCategoryDiv(document, user);
        pdfUtils.addResultAndRecommendation(document);
        pdfUtils.addUnexpectedResult(document);

        pdfUtils.closeResources(document, writer);
    }

    

    private PdfWriter openPDFWriter(Document document, String id, String date)
            throws DocumentException, FileNotFoundException {
        File directory = new File(pdfFilePath + "\\" + id);
        if (!directory.exists()) {
            directory.mkdirs(); // Make directories recursively
        }
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(pdfFilePath + "\\" + id + "\\" + date + ".pdf"));
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onStartPage(PdfWriter writer, Document document) {
                PdfContentByte canvas = writer.getDirectContentUnder();
                canvas.setColorFill(new Color(205, 235, 197));
                canvas.rectangle(0, 0, PageSize.A4.getWidth(), PageSize.A4.getHeight());
                canvas.fill();
            }
        });
        return writer;
    }
    

    
    private String getUserPdfPath(User user) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy HHmm");
        String formattedDate = dateFormat.format(user.getData().get(0).getDate());
        return this.pdfFilePath + "/" + user.getId() + "/" + formattedDate + ".pdf";
    }

    private void copyPages(PdfCopy copy, PdfReader reader) throws IOException {
        int numberOfPages = reader.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            copy.addPage(copy.getImportedPage(reader, i));
        }
    }

    private void handleException(HttpServletResponse response, IOException e) throws IOException {
        System.err.println("Error downloading document: " + e.getMessage());
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading document");
    }

    private void closeResources(PdfReader reader, Document document) {
        closeResource(reader);
        closeResource(document);
    }

    private void closeResource(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                System.err.println("Error closing resource: " + e.getMessage());
            }
        }
    }
}
