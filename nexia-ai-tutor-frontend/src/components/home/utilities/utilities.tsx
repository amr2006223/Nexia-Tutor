"use client";
import React, { useState } from "react";
import { Fab } from "@mui/material";
import VolumeUpIcon from "@mui/icons-material/VolumeUp";
import StopIcon from "@mui/icons-material/Stop";
import CustomAlert from "@/components/customAlert/CustomAlert";

const Utilities = () => {
  const [isSpeaking, setIsSpeaking] = useState(false);
  const [speech, setSpeech] = useState<SpeechSynthesisUtterance | undefined>(
    undefined
  );
  const [showAlert, setShowAlert] = useState(false); // State to control showing the alert

  const handleClick = () => {
    if (!isSpeaking) {
      const selection = window.getSelection();
      if (selection) {
        const selectedText = selection.toString();
        if (selectedText) {
          const newSpeech = new SpeechSynthesisUtterance(selectedText);
          window.speechSynthesis.speak(newSpeech);
          setSpeech(newSpeech);
          setIsSpeaking(true);
          // Event listener to detect when speech has finished
          newSpeech.addEventListener("end", handleSpeechEnd);
          return;
        }
      }
      // If no text is selected or speech synthesis is not supported
      // Set showAlert to true to display the alert
      setShowAlert(true);
    } else {
      // Stop speech if it's currently speaking
      window.speechSynthesis.cancel();
      setIsSpeaking(false);
      setSpeech(undefined); // Reset speech to undefined
    }
  };

  const handleSpeechEnd = () => {
    setIsSpeaking(false);
    setSpeech(undefined); // Reset speech to undefined
  };

  const handleCloseAlert = () => {
    setShowAlert(false);
  };

  return (
    <div style={{ position: "fixed", bottom: 20, left: 20, zIndex: 9999999 }}>
      {/* Conditionally render the alert */}
      {showAlert && (
        <CustomAlert
          message="Please select text to listen to."
          onClose={handleCloseAlert}
        />
      )}
      <Fab
        style={{
          backgroundColor: "#1976d2",
          color: "#fff",
          zIndex: 9999999,
        }}
        aria-label="text-to-speech"
        onClick={handleClick}
      >
        {isSpeaking ? <StopIcon /> : <VolumeUpIcon />}
      </Fab>
    </div>
  );
};

export default Utilities;
