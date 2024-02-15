package com.example.report_generation.report_generation.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.report_generation.report_generation.models.User;
import com.lowagie.text.Chunk;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import com.lowagie.text.Element;

import java.awt.Color;

@Service
public class PDFGeneratorService {
    String pdfFilePath = "reportGeneration\\src\\main\\resources\\pdf\\";
    String userFilePath = "reportGeneration\\src\\main\\resources\\json\\ImportantUser.json";
    @Autowired
    UserService _userService;

    public void downloadDocument(HttpServletResponse response, User user) throws IOException {
        // Resource resource = new
        // ClassPathResource("report_generation\\src\\main\\resources\\pdf\\pdf.pdf");
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
     * Important for download
     * 
     */
    public void generateDocumentInServer(String id) throws IOException {
        // get user by id
        User user = _userService.getUserById(id, userFilePath);

        // generate the actual document
        Document document = new Document(PageSize.A4);
        PdfWriter writer = getWriter(document, user);

        document.open();

        // Title
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setColor(new Color(0x3E4772));
        fontTitle.setSize(18);
        Paragraph title = new Paragraph("Screening Test Report", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Font fontDate = FontFactory.getFont(FontFactory.HELVETICA);
        fontDate.setColor(new Color(0x3E4772));
        String currentDate = dateFormat.format(user.getData().get(0).getDate());
        Paragraph dateParagraph = new Paragraph("Date: " + currentDate, fontDate);
        dateParagraph.setAlignment(Paragraph.ALIGN_CENTER); // Align center
        document.add(dateParagraph);

        // Result report for
        Font fontReport = FontFactory.getFont(FontFactory.HELVETICA);
        fontReport.setColor(new Color(0x3E4772));
        Paragraph resultReportFor = new Paragraph("Result report for: " + "amremad@gmail.com", fontReport);
        resultReportFor.setAlignment(Paragraph.ALIGN_CENTER); // Align center
        document.add(resultReportFor);

        // Add a line break
        document.add(new Paragraph("\n"));

        // Main Paragraph
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
        fontParagraph.setColor(new Color(0x3E4772)); // Set the color to #3E4772
        String mainText = "The following report presents the outcomes of the dyslexia screening test, designed to identify potential challenges in reading, spelling, and memory for children aged 5 to 11. It is important to note that this screening is not a diagnostic tool but serves as an initial step in creating a tailored educational environment within a language leading learning system.";
        Paragraph mainParagraph = new Paragraph(mainText, fontParagraph);
        mainParagraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        mainParagraph.setSpacingAfter(10f); // Adding space after the paragraph
        document.add(mainParagraph);

        // Adding a styled "div"
        PdfPTable styledDiv = new PdfPTable(1);
        styledDiv.setWidthPercentage(100);
        styledDiv.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        styledDiv.setSpacingBefore(16);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(16);
        cell.setBackgroundColor(new Color(0xE3FFDC));
        cell.setBorder(Rectangle.NO_BORDER);

        // Create a list for bullet points
        List list = new List(List.UNORDERED);
        // list.setListSymbol(" ");
        list.setListSymbol("\u2022"); // Use Unicode character for bullet point

        // Customize the bullet point size and indentation
        list.setIndentationLeft(10); // Adjust the indentation as needed

        Font fontListItem = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.RED);
        // Add items to the list with the word "red" beside each bullet point
        list.add(new ListItem(new Chunk("  Alphabetic Awarness", fontListItem)));
        list.add(new ListItem(new Chunk("  Phonological awareness", fontListItem)));
        list.add(new ListItem(new Chunk("  Visual Awarness", fontListItem)));
        // Add more items as needed

        // Add the list to the cell
        cell.addElement(list);

        styledDiv.addCell(cell);
        document.add(styledDiv);
        // Add a paragraph with the additional content
        Paragraph additionalParagraph = new Paragraph();
        additionalParagraph.setSpacingBefore(16); // Adjust spacing before the paragraph
        additionalParagraph.setAlignment(Element.ALIGN_CENTER); // Align center
        additionalParagraph.add(new Chunk("Results and Recommendations:",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(0x3E4772)))); // Set color and bold
        additionalParagraph.add("\n\n");

        String additionalText = "Upon completion of the screening test, if a child is identified at risk, it is advisable to seek professional assistance. Referrals can be made to educational center experts or external health professionals in specialized centers. For those located in Egypt, we can provide recommendations for suitable services.\n\n"
                + "Cognitive skills can be stimulated through daily games and activities focusing on reading, memory, and spelling. Our personalized language learning service complements these efforts, contributing to a holistic educational approach.\n\n";

        additionalParagraph
                .add(new Chunk(additionalText, FontFactory.getFont(FontFactory.HELVETICA, 12, new Color(0x3E4772))));

        document.add(additionalParagraph); // Add the additional paragraph
        Paragraph additionalParagraph2 = new Paragraph();
        additionalParagraph2.setSpacingBefore(16); // Adjust spacing before the paragraph
        additionalParagraph2.setAlignment(Element.ALIGN_CENTER); // Align center
        additionalParagraph2.add(new Chunk("Unexpected Results:\n\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(0x3E4772)))); // Set color and bold
        String additionalText2 = "If the screening outcome differs from expectations, particularly if a child is already diagnosed with dyslexia, we encourage contacting us at nexiaTeam@gmail.com. Your input is crucial for our ongoing study to understand and address potential discrepancies, aiding in the continuous improvement of the Nexia Tutor.\n\n"
                + "The dyslexia screening test is an initial assessment tool and does not offer a diagnosis. Only professionals can provide accurate diagnoses. The test should be conducted only once for accurate results and should not be repeated to evaluate the user. The test is specifically designed for the English language, and its accuracy may vary for other languages using Latin letters. The system is focused on language learning aspects such as spelling, writing, reading, and working memory and may not be applicable to other disorders like ADHD or dyscalculia.";
        additionalParagraph2
                .add(new Chunk(additionalText2, FontFactory.getFont(FontFactory.HELVETICA, 12, new Color(0x3E4772))));
        document.add(additionalParagraph2);
        document.close();
        writer.close();
    }

    public PdfWriter getWriter(Document document, User user) throws DocumentException, FileNotFoundException {
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
