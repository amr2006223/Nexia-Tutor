"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import Utilities from "@/components/home/utilities/utilities";
import { getTextSound } from "@/services/text-to-speech/textSound";

const FifthScreeningGamePage = async () => {
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

  const goalLetter = "ba";

  const nextGameLink = "6";

  const gameNumber = 5;

  const goalLetterSound = await getTextSound(goalLetter);

  return (
    <div>
    <Utilities />
    <button onClick={() => console.log("Button clicked!")}>My Button</button>
    <FindLetterGame
      goalLetterSound={goalLetterSound}
      gameNumber={gameNumber}
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
    </div>
  );
};

export default FifthScreeningGamePage;
