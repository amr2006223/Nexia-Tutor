"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const SixthScreeningGamePage = () => {
  const wordsLists: string[] = [
    "gar",
    "gab",
    "gal",
    "gam",
    "gad",
    "gan",
    "gac",
    "gak",
    "gat",
    "gau",
    "gaf",
    "gah",
    "gaj",
    "gaw",
    "gae",
    "gai",
    "gao",
    "gap",
    "gaz",
    "gax",
    "gav",
    "gay",
    "gad",
    "gag",
    "gaw",
  ];

  const goalLetter = "gar";

  const nextGameLink = "7";

  return (
    <FindLetterGame
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default SixthScreeningGamePage;
