"use client";
// pages/another-page.tsx
import ReportPage from "@/components/report/reportComponent";
import React from "react";
import { useSearchParams } from "next/navigation";

const AnotherPage: React.FC = () => {
  // Add your state and fetch functions here
  const searchParams = useSearchParams();
  const prediction = searchParams.get("prediciton") || "0";
  const userId = "11234567-e312d3-a456-426614174001";
  const reportProps = {
    reportDate: "7 Jan 2023",
    userEmail: "amremad@gmail.com",
    cognitiveSkills: [
      "Alphabetical awareness",
      "Morphological awareness",
      "Lexical awareness",
      "Syntactical awareness",
      "Phonological awareness",
      "Working memory",
      "Semantic awareness",
    ],
    riskStatus: {
      "Alphabetical awareness": "risk",
      // ... Set risk status for other skills
    },
    userId: userId,
    prediction: prediction,
  };

  return <ReportPage {...reportProps} />;
};

export default AnotherPage;
