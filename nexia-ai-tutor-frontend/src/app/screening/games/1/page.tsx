"use client";
// Import React and necessary components
import React from 'react';
import FindLetterGame from "@/components/games/screening-games/games-types/findLetterGame";
import { getTextSound } from "@/services/text-to-speech/textSound";

// Define the FirstScreeningGamePage component
const FirstScreeningGamePage = async () => {
  // Define initial data for the game
  const wordsLists: string[] = ["e", "u", "u", "a", "e", "u", "i", "e", "a"];
  const goalLetter = "e";
  const nextGameLink = "2";
  const gameNumber = 1;
  
  // Fetch sound for the goal letter asynchronously
  const goalLetterSound = await getTextSound(goalLetter);

  // Return the JSX including Utilities component and FindLetterGame component
  return (
    <div>
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

// Export the FirstScreeningGamePage component
export default FirstScreeningGamePage;

