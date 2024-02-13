package com.example.report_generation.report_generation.service;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lowagie.text.pdf.BaseFont;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
@Service
public class PDFGeneratorService {
    String pdfFilePath = "src\\main\\resources\\pdf\\";
    String userFilePath = "src\\main\\resources\\json\\ImportantUser.json";
    @Autowired 
    UserService _userService;
    public void downloadDocument(HttpServletResponse response,User user) throws IOException{
        // Resource resource = new ClassPathResource("report_generation\\src\\main\\resources\\pdf\\pdf.pdf");
        // InputStream inputStream = resource.getInputStream();
        
    try {
            PdfReader reader = new PdfReader(pdfFilePath + user.getId() + ".pdf");
            int numberOfPages = reader.getNumberOfPages();
            Document document = new Document();
            // Replace "output.pdf" with the path for your output PDF file
            PdfCopy copy = new PdfCopy(document, response.getOutputStream());
            document.open();
            for (int i = 1; i <= numberOfPages; i++) {
                copy.addPage(copy.getImportedPage(reader, i));
            }
            document.close();
            reader.close();
            System.out.println("Existing PDF content copied successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    Important for download
    
    */
    public void generateDocumentInServer(Map<String, Object> body) throws IOException {
        // get user by id
        User user = _userService.getUserById(body.get("userId").toString(), userFilePath);

        // generate the actual document
        Document document = new Document(PageSize.A4);
        PdfWriter writer = getWriter(document, user);

        document.open();

        // Title
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph title = new Paragraph("Screening Test Report", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String currentDate = dateFormat.format(user.getData().get(0).getDate());
        Paragraph dateParagraph = new Paragraph("Date: " + currentDate);
        dateParagraph.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(dateParagraph);

        // Result report for
        Paragraph resultReportFor = new Paragraph("Result report for: " + "amremad@gmail.com");
        resultReportFor.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(resultReportFor);

        // Add a line break
        document.add(new Paragraph("\n"));

        // Main Paragraph
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
        String mainText = "The following report presents the outcomes of the dyslexia screening test, designed to identify potential challenges in reading, spelling, and memory for children aged 5 to 11. It is important to note that this screening is not a diagnostic tool but serves as an initial step in creating a tailored educational environment within a language leading learning system.";
        Paragraph mainParagraph = new Paragraph(mainText, fontParagraph);
        mainParagraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        mainParagraph.setSpacingAfter(10f); // Adding space after the paragraph
        document.add(mainParagraph);

        document.close();
    }

    public PdfWriter getWriter(Document document,User user) throws DocumentException, FileNotFoundException{
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(pdfFilePath + user.getId() + ".pdf"));
        writer.setPageEvent(new PdfPageEvent() {
            @Override
            public void onStartPage(PdfWriter writer, Document document) {
                // Set the background color for each page
                writer.getDirectContentUnder().setRGBColorFill(205, 235, 197);
                writer.getDirectContentUnder().rectangle(0, 0, PageSize.A4.getWidth(), PageSize.A4.getHeight());
                writer.getDirectContentUnder().fill();
            }

            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                // You can add other content to the page if needed
            }

            @Override
            public void onCloseDocument(PdfWriter writer, Document document) {
                // Close any resources if needed
            }

            @Override
            public void onOpenDocument(PdfWriter writer, Document document) {
                // TODO Auto-generated method stub
                System.out.println("Document is being opened");
            }

            @Override
            public void onParagraph(PdfWriter writer, Document document, float paragraphPosition) {
                // TODO Auto-generated method stub
                System.out.println("Document is being opened");
            }

            @Override
            public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition) {
                // TODO Auto-generated method stub
                System.out.println("Document is being opened");
            }

            @Override
            public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {
                // TODO Auto-generated method stub
                System.out.println("Document is being opened");
            }

            @Override
            public void onChapterEnd(PdfWriter writer, Document document, float paragraphPosition) {
                // TODO Auto-generated method stub
                System.out.println("Document is being opened");
            }

            @Override
            public void onSection(PdfWriter writer, Document document, float paragraphPosition, int depth,
                    Paragraph title) {
                // TODO Auto-generated method stub
                System.out.println("Document is being opened");
            }

            @Override
            public void onSectionEnd(PdfWriter writer, Document document, float paragraphPosition) {
                // TODO Auto-generated method stub
                System.out.println("Document is being opened");
            }

            @Override
            public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {
                // TODO Auto-generated method stub
                System.out.println("Document is being opened");
            }
        });
        return writer;
    }
    
}
