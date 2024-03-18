package com.example.report_generation.report_generation.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.utils.Constants;
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
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
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
    Color defaultColor = new Color(0x3E4772);
    String defalutFont = FontFactory.HELVETICA;
    String defaultTitleFont = FontFactory.HELVETICA_BOLD;
    @Autowired
    UserService _userService;

    public void downloadDocument(HttpServletResponse response, User user) throws IOException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy HHmm");
            String currentDate = dateFormat.format(user.getData().get(0).getDate());
            PdfReader reader = new PdfReader(pdfFilePath +"\\" +  user.getId() +"\\"+ currentDate + ".pdf");
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

    public boolean generateDocumentInServer(User user) throws IOException {
        // User user = _userService.getUserById(id, userFilePath);
        if (user == null){
            return false;
        }
        try {
            writeDocument(user);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private void writeDocument(User user) throws DocumentException, FileNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy HHmm");
        String currentDate = dateFormat.format(user.getData().get(0).getDate());
        Document document = new Document(PageSize.A4);
        PdfWriter writer = openPDFWriter(document, user.getId(), currentDate);
        document.open();
        // Title
        document.add(generateParagraph(defaultTitleFont, defaultColor,
                18.0f, Constants.reportTitle, Paragraph.ALIGN_CENTER));
        // Date
        
        document.add(generateParagraph(defalutFont, defaultColor, 12, "Date: " + currentDate,
                Paragraph.ALIGN_CENTER));
        // Result report for
        document.add(generateParagraph(defalutFont, defaultColor, 12,
                Constants.reportSubTitle + user.getUsername(), Paragraph.ALIGN_CENTER, 12, 0));
        // Main Paragraph
        document.add(generateParagraph(defalutFont, defaultColor, 12,
                Constants.mainText,
                Paragraph.ALIGN_JUSTIFIED, 10, 0));
        // Adding a styled "div"
       java.util.List<DyslexiaCategory> list = _userService.categoryDetection(user);
        document.add(generateCustomDiv(list));
        // Title for subtext
        document.add(generateParagraph(
                defaultTitleFont, defaultColor, 12,
                Constants.resultAndRecomTitle, Element.ALIGN_CENTER, 12, 16));
        // Add subtext paragraph
        document.add(generateParagraph(
                defalutFont, defaultColor, 12, Constants.resultAndRecomText, Paragraph.ALIGN_CENTER, 24, 0));
        // title for a second subtext
        document.add(generateParagraph(
                defaultTitleFont, defaultColor, 12, Constants.unexpectedResultTitle,
                Element.ALIGN_CENTER, 16, 0));
        // adding subtext 2
        document.add(generateParagraph(defalutFont, defaultColor, 12,
                Constants.unexpectedResultText, Paragraph.ALIGN_CENTER, 0, 0));

        document.close();
        writer.close();
    }

    private PdfWriter openPDFWriter(Document document, String id,String date) throws DocumentException, FileNotFoundException {
        File directory = new File(pdfFilePath + "\\" + id);
        if (!directory.exists()) {
            directory.mkdirs(); // Make directories recursively
        }
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath +"\\"+ id +"\\"+ date +".pdf"));
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

    private PdfPTable generateCustomDiv(java.util.List<DyslexiaCategory> userCategories) {
        PdfPTable styledDiv = new PdfPTable(1);
        styledDiv.setWidthPercentage(100);
        styledDiv.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        styledDiv.setSpacingBefore(16);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(16);
        cell.setBackgroundColor(new Color(0xE3FFDC));
        cell.setBorder(Rectangle.NO_BORDER);

        // create a list
        List list = generateList(userCategories);

        // Add the list to the cell
        cell.addElement(list);

        styledDiv.addCell(cell);
        return styledDiv;
    }

    private List generateList(java.util.List<DyslexiaCategory> userCategories) {
        // Create a list for bullet points
        List list = new List(List.UNORDERED);
        // list.setListSymbol(" ");
        list.setListSymbol("\u2022"); // Use Unicode character for bullet point
        // Customize the bullet point size and indentation
        list.setIndentationLeft(10); // Adjust the indentation as needed
        if (userCategories.isEmpty()) {
            list.add(new ListItem(new Chunk(" No weaknesses for this user",
                    FontFactory.getFont(defalutFont, 12, Color.black))));
            return list;
        }
        Font fontListItem = FontFactory.getFont(defalutFont, 12, Color.RED);
        // Add items to the list with the word "red" beside each bullet point
        System.out.println("userCategories has " + userCategories.size()  +" Categories");
        for (DyslexiaCategory category : userCategories) {
            list.add(new ListItem(new Chunk(" " + category.getName(), fontListItem)));
        }
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
        return generateParagraph(fontName, color, size, paragraphText, alignment, 0f, 0f);
    }
}
