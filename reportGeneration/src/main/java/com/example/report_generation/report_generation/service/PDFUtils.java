package com.example.report_generation.report_generation.service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.utils.Constants;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
@Service
public class PDFUtils {
    private final Color defaultColor = new Color(0x3E4772);
    private final String defaultTitleFont = FontFactory.HELVETICA_BOLD;
    private final String defalutFont = FontFactory.HELVETICA;
    @Autowired
    private UserService _userService;
    @Autowired
    private DyslexiaCategoryService dyslexiaCategoryService;

    public String formatUserDate(User user) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy HHmm");
        return dateFormat.format(user.getData().get(0).getDate());
    }

    public String determineUserResult(User user) {
        int prediction = user.getData().get(0).getPrediction();
        String username = user.getUsername();
        return prediction == 0 ? username + " Doesn't Have Dyslexia" : username + " Have Dyslexia";
    }

    public Document createDocument() {
        return new Document(PageSize.A4);
    }

    public void addTitle(Document document) throws DocumentException {
        Paragraph titleParagraph = generateParagraph(
                this.defaultTitleFont, this.defaultColor, 18.0f, Constants.reportTitle, Paragraph.ALIGN_CENTER);
        document.add(titleParagraph);
    }

    public void addDate(Document document, String currentDate) throws DocumentException {
        Paragraph dateParagraph = generateParagraph(
                this.defalutFont, this.defaultColor, 12, "Date: " + currentDate, Paragraph.ALIGN_CENTER);
        document.add(dateParagraph);
    }

    public void addReportFor(Document document, String username) throws DocumentException {
        Paragraph reportForParagraph = generateParagraph(
                this.defalutFont, this.defaultColor, 12, Constants.reportSubTitle + username, Paragraph.ALIGN_CENTER, 0,
                0);
        document.add(reportForParagraph);
    }

    public void addResult(Document document, String result) throws DocumentException {
        Paragraph resultParagraph = generateParagraph(
                this.defalutFont, this.defaultColor, 12, Constants.reportResult + result, Paragraph.ALIGN_CENTER, 12,
                0);
        document.add(resultParagraph);
    }

    public void addMainParagraph(Document document) throws DocumentException {
        Paragraph mainParagraph = generateParagraph(
                this.defalutFont, this.defaultColor, 12, Constants.mainText, Paragraph.ALIGN_JUSTIFIED, 10, 0);
        document.add(mainParagraph);
    }

    public void addCategoryDiv(Document document, User user) throws DocumentException {
        java.util.List<DyslexiaCategory> list = dyslexiaCategoryService.categorizeUser(user);
        Element customDiv = generateCustomDiv(list);
        document.add(customDiv);
    }

    public void addResultAndRecommendation(Document document) throws DocumentException {
        document.add(generateParagraph(
                this.defaultTitleFont, this.defaultColor, 12, Constants.resultAndRecomTitle, Element.ALIGN_CENTER, 12,
                16));
        document.add(generateParagraph(
                this.defalutFont, this.defaultColor, 12, Constants.resultAndRecomText, Paragraph.ALIGN_CENTER, 24, 0));
    }

    public void addUnexpectedResult(Document document) throws DocumentException {
        document.add(generateParagraph(
                this.defaultTitleFont, this.defaultColor, 12, Constants.unexpectedResultTitle, Element.ALIGN_CENTER, 16,
                0));
        document.add(generateParagraph(
                this.defalutFont, this.defaultColor, 12, Constants.unexpectedResultText, Paragraph.ALIGN_CENTER, 0, 0));
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
    
    public void closeResources(Document document, PdfWriter writer) {
        document.close();
        writer.close();
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
        System.out.println("userCategories has " + userCategories.size() + " Categories");
        for (DyslexiaCategory category : userCategories) {
            list.add(new ListItem(new Chunk(" " + category.getName(), fontListItem)));
        }
        // Add more items as needed
        return list;
    }
}