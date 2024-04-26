"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const ThirdScreeningGamePage = () => {
  const wordsLists: string[] = [
    "b",
    "o",
    "b",
    "w",
    "b",
    "p",
    "i",
    "b",
    "t",
    "l",
    "r",
    "a",
    "q",
    "x",
    "b",
    "u",
    "b",
    "e",
    "b",
    "f",
    "b",
    "y",
    "b",
    "z",
    "b",
  ];

  const goalLetter = "b";

  const nextGameLink = "4";

  const gameNumber = 3;

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

export default ThirdScreeningGamePage;
