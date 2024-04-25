"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const FirstScreeningGamePage = () => {
  const wordsLists: string[] = ["e", "u", "u", "a", "e", "u", "i", "e", "a"];

  const goalLetter = "e";

  const nextGameLink = "2";

  return (
    <FindLetterGame
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default FirstScreeningGamePage;
