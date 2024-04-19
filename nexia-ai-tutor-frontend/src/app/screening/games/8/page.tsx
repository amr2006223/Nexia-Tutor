"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const EighthScreeningGamePage = () => {
  const wordsLists: string[] = [
    "bla",
    "ble",
    "bli",
    "blo",
    "blu",
    "blm",
    "bln",
    "blp",
    "blq",
    "blr",
    "bls",
    "blt",
    "blv",
    "blw",
    "blx",
    "bly",
    "blz",
    "blb",
    "blc",
    "bld",
    "ble",
    "blf",
    "blg",
    "blh",
    "bli",
  ];

  const goalLetter = "bla";

  const nextGameLink = "9";

  return (
    <FindLetterGame
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default EighthScreeningGamePage;
