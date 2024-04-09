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
    "pla",
    "ple",
    "pli",
    "plo",
    "plu",
    "plb",
    "plc",
    "pld",
    "ple",
    "plf",
    "plg",
    "plh",
    "pli",
    "plj",
    "plk",
    "pll",
    "plm",
    "pln",
    "plo",
    "plp",
    "plq",
    "plr",
    "pls",
    "plt",
    "plu",
  ];
  const [currentWordsList, setCurrentWordsList] =
    useState<string[]>(wordsLists);

  const [goalLetter, setGoalLetter] = useState("pla");

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

  const handleResetTimer = () => {
    setResetKey((prevKey) => prevKey + 1);
  };

  const handleOnTimeEnd = () => {
    if (hits === 0 && misses === 0) {
      handleResetTimer();
      return;
    }
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
      <div>Game 7: Find the word</div>
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

      <div>{finished && <EndScreenGameComponent nextGameLink="8" />}</div>
    </div>
  );
};

export default FirstScreeningGamePage;
