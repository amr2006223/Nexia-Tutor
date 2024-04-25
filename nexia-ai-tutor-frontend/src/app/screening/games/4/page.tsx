"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const FourthScreeningGamePage = () => {
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

  return (
    <FindLetterGame
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default FourthScreeningGamePage;
