package com.example.report_generation.report_generation.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;


import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.service.JwtService;
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
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/report-generation")
public class PDFGeneratorController {
    private PDFGeneratorService _pdfGeneratorService;
    @Autowired
    private UserService _userService;
    @Autowired
    private JwtService _JwtService;

    public PDFGeneratorController(PDFGeneratorService PdfGeneratorService) {
        this._pdfGeneratorService = PdfGeneratorService;
    }

    String filePath = "src\\main\\resources\\pdf\\";
    String userFilePath = "src\\main\\resources\\json\\ImportantUser.json";

    // String jsonFilePath = "src\\main\\resources\\json\\ImportantUser.json";
    @GetMapping("/pdf/down/{userId}")
    public ResponseEntity<?> downloadPDF(HttpServletResponse response, @PathVariable String userId) throws IOException {
        String id = _JwtService.extractUUID(userId);
        User user = _userService.getUserById(id, userFilePath);
        //need to handle this error
        if(user == null){
            System.out.println("user is null");
            return new ResponseEntity<String>("User Not Found",HttpStatus.BAD_REQUEST);
        }
        //category detection and get the latest record
        UserData data = _userService.getLatestRecord(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String formatedDate = dateFormat.format(data.getDate());
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=DyslexiaReport " + formatedDate + ".pdf";
        response.setHeader(headerKey, headerValue);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        this._pdfGeneratorService.downloadDocument(response,user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/pdf/gen")
    public ResponseEntity<String> generatePDF(@RequestBody Map<String, Object> body) throws IOException {
        String token = body.get("userId").toString();
        String id = _JwtService.extractUUID(token);
        User user = _userService.getUserById(id, userFilePath);
        boolean generated = _pdfGeneratorService.generateDocumentInServer(user);
        if(!generated) return new ResponseEntity<String>("Failed to generate PDF", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("Pdf Generated Successfully", HttpStatus.OK);
    }

}
