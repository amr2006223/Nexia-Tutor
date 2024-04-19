"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const FifthScreeningGamePage = () => {
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

  return (
    <FindLetterGame
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default FifthScreeningGamePage;
