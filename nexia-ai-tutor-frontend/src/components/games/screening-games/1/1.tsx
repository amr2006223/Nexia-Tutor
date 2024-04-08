// "use client";
import React from "react";
import BoxesMatrixToFindWordComponent from "./BoxesMatrixToFindWord";

type GameOneProps = {
  wordsList: string[];
  goalLetter: string;
  handleSuccess: () => void;
  handleFailure: () => void;
};

const GameOneComponent = (props: GameOneProps) => {
  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <div className="text-2xl font-bold text-center mb-4">
        Find the word: {props.goalLetter}
      </div>
      <BoxesMatrixToFindWordComponent
        wordList={props.wordsList}
        goalLetter={props.goalLetter}
        handleSuccess={props.handleSuccess}
        handleFailure={props.handleFailure}
      />
    </div>
  );
};

export default GameOneComponent;
