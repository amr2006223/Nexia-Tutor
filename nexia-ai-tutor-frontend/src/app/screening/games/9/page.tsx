"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";

const NinthScreeningGamePage = () => {
  const wordsLists: string[] = [
    "glis",
    "glir",
    "glin",
    "gris",
    "gril",
    "grin",
    "glir",
    "glil",
    "glig",
    "glie",
    "glip",
    "glid",
    "gliz",
    "glif",
    "glia",
    "gliv",
    "glim",
    "gliv",
    "glis",
    "glir",
    "glin",
    "gris",
    "gril",
    "grin",
    "gliv",
  ];

  const goalLetter = "glis";

  const nextGameLink = "10";

  return (
    <FindLetterGame
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={true}
    />
  );
};

export default NinthScreeningGamePage;
