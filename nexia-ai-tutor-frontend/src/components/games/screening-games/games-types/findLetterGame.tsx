import EndScreenGameComponent from "@/components/games/screening-games/endScreenGame";
import { getTextSound } from "@/services/text-to-speech/textSound";
import TimerComponent from "@/shared/components/progress/timer";
import { useScreeningGamesStore } from "@/shared/state/screening-games";
import { useEffect, useState } from "react";
import CounterComponent from "@/shared/components/counter/CounterComponent";
import CloseIcon from "@mui/icons-material/Close";
import CheckIcon from "@mui/icons-material/Check";
import { useTimer } from "use-timer";
import SpeakerButtonComponent from "@/shared/components/buttons/speakerButtonComponent";
import BoxesMatrixToFindWordComponent from "../BoxesMatrixToFindWord";

type FindLetterGameProps = {
  goalLetter: string;
  wordsList: string[];
  nextGameLink: string;
  isLastGame: boolean;
};

const FindLetterGame = (props: FindLetterGameProps) => {
  const gamesStore = useScreeningGamesStore();

  const { time, start } = useTimer({
    initialTime: 5,
    endTime: 0,
    timerType: "DECREMENTAL",
    onTimeOver: () => {
      handleOnTimeEnd();
    },
  });

  const [clicks, setClicks] = useState(0);
  const [hits, setHits] = useState(0);
  const [misses, setMisses] = useState(0);

  const wordsLists: string[] = props.wordsList;

  const [currentWordsList, setCurrentWordsList] =
    useState<string[]>(wordsLists);

  const goalLetter = props.goalLetter;

  let goalLetterSound = "";

  const [finished, setFinished] = useState(false);

  useEffect(() => {
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
      start();
      return;
    }

    setFinished(true);
    handleFinishGame();
  };

  const handleFinishGame = () => {
    const score = hits;
    const accuracy = hits / clicks;
    const missrate = misses / clicks;

    const gameStats = [clicks, hits, misses, score, accuracy, missrate];

    gamesStore.append(gameStats);
  };

  return (
    <div
      className="flex flex-col items-center justify-center h-screen"
      onClick={handleClicks}
    >
      <div>Game 1: Find the word</div>
      <div>Click on the word that starts with the letter {goalLetter}</div>

      <div>
        <TimerComponent timeOnSeconds={time} />
      </div>

      <div className="flex flex-col items-center justify-center h-screen">
        <div className="text-2xl font-bold text-center mb-4">
          Listen and find:{" "}
          <SpeakerButtonComponent
            sound={goalLetterSound}
            from_google={true}
            theme="dark"
          />
        </div>
        <BoxesMatrixToFindWordComponent
          wordList={currentWordsList}
          goalLetter={goalLetter}
          handleSuccess={handleSuccess}
          handleFailure={handleFailure}
        />
      </div>

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

      <div>
        {finished && (
          <EndScreenGameComponent
            nextGameLink={props.nextGameLink}
            lastGame={props.isLastGame}
          />
        )}
      </div>
    </div>
  );
};

export default FindLetterGame;
