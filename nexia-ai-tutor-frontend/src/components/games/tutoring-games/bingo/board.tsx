"use client";
import React, { useEffect, useState } from "react";
import { Button, Grid, IconButton, Snackbar } from "@mui/material";
import LetterComponentBingo from "./letterComponent";
import { LetterDetail } from "@/types/bingo";
import CloseIcon from "@mui/icons-material/Close";
import {
  getTextSound,
  getTextSoundFemale,
} from "@/services/text-to-speech/textSound";
import ProgressBarComponent from "../../../../shared/progress/progressBar";
import VolumeUpIcon from "@mui/icons-material/VolumeUp";
import { useRouter } from "next/navigation";

type Props = {
  size: number;
  letters: string[];
  keyword: string[];
};

const BingoBoard = ({ size, letters, keyword }: Props) => {
  const router = useRouter();
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("Bingo");
  const [congratsSound, setCongratsSound] = useState("");
  //
  const [gridContent, setGridContent] = useState<LetterDetail[]>([]);
  const [clickedLetter, setClickedLetter] = useState<LetterDetail>();
  const [spelledLetter, setSpelledLetter] = useState<LetterDetail>();
  // const [audioUrl, setAudioUrl] = useState("");

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
        handleClick("Correct Letter");
        changeRandomSpelled(updatedGridContent);
      } else {
        handleClick("Wrong Letter");
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
      const audio = new Audio("data:audio/wav;base64," + congratsSound);
      await audio.play();
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
      const audio = new Audio("data:audio/wav;base64," + letter.sound);
      await audio.play();
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
        handleClick("Bingo");
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
        handleClick("Bingo");
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
      handleClick("Bingo");
      handleCongrats();
      return;
    }

    if (rightdiagonal.every((cell) => cell.isClicked)) {
      console.log("Bingo in diagonal 2");
      console.log("rightdiagonal: ", rightdiagonal);
      handleClick("Bingo");
      handleCongrats();
      return;
    }
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

  return (
    <div>
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
      {/* if  gridContent not loaded load progress bar*/}
      {gridContent.length > 0 ? (
        <div className="container mx-auto">
          <br />
          <div
            className="text-center font-bold text-2xl"
            style={{
              color: "#3E4772",
            }}
          >
            Lesson 1
          </div>
          <br />
          <div
            className="p-6 mx-4 flex flex-col items-center justify-center"
            style={{ backgroundColor: "#E3FFDC" }}
          >
            <div className="text-center">
              <Button
                startIcon={
                  <VolumeUpIcon
                    style={{ color: "#CDEBC5", fontSize: "2rem" }}
                  />
                }
                className="font-bold text-2xl"
                variant="contained"
                style={{ backgroundColor: "#3E4772", color: "#CDEBC5" }}
                onClick={() => {
                  const audio = new Audio(
                    "data:audio/wav;base64," + spelledLetter?.sound
                  );
                  audio.play();
                }}
              >
                Letter sound
              </Button>
            </div>
            <br />
            <Grid container spacing={6}>
              {gridContent &&
                gridContent.map((index) => (
                  <Grid item xs={12 / size} key={index.index}>
                    <LetterComponentBingo
                      letter={index}
                      pressLetter={() => pressLetter(index)}
                    />
                  </Grid>
                ))}
            </Grid>
          </div>

          {/*  */}
          <Snackbar
            open={open}
            autoHideDuration={6000}
            onClose={handleClose}
            message={message}
            action={action}
          />
        </div>
      ) : (
        <ProgressBarComponent />
      )}
    </div>
  );
};

export default BingoBoard;
