"use client";
import ProgressBarComponent from "@/shared/progress/progressBar";
import LettersGrid from "@/components/hunt/lettersGrid";
import {
  getTextSound,
  getTextSoundFemale,
} from "@/services/text-to-speech/textSound";
import { Box, Button, IconButton, Snackbar } from "@mui/material";
import React, { useEffect, useState } from "react";
import CloseIcon from "@mui/icons-material/Close";
import { useSearchParams, useRouter } from "next/navigation";

const LetterHuntPage = () => {
  const router = useRouter();

  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  const handleClick = (message: string) => {
    setOpen(false);
    setMessage(message);
    setOpen(true);
  };

  const handleClose = (
    event: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };
  const action = (
    <React.Fragment>
      <IconButton
        size="small"
        aria-label="close"
        color="inherit"
        onClick={handleClose}
      >
        <CloseIcon fontSize="small" />
      </IconButton>
    </React.Fragment>
  );
  //
  const searchParams = useSearchParams();
  const keywordValue = searchParams.get("keyword");
  const imgLinkValue = searchParams.get("imgLink");
  const imgLink = imgLinkValue ? imgLinkValue : "";

  const keyword = keywordValue ? keywordValue.toUpperCase() : "";
  const [keywordSound, setKeywordSound] = useState("");
  const [congratsSound, setCongratsSound] = useState("");

  const getKeywordSound = async () => {
    const response = await getTextSound(keyword);
    const congratsResponse = await getTextSoundFemale(
      "Congratulations! You found the letter " + keyword[0]
    );
    setKeywordSound(response);
    setCongratsSound(congratsResponse);
  };

  useEffect(() => {
    getKeywordSound();
  }, []);

  const handleOnClickLetter = (letter: string) => {
    if (letter === keyword[0]) {
      handleClick("correct letter");
      handleCongrats();
    } else {
      handleClick("wrong letter");
      handleListen();
    }
  };

  const handleListen = async () => {
    try {
      const audio = new Audio("data:audio/wav;base64," + keywordSound);
      await audio.play();
    } catch (error) {
      console.log("error: hunt: listen word error: ", error);
    }
  };

  const handleCongrats = async () => {
    try {
      const audio = new Audio("data:audio/wav;base64," + congratsSound);
      await audio.play();
    } catch (error) {
      console.log("error: hunt: listen word error: ", error);
    }
  };

  return (
    <div>
      {keywordSound.length > 0 ? (
        <div className="flex flex-col items-center justify-center h-screen">
          <div>
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
          </div>

          <div className="text-center font-bold text-3xl">Lesson 1</div>
          <Box
            component="img"
            sx={{
              maxHeight: { xs: 250, md: 250 },
              maxWidth: { xs: 250, md: 250 },
            }}
            alt={keyword}
            src={imgLink}
          />
          <br />
          <div
            className="flex flex-col items-center ustify-center p-5 rounded-2xl"
            style={{
              backgroundColor: "#E3FFDC",
            }}
          >
            <Button
              className="font-bold text-2xl"
              variant="contained"
              style={{ backgroundColor: "#3E4772", color: "#CDEBC5" }}
              onClick={handleListen}
            >
              Listen
            </Button>
            <br />
            <LettersGrid onClickLetter={handleOnClickLetter} />
            <Snackbar
              open={open}
              autoHideDuration={1500}
              onClose={handleClose}
              message={message}
              action={action}
            />
          </div>
        </div>
      ) : (
        <ProgressBarComponent />
      )}
    </div>
  );
};

export default LetterHuntPage;
