// components/ReportPage.tsx
import React, { ReactNode, useState } from "react";
import { useRouter } from "next/navigation";
import {
  Container,
  Typography,
  Button,
  Box,
  Paper,
  SelectChangeEvent,
} from "@mui/material";

interface RoundedRectangleProps {
  color: string;
  children: ReactNode;
}

const RoundedRectangle: React.FC<RoundedRectangleProps> = ({ color, children }) => (
  <Paper
    sx={{
      backgroundColor: color,
      borderRadius: "10px",
      padding: "16px",
      marginTop: "16px",
    }}
  >
    {children}
  </Paper>
);

interface ReportPageProps {
  reportDate: string;
  userEmail: string;
  cognitiveSkills: string[];
  riskStatus: Record<string, string>;
  userId: string;
  prediction: string;
}

const ReportPage: React.FC<ReportPageProps> = ({
  reportDate,
  userEmail,
  cognitiveSkills,
  riskStatus,
  userId,
  prediction,
}) => {
   const [selectedReportId, setSelectedReportId] = useState("");
  const handleReportChange = (event: SelectChangeEvent<string>) => {
    setSelectedReportId(event.target.value);
  };

  const downloadReport = async () => {
    const reportUrl = `http://localhost:8080/pdf/down/123e4567-e89b-12d3-a456-426614174001`;
 
    try {
      const response = await fetch(reportUrl, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error("API request failed");
      }

      const blob = await response.blob();
      const filename =
        response.headers?.get("Content-Disposition")?.split("filename=")[1] ||
        "DyslexiaReport.pdf";

      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error("Error making API request:", error);
    }
  };
  const dyslexia = prediction == "0" ? "has Dyslexia" : "doesn't have Dyslexia";
     const router = useRouter();
  return (
    <Container component="main" maxWidth="md" sx={{ textAlign: "center" }}>
      <Button
        onClick={() => router.back()}
        className="font-bold text-base"
        variant="contained"
        style={{
          backgroundColor: "#3E4772",
          color: "#CDEBC5",
          position: "absolute",
          top: "10px",
          left: "10px",
        }}
      >
        Back
      </Button>
      <Typography
        variant="h4"
        sx={{ color: "#3E4772", fontWeight: "bold", marginTop: "16px" }}
      >
        Screening test report
      </Typography>
      <Typography variant="body1" sx={{ color: "#3E4772" }}>
        Date: {reportDate} <br />
        Results report for: {userEmail} <br />
        User {dyslexia}
      </Typography>

      <Typography variant="body1" sx={{ color: "#3E4772", marginTop: "16px" }}>
        The following report presents the outcomes of the dyslexia screening
        test, designed to identify potential challenges in reading, spelling,
        and memory for children aged 5 to 11. It is important to note that this
        screening is not a diagnostic tool but serves as an initial step in
        creating a tailored educational environment within the language learning
        system.
      </Typography>

      <RoundedRectangle color="#E3FFDC">
        {cognitiveSkills.map((skill, index) => (
          <Box
            key={index}
            sx={{ display: "flex", alignItems: "center", marginTop: "8px" }}
          >
            <Box
              sx={{
                width: "10px",
                height: "10px",
                borderRadius: "50%",
                marginRight: "8px",
                backgroundColor: riskStatus[skill] === "risk" ? "red" : "green",
              }}
            />
            <Typography variant="body2" sx={{ color: "#3E4772" }}>
              {skill}
            </Typography>
          </Box>
        ))}
      </RoundedRectangle>

      {/* Results and Recommendations */}
      <Typography
        variant="body1"
        sx={{ color: "#3E4772", marginTop: "16px", fontWeight: "bold" }}
      >
        Results and Recommendations:
      </Typography>
      <Typography variant="body1" sx={{ color: "#3E4772" }}>
        Upon completion of the screening test, if a child is identified at risk,
        it is advisable to seek professional assistance. Referrals can be made
        to educational center experts or external health professionals in
        specialized centers. For those located in Egypt, we can provide
        recommendations for suitable services.
      </Typography>
      <Button
        // onClick={() => router.back()}
        className="font-bold text-base"
        variant="contained"
        style={{
          backgroundColor: "#3E4772",
          color: "#CDEBC5",
          marginTop: "16px",
        }}
      >
        Check Recommended Places
      </Button>

      {/* Cognitive Skills Stimulation */}
      <Typography variant="body1" sx={{ color: "#3E4772", marginTop: "16px" }}>
        Cognitive skills can be stimulated through daily games and activities
        focusing on reading, memory, and spelling. Our personalized language
        learning service complements these efforts, contributing to a holistic
        educational approach.
      </Typography>

      {/* Unexpected Results */}
      <Typography
        variant="body1"
        sx={{ color: "#3E4772", marginTop: "16px", fontWeight: "bold" }}
      >
        Unexpected Results:
      </Typography>
      <Typography
        variant="body1"
        style={{
          color: "#3E4772",
          marginLeft: "4px",
          textDecoration: "underline",
        }}
      >
        If the screening outcome differs from expectations, particularly if a
        child is already diagnosed with dyslexia, we encourage contacting us at
        <a
          href="mailto:nexiaTeam@gmail.com"
          style={{
            color: "blue",
            marginLeft: "4px",
            textDecoration: "underline",
          }}
        >
          nexiaTeam@gmail.com
        </a>
        . Your input is crucial for our ongoing study to understand and address
        potential discrepancies, aiding in the continuous improvement of the
        Nexia Tutor.
      </Typography>

      {/* Additional Information */}
      <Typography variant="body1" sx={{ color: "#3E4772" }}>
        The dyslexia screening test is an initial assessment tool and does not
        offer a diagnosis. Only professionals can provide accurate diagnoses.
        The test should be conducted only once for accurate results and should
        not be repeated to evaluate the user. The test is specifically designed
        for the English language, and its accuracy may vary for other languages
        using Latin letters. The system is focused on language learning aspects
        such as spelling, writing, reading, and working memory and may not be
        applicable to other disorders like ADHD or dyscalculia.
      </Typography>

      {/* Print Report Button */}
      <Button
        onClick={downloadReport}
        className="font-bold text-base"
        variant="contained"
        style={{
          backgroundColor: "#3E4772",
          color: "#CDEBC5",
        }}
      >
        Print Report
      </Button>

      <Button
        onClick={() => router.push("/lessons")}
        className="font-bold text-base"
        variant="contained"
        style={{
          backgroundColor: "#3E4772",
          color: "#CDEBC5",
          margin: "20px",
        }}
      >
        Begin Learning
      </Button>
    </Container>
  );
};

export default ReportPage;
