"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const SecondScreeningGamePage = () => {
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

  return (
    <FindLetterGame
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default SecondScreeningGamePage;
