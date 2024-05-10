package com.example.report_generation.report_generation.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.servlet.http.HttpServletResponse;

import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.repository.UserRepository;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PDFGeneratorServiceTest {

    @Mock
    private PDFUtils pdfUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private PDFGeneratorService pdfGeneratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    public UserData data;
    public UserData data2;
    public UserData data3;
    public UserData data4;
    List<UserData> alldata = new ArrayList<>();
    public PDFGeneratorServiceTest() {
        Map<String,String> record = new HashMap<>();
        record.put("some", "value");
        data = new UserData("null", 0, record, new Date());
        data2 = new UserData("null", 0, record, new Date());
        data3 = new UserData("null", 0, record, new Date());
        data4 = new UserData("null", 0, record, new Date());
        alldata.add(data);
        alldata.add(data2);
        alldata.add(data3);
        alldata.add(data4);
    }
    
    @Test
    void generateDocumentInServer_userNotFound() throws IOException {
        // Arrange
        User user = new User();
        user.setId("user123");
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.empty());

        // Act
        boolean result = pdfGeneratorService.generateDocumentInServer(user);

        // Assert
        assertFalse(result);
    }

    // Additional tests for other methods

    // For the private methods like openPDFWriter, you can test them by calling the
    // methods that use them directly.
}
