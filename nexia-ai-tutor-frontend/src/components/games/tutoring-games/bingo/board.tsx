"use client";
import React, { useEffect, useState } from "react";
import { Button, Grid } from "@mui/material";
import LetterComponentBingo from "./letterComponent";
import { LetterDetail } from "@/types/bingo";
import {
  getTextSound,
  getTextSoundFemale,
} from "@/services/text-to-speech/textSound";
import ProgressBarComponent from "../../../../shared/components/progress/progressBar";
import VolumeUpIcon from "@mui/icons-material/VolumeUp";
import { useRouter } from "next/navigation";
import { playSoundFromGoogle } from "@/shared/utils/play-sounds";
import Swal from "sweetalert2";
import StepsCard from "@/shared/components/card/stepsCard";
import BackButtonComponent from "@/shared/components/buttons/backButton";

type Props = {
  size: number;
  letters: string[];
  keyword: string[];
};

const BingoBoard = ({ size, letters, keyword }: Props) => {
  const router = useRouter();
  const [congratsSound, setCongratsSound] = useState("");
  //
  const [gridContent, setGridContent] = useState<LetterDetail[]>([]);
  const [clickedLetter, setClickedLetter] = useState<LetterDetail>();
  const [spelledLetter, setSpelledLetter] = useState<LetterDetail>();

  function shuffleArray(letters: string[]) {
    return [...letters].sort(() => 0.5 - Math.random());
  }

  async function initiateBoard() {
    const congratsResponse = await getTextSoundFemale(
      "Congratulations! you compelete Bingo"
    );

    setCongratsSound(congratsResponse);
    const gridContent: LetterDetail[] = [];

    const keywordPositions: number[] = [];

    // Choose whether to place keyword letters in a row, column, or diagonal
    const placementType = Math.floor(Math.random() * 3); // 0 for row, 1 for column, 2 for diagonal

    // Randomly choose the index for the keyword placement
    const keywordIndex = Math.floor(Math.random() * size);

    // Initialize keyword positions based on the chosen placement type
    if (placementType === 0) {
      for (let i = 0; i < size; i++) {
        keywordPositions.push(keywordIndex * size + i);
      }
    } else if (placementType === 1) {
      for (let i = 0; i < size; i++) {
        keywordPositions.push(i * size + keywordIndex);
      }
    } else {
      for (let i = 0; i < size; i++) {
        keywordPositions.push(i * size + i);
      }
    }

    // Place keyword letters at the chosen positions
    const shuffledKeyword = shuffleArray(keyword);
    for (let i = 0; i < size * size; i++) {
      const isKeywordPosition = keywordPositions.includes(i);
      const letter = isKeywordPosition
        ? shuffledKeyword.shift()
        : shuffledLetters.shift();

      let audio = await getTextSound(letter ? letter : "");

      gridContent.push({
        value: letter ? letter : "",
        index: i,
        isClicked: false,
        color: "green",
        sound: audio,
      });
    }

    return gridContent;
  }

  // this is the initial state of the board will run only once when the board is created
  useEffect(() => {
    const initializeBoard = async () => {
      const gridContent = await initiateBoard();
      setGridContent(gridContent);

      const generateFirstLetterToSpell = () => {
        let randomIndex = Math.floor(Math.random() * gridContent.length);
        console.log("gridContent", gridContent);
        while (!keyword.includes(gridContent[randomIndex].value)) {
          randomIndex = Math.floor(Math.random() * gridContent.length);
        }

        const randomLetter = gridContent[randomIndex];
        spellLetter(randomLetter);
        return randomLetter;
      };

      setSpelledLetter(generateFirstLetterToSpell());
    };

    initializeBoard();
  }, []);

  // this is the state of the board after a letter is clicked
  useEffect(() => {
    // if (spelledLetter) {
    //   handleClick(spelledLetter.value);
    // }
    let updatedGridContent = [...gridContent];
    if (clickedLetter) {
      if (clickedLetter.value === spelledLetter!.value) {
        const updatedLetter = {
          ...clickedLetter,
          isClicked: true,
          color: "red",
        };
        updatedGridContent[clickedLetter.index] = updatedLetter;
        setGridContent(updatedGridContent);
        changeRandomSpelled(updatedGridContent);
      } else {
        Swal.fire({
          title: "Try Again!",
          text: "Wrong Letter",
          icon: "error",
        });
        // delay 2 seconds
        if (spelledLetter) {
          spellLetter(spelledLetter);
        }
      }
    }
  }, [clickedLetter]);

  useEffect(() => {
    checkBingo();
  }, [gridContent]);

  const handleCongrats = async () => {
    try {
      Swal.fire({
        title: "Congratulations!",
        text: "You compelete Bingo",
        icon: "success",
      });
      await playSoundFromGoogle(congratsSound);
    } catch (error) {
      console.log("error: hunt: listen word error: ", error);
    }
  };
  // Generate a shuffled array of letters, excluding the keyword letters
  const shuffledLetters = [...letters]
    .filter((letter) => !keyword.includes(letter))
    .sort(() => 0.5 - Math.random())
    .slice(0, size * size - size);

  const pressLetter = (letter: LetterDetail) => {
    console.log("letter", letter);
    setClickedLetter(letter);
  };

  const spellLetter = async (letter: LetterDetail) => {
    try {
      await playSoundFromGoogle(letter.sound);
    } catch (error) {
      console.log("error: spellLetter", error);
    }
  };

  const changeRandomSpelled = (updatedContent: LetterDetail[]) => {
    if (updatedContent.length === 0) return;

    const unspelledLetters = updatedContent.filter(
      (cell) => !cell.isClicked && keyword.includes(cell.value)
    );

    if (unspelledLetters.length > 0) {
      const randomIndex = Math.floor(Math.random() * unspelledLetters.length);
      const randomLetter = unspelledLetters[randomIndex];
      setSpelledLetter(randomLetter);
      spellLetter(randomLetter);
    } else {
      // Handle the case where all letters are already spelled
      console.log("All letters are already spelled");
    }
  };

  const checkBingo = () => {
    if (gridContent.length === 0) return;

    // console.log("grid content Checking bingo", gridContent);

    for (let i = 0; i < size; i++) {
      const row = gridContent.slice(i * size, (i + 1) * size);

      if (row.every((cell) => cell.isClicked)) {
        console.log("Bingo in row", i);
        console.log("row: " + i, row);
        handleCongrats();

        return;
      }
    }

    // Check columns
    for (let i = 0; i < size; i++) {
      const column = [];
      for (let j = 0; j < size; j++) {
        column.push(gridContent[j * size + i]);
      }

      if (column.every((cell) => cell.isClicked)) {
        console.log("Bingo in column", i);
        console.log("column: " + i, column);
        handleCongrats();
        return;
      }
    }

    // Check diagonals
    const leftDiagonal = [];
    const rightdiagonal = [];

    for (let i = 0; i < size; i++) {
      leftDiagonal.push(gridContent[i * size + i]);
      rightdiagonal.push(gridContent[i * size + (size - i - 1)]);
    }

    if (leftDiagonal.every((cell) => cell.isClicked)) {
      console.log("Bingo in diagonal 1");
      console.log("leftDiagonal: ", leftDiagonal);
      handleCongrats();
      return;
    }

    if (rightdiagonal.every((cell) => cell.isClicked)) {
      console.log("Bingo in diagonal 2");
      console.log("rightdiagonal: ", rightdiagonal);
      handleCongrats();
      return;
    }
  };

  return (
    <div className="flex flex-col h-screen">
      {/* Header */}
      <div className="flex justify-between items-center p-2 relative">
        <BackButtonComponent />
        <div>
          <StepsCard
            title="Bingo Game"
            steps={[
              "Click on the letter that you hear",
              "Collect the words to complete BINGO",
            ]}
          />
        </div>
        <div></div>
      </div>

      {/* Body */}
      <div className="flex-grow flex flex-col justify-center items-center p-4">
        {/* If gridContent is not loaded, show ProgressBarComponent */}
        {gridContent.length > 0 ? (
          <div className="container mx-auto">
            <div
              className="p-6 mx-4 flex flex-col items-center justify-center rounded-2xl"
              style={{ backgroundColor: "#E3FFDC" }}
            >
              <div className="text-center mb-4">
                <Button
                  startIcon={
                    <VolumeUpIcon
                      style={{ color: "#CDEBC5", fontSize: "2rem" }}
                    />
                  }
                  className="font-bold text-2xl"
                  variant="contained"
                  style={{ backgroundColor: "#3E4772", color: "#CDEBC5" }}
                  onClick={async () => {
                    await playSoundFromGoogle(spelledLetter!.sound);
                  }}
                >
                  Letter sound
                </Button>
              </div>
              <Grid container spacing={6}>
                {gridContent.map((index) => (
                  <Grid item xs={12 / size} key={index.index}>
                    <LetterComponentBingo
                      letter={index}
                      pressLetter={() => pressLetter(index)}
                    />
                  </Grid>
                ))}
              </Grid>
            </div>
          </div>
        ) : (
          <ProgressBarComponent />
        )}
      </div>
    </div>
  );
};

export default BingoBoard;
