"use client";
import GameOneComponent from "@/components/games/screening-games/1/1";
import EndScreenGameComponent from "@/components/games/screening-games/endScreenGame";
import { getTextSound } from "@/services/text-to-speech/textSound";
import TimerComponent from "@/shared/components/progress/timer";
import { useScreeningGamesStore } from "@/shared/state/screening-games";
import { useEffect, useState } from "react";

const FirstScreeningGamePage = () => {
  const gamesStore = useScreeningGamesStore();
  /*
    (Clicks)   = number of Clicks;  done
    (Hits)     = number of correct answers; done
    (Misses)   = number of incorrect answers; done
    (Accuracy) = Hits / Clicks;
    (Missrate) = Misses / Clicks;
  */

  const [clicks, setClicks] = useState(0);
  const [hits, setHits] = useState(0);
  const [misses, setMisses] = useState(0);

  const wordsLists: string[] = [
    "b",
    "o",
    "b",
    "w",
    "b",
    "p",
    "i",
    "b",
    "t",
    "l",
    "r",
    "a",
    "q",
    "x",
    "b",
    "u",
    "b",
    "e",
    "b",
    "f",
    "b",
    "y",
    "b",
    "z",
    "b",
  ];
  const [currentWordsList, setCurrentWordsList] =
    useState<string[]>(wordsLists);

  const [goalLetter, setGoalLetter] = useState("b");

  let goalLetterSound = "";

  const [finished, setFinished] = useState(false);

  useEffect(() => {
    // get the sound of the goal letter
    getGoalLetterSound();
  }, []);

  const shuffleWordsList = () => {
    setCurrentWordsList((prevList) => {
      const newList = [...prevList];
      newList.sort(() => Math.random() - 0.5);
      return newList;
    });
  };

  const getGoalLetterSound = async () => {
    const sound = await getTextSound(goalLetter);
    console.log(sound);
    goalLetterSound = sound;
  };

  const handleSuccess = () => {
    setHits((prevHits) => prevHits + 1);

    shuffleWordsList();
  };

  const handleFailure = () => {
    setMisses((prevMisses) => prevMisses + 1);

    shuffleWordsList();
  };

  const handleClicks = () => {
    setClicks((prevClicks) => prevClicks + 1);
  };

  const [resetKey, setResetKey] = useState(0);

  const handleOnTimeEnd = () => {
    setFinished(true);
  };

  useEffect(() => {
    // calculate the accuracy and missrate
    const accuracy = hits / clicks;
    const missrate = misses / clicks;
    const gameStats = [clicks, hits, misses, accuracy, missrate];

    // add game stats to the store
    gamesStore.append(gameStats);
    console.log(gameStats);
  }, [finished]);

  // ENd screen

  return (
    <div
      className="flex flex-col items-center justify-center h-screen"
      onClick={handleClicks}
    >
      <div>Game 3: Find the word</div>
      <div>Click on the word that starts with the letter {goalLetter}</div>

      <div>
        <TimerComponent
          onTimeEnd={handleOnTimeEnd}
          timeOnSeconds={15}
          key={resetKey}
        />
      </div>

      <GameOneComponent
        goalLetter={goalLetter}
        goalLetterSound={goalLetterSound}
        wordsList={currentWordsList}
        handleSuccess={handleSuccess}
        handleFailure={handleFailure}
      />

      <div>{finished && <EndScreenGameComponent nextGameLink="4" />}</div>
    </div>
  );
};

export default FirstScreeningGamePage;
