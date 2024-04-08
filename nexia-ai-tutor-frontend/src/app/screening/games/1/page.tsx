"use client";
import GameOneComponent from "@/components/games/screening-games/1/1";
import { useEffect, useState } from "react";
import Swal from "sweetalert2";

const FirstScreeningGamePage = () => {
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

  const wordsLists: string[][] = [
    ["e", "u", "u", "a", "e", "u", "i", "e", "a"],
    ["qar", "pra", "qra", "gar", "gar"],
    ["q", "a", "b", "c"],
  ];

  const [currentWordsListIndex, setCurrentWordsListIndex] = useState(0);
  const [currentWordsList, setCurrentWordsList] = useState<string[]>([]);
  const [goalLetter, setGoalLetter] = useState("");

  useEffect(() => {
    setCurrentWordsList(wordsLists[currentWordsListIndex]);

    // Shuffle the wordList
    currentWordsList.sort(() => Math.random() - 0.5);

    let randomIndex = Math.floor(
      Math.random() * wordsLists[currentWordsListIndex].length
    );

    setGoalLetter(wordsLists[currentWordsListIndex][randomIndex]);
  }, [currentWordsListIndex]);

  const goToNextWordsList = () => {
    setCurrentWordsList(wordsLists[currentWordsListIndex + 1]);
    setCurrentWordsListIndex(currentWordsListIndex + 1);
  };

  const handleSuccess = () => {
    if (checkFinished()) {
      return;
    }

    setHits((prevHits) => prevHits + 1);

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
      Swal.fire({
        title: "Good job!",
        text: "You have successfully completed the screening games.",
        icon: "success",
        confirmButtonText: "OK",
      });
      return true;
    }
  };

  const handleClicks = () => {
    setClicks((prevClicks) => prevClicks + 1);
  };

  return (
    <div
      className="flex flex-col items-center justify-center h-screen"
      onClick={handleClicks}
    >
      <GameOneComponent
        goalLetter={goalLetter}
        wordsList={currentWordsList}
        handleSuccess={handleSuccess}
        handleFailure={handleFailure}
      />
    </div>
  );
};

export default FirstScreeningGamePage;
