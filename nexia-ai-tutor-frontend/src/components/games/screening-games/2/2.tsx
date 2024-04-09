// "use client";
import React, { useEffect } from "react";
import BoxesMatrixToFindWordComponent from "../BoxesMatrixToFindWord";

type GameTwoProps = {
  wordsList: string[];
  goalLetter: string;
  handleSuccess: () => void;
  handleFailure: () => void;
};

const GameTwoComponent = (props: GameTwoProps) => {
  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <div className="text-2xl font-bold text-center mb-4">
        Find the Diffrent Letter
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

export default GameTwoComponent;
