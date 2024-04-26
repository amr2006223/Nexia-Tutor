"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import { getTextSound } from "@/services/text-to-speech/textSound";

const FirstScreeningGamePage = async () => {
  const wordsLists: string[] = ["e", "u", "u", "a", "e", "u", "i", "e", "a"];
  const goalLetter = "e";
  const nextGameLink = "2";
  const gameNumber = 1;
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

export default FirstScreeningGamePage;
