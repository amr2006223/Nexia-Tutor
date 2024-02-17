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
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfPageEventHelper;
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

    public boolean generateDocumentInServer(String id) throws IOException {
        User user = _userService.getUserById(id, userFilePath);
        if (user == null)
            return false;
        try {

            Document document = new Document(PageSize.A4);
            PdfWriter writer = writePdf(document, user.getId());
            document.open();
            // Title
            document.add(generateParagraph(FontFactory.HELVETICA_BOLD, new Color(0x3E4772),
                    18.0f, "Screening Test Report", Paragraph.ALIGN_CENTER));
            // Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            String currentDate = dateFormat.format(user.getData().get(0).getDate());
            document.add(generateParagraph(FontFactory.HELVETICA, new Color(0x3E4772), 12, "Date: " + currentDate,
                    Paragraph.ALIGN_CENTER));
            // Result report for
            document.add(generateParagraph(FontFactory.HELVETICA, new Color(0x3E4772), 12,
                    "Result report for: " + "amremad@gmail.com", Paragraph.ALIGN_CENTER));
            // Add a line break
            document.add(new Paragraph("\n"));
            // Main Paragraph
            String mainText = "The following report presents the outcomes of the dyslexia screening test, designed to identify potential challenges in reading, spelling, and memory for children aged 5 to 11. It is important to note that this screening is not a diagnostic tool but serves as an initial step in creating a tailored educational environment within a language leading learning system.";
            Paragraph mainparagraph = generateParagraph(FontFactory.HELVETICA, new Color(0x3E4772), 12, mainText,
                    Paragraph.ALIGN_JUSTIFIED, 10f,0f);
            document.add(mainparagraph);
            // Adding a styled "div"
            document.add(generateCustomDiv());

            // Add a paragraph with the additional content
            Paragraph additionalParagraph = new Paragraph();
            additionalParagraph.setSpacingBefore(16); // Adjust spacing before the paragraph
            additionalParagraph.setAlignment(Element.ALIGN_CENTER); // Align center
            additionalParagraph.add(new Chunk("Results and Recommendations:",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(0x3E4772)))); // Set color and bold
            additionalParagraph.add("\n\n");

            String additionalText = "Upon completion of the screening test, if a child is identified at risk, it is advisable to seek professional assistance. Referrals can be made to educational center experts or external health professionals in specialized centers. For those located in Egypt, we can provide recommendations for suitable services.\n\n"
                    + "Cognitive skills can be stimulated through daily games and activities focusing on reading, memory, and spelling. Our personalized language learning service complements these efforts, contributing to a holistic educational approach.\n\n";

            additionalParagraph.add(new Chunk(additionalText,
                            FontFactory.getFont(FontFactory.HELVETICA, 12, new Color(0x3E4772))));

            document.add(additionalParagraph); // Add the additional paragraph
            Paragraph additionalParagraph2 = new Paragraph();
            additionalParagraph2.setSpacingBefore(16); // Adjust spacing before the paragraph
            additionalParagraph2.setAlignment(Element.ALIGN_CENTER); // Align center
            additionalParagraph2.add(new Chunk("Unexpected Results:\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(0x3E4772)))); // Set color and bold
            String additionalText2 = "If the screening outcome differs from expectations, particularly if a child is already diagnosed with dyslexia, we encourage contacting us at nexiaTeam@gmail.com. Your input is crucial for our ongoing study to understand and address potential discrepancies, aiding in the continuous improvement of the Nexia Tutor.\n\n"
                    + "The dyslexia screening test is an initial assessment tool and does not offer a diagnosis. Only professionals can provide accurate diagnoses. The test should be conducted only once for accurate results and should not be repeated to evaluate the user. The test is specifically designed for the English language, and its accuracy may vary for other languages using Latin letters. The system is focused on language learning aspects such as spelling, writing, reading, and working memory and may not be applicable to other disorders like ADHD or dyscalculia.";
            additionalParagraph2
                    .add(new Chunk(additionalText2,
                            FontFactory.getFont(FontFactory.HELVETICA, 12, new Color(0x3E4772))));
            document.add(additionalParagraph2);
            document.close();
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private PdfWriter writePdf(Document document, String id) throws DocumentException, FileNotFoundException {

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath + id + ".pdf"));
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onStartPage(PdfWriter writer, Document document) {
                PdfContentByte canvas = writer.getDirectContentUnder();
                canvas.setColorFill(new GrayColor(0.8f));
                canvas.rectangle(0, 0, PageSize.A4.getWidth(), PageSize.A4.getHeight());
                canvas.fill();
            }
        });
        return writer;
    }

    private PdfPTable generateCustomDiv() {
        PdfPTable styledDiv = new PdfPTable(1);
        styledDiv.setWidthPercentage(100);
        styledDiv.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        styledDiv.setSpacingBefore(16);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(16);
        cell.setBackgroundColor(new Color(0xE3FFDC));
        cell.setBorder(Rectangle.NO_BORDER);

        // create a list
        List list = generateList();

        // Add the list to the cell
        cell.addElement(list);

        styledDiv.addCell(cell);
        return styledDiv;
    }

    private List generateList() {
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
        return list;
    }

    private Paragraph generateParagraph(String fontName, Color color, float size, String paragraphText, int alignment,
            float spacingAfter, float spacingBefore) {
        Font font = FontFactory.getFont(fontName);
        font.setColor(color);
        font.setSize(size);
        Paragraph paragraph = new Paragraph(paragraphText, font);
        paragraph.setSpacingBefore(spacingBefore);
        paragraph.setAlignment(alignment);
        paragraph.setSpacingAfter(spacingAfter);
        return paragraph;
    }

    private Paragraph generateParagraph(String fontName, Color color, float size, String paragraphText, int alignment) {
        return generateParagraph(fontName, color, size, paragraphText, alignment, 0f,0f);
    }
}
