"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import { getTextSound } from "@/services/text-to-speech/textSound";

const SecondScreeningGamePage = async () => {
  const wordsLists: string[] = [
    "g",
    "a",
    "g",
    "t",
    "g",
    "h",
    "i",
    "g",
    "r",
    "g",
    "t",
    "g",
    "h",
    "i",
    "g",
    "r",
  ];

  const goalLetter = "g";

  const nextGameLink = "3";

  const gameNumber = 2;
  const goalLetterSound = await getTextSound(goalLetter);

  return (
    <FindLetterGame
      goalLetterSound={goalLetterSound}
      gameNumber={gameNumber}
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default SecondScreeningGamePage;
