"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const SeventhScreeningGamePage = () => {
  const wordsLists: string[] = [
    "pla",
    "ple",
    "pli",
    "plo",
    "plu",
    "plb",
    "plc",
    "pld",
    "ple",
    "plf",
    "plg",
    "plh",
    "pli",
    "plj",
    "plk",
    "pll",
    "plm",
    "pln",
    "plo",
    "plp",
    "plq",
    "plr",
    "pls",
    "plt",
    "plu",
  ];

  const goalLetter = "pla";

  const nextGameLink = "8";

  return (
    <FindLetterGame
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default SeventhScreeningGamePage;
