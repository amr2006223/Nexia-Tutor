import EndScreenGameComponent from "@/components/games/screening-games/endScreenGame";
import TimerComponent from "@/shared/components/progress/timer";
import { useScreeningGamesStore } from "@/shared/state/screening-games";
import { useEffect, useState } from "react";
import { useTimer } from "use-timer";
import SpeakerButtonComponent from "@/shared/components/buttons/speakerButtonComponent";
import BoxesMatrixToFindWordComponent from "../BoxesMatrixToFindWord";
import FooterCounter from "@/shared/components/games/footerCounter";
import { playSoundFromGoogle } from "@/shared/utils/play-sounds";

type FindLetterGameProps = {
  goalLetter: string;
  wordsList: string[];
  nextGameLink: string;
  isLastGame: boolean;
  gameNumber: number;
  goalLetterSound: string;
};

const FindLetterGame = (props: FindLetterGameProps) => {
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

  const [currentWordsList, setCurrentWordsList] = useState<string[]>(
    props.wordsList
  );

  const [finished, setFinished] = useState(false);

  useEffect(() => {
    start();
  }, []);

  const [hasFirstMouseMove, setHasFirstMouseMove] = useState(false);
  const handleFirstPlay = async () => {
    if (!hasFirstMouseMove) {
      try {
        await playSoundFromGoogle(props.goalLetterSound);
      } catch (e) {
        console.error(e);
      }
      setHasFirstMouseMove(true);
    }
  };

  const shuffleWordsList = () => {
    setCurrentWordsList((prevList) => {
      const newList = [...prevList];
      newList.sort(() => Math.random() - 0.5);
      return newList;
    });
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
      onMouseOverCapture={handleFirstPlay}
    >
      <div
        className="
        flex
        flex-row
        items-center
        justify-between
        w-full
        my-3
        px-5
        "
      >
        <div className="text-3xl font-bold text-center">
          Game {props.gameNumber}: Listen & Find
        </div>

        <TimerComponent timeOnSeconds={time} />
      </div>

      <div className="flex flex-col items-center justify-center h-screen">
        <div className="text-3xl font-bold text-center mb-4">
          Listen{" "}
          <SpeakerButtonComponent
            sound={props.goalLetterSound}
            from_google={true}
            theme="dark"
          />
        </div>
        <BoxesMatrixToFindWordComponent
          wordList={currentWordsList}
          goalLetter={props.goalLetter}
          handleSuccess={handleSuccess}
          handleFailure={handleFailure}
        />
      </div>

      <FooterCounter greenCounterNumber={hits} redCounterNumber={misses} />

      {finished && (
        <EndScreenGameComponent
          nextGameLink={props.nextGameLink}
          lastGame={props.isLastGame}
        />
      )}
    </div>
  );
};

export default FindLetterGame;
