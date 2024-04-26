"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import { getTextSound } from "@/services/text-to-speech/textSound";

const EighthScreeningGamePage = async () => {
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

  const gameNumber = 8;

  const goalLetterSound = await getTextSound(goalLetter);

  return (
    <FindLetterGame
      goalLetterSound={goalLetterSound}
      gameNumber={gameNumber}
      goalLetter={goalLetter}
      wordsList={wordsLists}
      nextGameLink={nextGameLink}
      isLastGame={false}
    />
  );
};

export default EighthScreeningGamePage;
