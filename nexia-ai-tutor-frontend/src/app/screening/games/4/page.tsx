"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import Utilities from "@/components/home/utilities/utilities";
import { getTextSound } from "@/services/text-to-speech/textSound";

const FourthScreeningGamePage = async () => {
  const wordsLists: string[] = [
    "d",
    "m",
    "d",
    "l",
    "d",
    "k",
    "i",
    "d",
    "n",
    "h",
    "e",
    "d",
    "f",
    "d",
    "o",
    "d",
    "g",
    "d",
    "p",
    "d",
    "r",
    "d",
    "q",
    "d",
    "s",
    "d",
    "t",
    "d",
    "u",
    "d",
    "v",
    "d",
    "w",
    "d",
    "x",
    "d",
  ];

  const goalLetter = "d";

  const nextGameLink = "5";

  const gameNumber = 4;

  const goalLetterSound = await getTextSound(goalLetter);

  return (
    <div>
      <Utilities />

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

export default FourthScreeningGamePage;
