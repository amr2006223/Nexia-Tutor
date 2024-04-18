"use client";
import GameOneComponent from "@/components/games/screening-games/1/1";
import EndScreenGameComponent from "@/components/games/screening-games/endScreenGame";
import { getTextSound } from "@/services/text-to-speech/textSound";
import TimerComponent from "@/shared/components/progress/timer";
import { useScreeningGamesStore } from "@/shared/state/screening-games";
import { useEffect, useState } from "react";
import CounterComponent from "@/shared/components/counter/CounterComponent";
import CloseIcon from "@mui/icons-material/Close";
import CheckIcon from "@mui/icons-material/Check";
import { useTimer } from "use-timer";

const FirstScreeningGamePage = () => {
  const gamesStore = useScreeningGamesStore();

  const { time, start } = useTimer({
    initialTime: 2,
    endTime: 0,
    timerType: "DECREMENTAL",
    onTimeOver: () => {
      handleOnTimeEnd();
    },
  });

  const [clicks, setClicks] = useState(0);
  const [hits, setHits] = useState(0);
  const [misses, setMisses] = useState(0);

  const wordsLists: string[] = [
    "ba",
    "be",
    "bi",
    "bo",
    "bu",
    "da",
    "la",
    "na",
    "ra",
    "ta",
    "va",
    "fa",
    "ga",
    "ha",
    "ja",
    "ka",
    "ma",
    "pa",
    "qa",
    "sa",
    "xa",
    "ya",
    "za",
    "wa",
    "ea",
  ];

  const [currentWordsList, setCurrentWordsList] =
    useState<string[]>(wordsLists);

  const [goalLetter, setGoalLetter] = useState("ba");

  let goalLetterSound = "";

  const [finished, setFinished] = useState(false);

  useEffect(() => {
    // get the sound of the goal letter
    getGoalLetterSound();
    start();
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

  const handleOnTimeEnd = () => {
    if (hits === 0 && misses === 0) {
      // handleResetTimer();
      start();
      return;
    }

    setFinished(true);
    handleFinishGame();
  };

  const handleFinishGame = () => {
    console.log("Game finished");
    // calculate the accuracy and missrate
    const score = hits;
    const accuracy = hits / clicks;
    const missrate = misses / clicks;

    const gameStats = [clicks, hits, misses, score, accuracy, missrate];

    // add game stats to the store
    gamesStore.append(gameStats);
    console.log(gameStats);
  };

  // ENd screen

  return (
    <div
      className="flex flex-col items-center justify-center h-screen"
      onClick={handleClicks}
    >
      <div>Game 5: Find the word</div>
      <div>Click on the word that starts with the letter {goalLetter}</div>

      <div>
        <TimerComponent timeOnSeconds={time} />
      </div>

      <GameOneComponent
        goalLetter={goalLetter}
        goalLetterSound={goalLetterSound}
        wordsList={currentWordsList}
        handleSuccess={handleSuccess}
        handleFailure={handleFailure}
      />

      <div
        style={{
          position: "fixed",
          bottom: 0,
          width: "100%",
          backgroundColor: "#fff",
          padding: "10px",
          borderTop: "2px solid #3e4772",
        }}
        className="flex flex-row justify-evenly items-center mt-3"
      >
        <CounterComponent count={misses} color="red" icon={CloseIcon} />
        <CounterComponent count={hits} color="green" icon={CheckIcon} />
      </div>

      <div>{finished && <EndScreenGameComponent nextGameLink="6" />}</div>
    </div>
  );
};

export default FirstScreeningGamePage;
