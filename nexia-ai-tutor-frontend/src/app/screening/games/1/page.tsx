"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const FirstScreeningGamePage = () => {
  const wordsLists: string[] = ["e", "u", "u", "a", "e", "u", "i", "e", "a"];
  const goalLetter = "e";
  const nextGameLink = "2";
  const gameNumber = 1;

  return (
    <FindLetterGame
      gameNumber={gameNumber}
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default FirstScreeningGamePage;
