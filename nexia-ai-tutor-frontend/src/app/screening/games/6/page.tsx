"use client";
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import Utilities from "@/components/home/utilities/utilities";
import { getTextSound } from "@/services/text-to-speech/textSound";

const SixthScreeningGamePage = async () => {
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

  const gameNumber = 6;

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

export default SixthScreeningGamePage;
