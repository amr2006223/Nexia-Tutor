"use client";
import GameTwoComponent from "@/components/games/screening-games/2/2";
import TimerComponent from "@/shared/components/progress/timer";
import React, { useEffect, useState } from "react";
import Swal from "sweetalert2";

type SecondScreeningGameLetter = {
  letter: string;
  otherLetter: string;
};

const SecondScreeningGamePage = () => {
  /*
    (Clicks)   = number of Clicks;  done
    (Hits)     = number of correct answers; done
    (Misses)   = number of incorrect answers; done
    (Score)    = sum of Hits per set of exercises;
    (Accuracy) = Hits / Clicks;
    (Missrate) = Misses / Clicks;
  */

  const [clicks, setClicks] = useState(0);
  const [hits, setHits] = useState(0);
  const [misses, setMisses] = useState(0);

  const wordsLists: SecondScreeningGameLetter[] = [
    { letter: "e", otherLetter: "a" },
    { letter: "F", otherLetter: "E" },
    { letter: "n", otherLetter: "u" },
    { letter: "p", otherLetter: "d" },
  ];

  const [currentWordsListIndex, setCurrentWordsListIndex] = useState(0);
  const [currentWordsList, setCurrentWordsList] = useState<string[]>([]);
  const [goalLetter, setGoalLetter] = useState("");

  useEffect(() => {
    let otherLettersArray = Array(24).fill(
      wordsLists[currentWordsListIndex].otherLetter
    );

    let wordList = [
      wordsLists[currentWordsListIndex].letter,
      ...otherLettersArray,
    ];
    wordList.sort(() => Math.random() - 0.5);
    setCurrentWordsList(wordList);

    setGoalLetter(wordsLists[currentWordsListIndex].letter);
  }, [currentWordsListIndex]);

  const goToNextWordsList = () => {
    setCurrentWordsListIndex(currentWordsListIndex + 1);
  };

  const handleSuccess = () => {
    if (checkFinished()) {
      Swal.fire({
        title: "Good job!",
        text: "You have successfully completed Game 1",
        icon: "success",
        confirmButtonText: "OK",
      });
      return;
    }

    setHits((prevHits) => prevHits + 1);
    handleResetTimer();

    goToNextWordsList();
  };

  const handleFailure = () => {
    setMisses((prevMisses) => prevMisses + 1);

    Swal.fire({
      title: "Oops! Try again.",
      text: "You clicked the wrong word. Try again!",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  const checkFinished = () => {
    if (currentWordsListIndex === wordsLists.length - 1) {
      return true;
    }
  };

  const handleClicks = () => {
    setClicks((prevClicks) => prevClicks + 1);
  };

  const [resetKey, setResetKey] = useState(0);
  const handleResetTimer = () => {
    setResetKey((prevKey) => prevKey + 1);
  };

  const handleOnTimeEnd = () => {
    if (checkFinished()) {
      return;
    }
    goToNextWordsList();
    handleResetTimer();
  };
  return (
    <div
      className="flex flex-col items-center justify-center h-screen"
      onClick={handleClicks}
    >
      <div>Game 2: Find the Diffrent Letter</div>
      <div>
        Phase {currentWordsListIndex + 1} / {wordsLists.length}
      </div>
      <div>
        <TimerComponent
          onTimeEnd={handleOnTimeEnd}
          timeOnSeconds={15}
          key={resetKey}
        />
      </div>
      <GameTwoComponent
        goalLetter={goalLetter}
        wordsList={currentWordsList}
        handleSuccess={handleSuccess}
        handleFailure={handleFailure}
      />
    </div>
  );
};

export default SecondScreeningGamePage;
