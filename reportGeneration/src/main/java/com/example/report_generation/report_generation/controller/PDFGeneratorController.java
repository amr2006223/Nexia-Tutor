package com.example.report_generation.report_generation.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.service.PDFGeneratorService;
import com.example.report_generation.report_generation.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PDFGeneratorController {
    PDFGeneratorService _pdfGeneratorService;
    @Autowired
    UserService _userService;

    public PDFGeneratorController(PDFGeneratorService PdfGeneratorService) {
        this._pdfGeneratorService = PdfGeneratorService;
    }

    String filePath = "src\\main\\resources\\pdf\\";
    String userFilePath = "src\\main\\resources\\json\\ImportantUser.json";

    // String jsonFilePath = "src\\main\\resources\\json\\ImportantUser.json";
    @GetMapping("/pdf/down/{userId}")
    public void downloadPDF(HttpServletResponse response, @PathVariable String userId) throws IOException {
        User user = _userService.getUserById(userId, userFilePath);
        UserData data = _userService.categoryDetection(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String formatedDate = dateFormat.format(data.getDate());
        // User user = _userService.getUserById(userId, jsonFilePath);
        response.setContentType("application/pdf");
        // DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:mm:ss");
        // String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=DyslexiaReport " + formatedDate + ".pdf";
        response.setHeader(headerKey, headerValue);
        this._pdfGeneratorService.downloadDocument(response,user);
    }

    @PostMapping("/pdf/gen")
    public ResponseEntity<String> generatePdf(@RequestBody Map<String, Object> body) throws IOException {
        // TODO: process POST request
        _pdfGeneratorService.generateDocumentInServer(body);
        return new ResponseEntity<String>("Pdf Generated Successfully", HttpStatus.OK);
    }

}
