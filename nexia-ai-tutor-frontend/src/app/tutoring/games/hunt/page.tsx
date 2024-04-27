"use client";
import ProgressBarComponent from "@/shared/components/progress/progressBar";
import LettersGrid from "@/components/games/tutoring-games/hunt/lettersGrid";
import {
  getTextSound,
  getTextSoundFemale,
} from "@/services/text-to-speech/textSound";
import { Box, Button } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { getWordImage } from "@/services/images/getWordImage";
import Swal from "sweetalert2";
import useWordSearchParams from "@/shared/hooks/useWordSearchParams";

const LetterHuntPage = () => {
  const router = useRouter();
  const { keywordParams } = useWordSearchParams();
  const [keywordSound, setKeywordSound] = useState("");
  const [keywordImage, setKeywordImage] = useState("");
  const [congratsSound, setCongratsSound] = useState("");
  const [open, setOpen] = useState(false);

  const getKeywordSound = async () => {
    const response = await getTextSound(keywordParams);
    const congratsResponse = await getTextSoundFemale(
      "Congratulations! You found the letter " + keywordParams[0]
    );
    const image = await getWordImage(keywordParams);

    setKeywordSound(response);
    setCongratsSound(congratsResponse);
    setKeywordImage(image);
    setOpen(true);
  };

  useEffect(() => {
    getKeywordSound();
    // getKeywordImage();
  }, []);

  const handleOnClickLetter = (letter: string) => {
    if (letter === keywordParams[0]) {
      Swal.fire({
        title: "Congratulations!",
        text: "You found the letter " + keywordParams[0],
        icon: "success",
      });
      handleCongrats();
    } else {
      handleListen();
      Swal.fire({
        title: "Try Again!",
        text: "Wrong Letter",
        icon: "error",
      });
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
      {open ? (
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

          <div className="text-center font-bold text-3xl">Hunt Game</div>
          <Box
            component="img"
            sx={{
              maxHeight: { xs: 250, md: 250 },
              maxWidth: { xs: 250, md: 250 },
            }}
            alt={keywordParams}
            src={keywordImage}
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
          </div>
        </div>
      ) : (
        <ProgressBarComponent />
      )}
    </div>
  );
};

export default LetterHuntPage;
