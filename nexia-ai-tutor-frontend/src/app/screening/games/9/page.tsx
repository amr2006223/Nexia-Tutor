"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import Utilities from "@/components/home/utilities/utilities";
import { getTextSound } from "@/services/text-to-speech/textSound";

const NinthScreeningGamePage = async () => {
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

  const gameNumber = 9;

  const goalLetterSound = await getTextSound(goalLetter);

  return (
    <div>
      <Utilities />

      <FindLetterGame
        goalLetterSound={goalLetterSound}
        gameNumber={gameNumber}
        goalLetter={goalLetter}
        wordsList={wordsLists}
        nextGameLink={nextGameLink}
        isLastGame={true}
      />
    </div>
  );
};

export default NinthScreeningGamePage;
