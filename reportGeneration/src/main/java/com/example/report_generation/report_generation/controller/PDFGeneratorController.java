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

    String filePath = "reportGeneration\\src\\main\\resources\\pdf\\";
    String userFilePath = "reportGeneration\\src\\main\\resources\\json\\ImportantUser.json";

    // String jsonFilePath = "src\\main\\resources\\json\\ImportantUser.json";
    @GetMapping("/pdf/down/{userId}")
    public ResponseEntity<?> downloadPDF(HttpServletResponse response, @PathVariable String userId) throws IOException {
        User user = _userService.getUserById(userId, userFilePath);
        //need to handle this error
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //category detection and get the latest record
        UserData data = _userService.getLatestRecord(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String formatedDate = dateFormat.format(data.getDate());
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=DyslexiaReport " + formatedDate + ".pdf";
        response.setHeader(headerKey, headerValue);
        this._pdfGeneratorService.downloadDocument(response,user);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/pdf/gen")
    public ResponseEntity<String> generatePdf(@RequestBody Map<String, Object> body) throws IOException {
        boolean generated = _pdfGeneratorService.generateDocumentInServer(body.get("userId").toString());
        if(!generated) return new ResponseEntity<String>("Falied to generate PDF", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("Pdf Generated Successfully", HttpStatus.OK);

        
    }

}
