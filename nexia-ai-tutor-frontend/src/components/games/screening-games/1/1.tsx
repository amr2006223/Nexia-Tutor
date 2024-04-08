// "use client";
import React, { useEffect } from "react";
import BoxesMatrixToFindWordComponent from "./BoxesMatrixToFindWord";
import SpeakerButtonComponent from "@/shared/components/buttons/speakerButtonComponent";

type GameOneProps = {
  wordsList: string[];
  goalLetter: string;
  goalLetterSound: string;
  handleSuccess: () => void;
  handleFailure: () => void;
};

const GameOneComponent = (props: GameOneProps) => {
  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <div className="text-2xl font-bold text-center mb-4">
        Listen and find:{" "}
        <SpeakerButtonComponent
          sound={props.goalLetterSound}
          from_google={true}
          theme="dark"
        />
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
