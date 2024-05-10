"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import Utilities from "@/components/home/utilities/utilities";
import { getTextSound } from "@/services/text-to-speech/textSound";

const SeventhScreeningGamePage = async () => {
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

  const gameNumber = 7;

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
        isLastGame={false}
      />
    </div>
  );
};

export default SeventhScreeningGamePage;
