"use client";
import CorrectButtonComponent from "@/shared/components/buttons/correctButtonComponent";
import SpeakerButtonComponent from "@/shared/components/buttons/speakerButtonComponent";
import { RhymingWord } from "@/types/tutoring-games/rhyme/rhyme";
import React from "react";

type Props = {
  index: number;
  secondaryWord: RhymingWord;
  checkFunction: Function;
};

const SecondryWordRhymeComponent = ({
  index,
  secondaryWord,
  checkFunction,
}: Props) => {
  return (
    <div
      key={index}
      className="flex flex-col items-center justify-center m-2 bg-green-100"
    >
      {secondaryWord.showImage && (
        <div className="flex flex-col items-center justify-center transition-opacity duration-500 ease-in-out">
          <img
            className="rounded-md m-2 bg-blue-200"
            src={secondaryWord.data.image}
            alt={secondaryWord.data.word}
            width={100}
            height={100}
          />
          <div className="w-full px-2 flex justify-between items-center text-2xl font-bold">
            <SpeakerButtonComponent
              sound={secondaryWord.data.sound}
              from_google={false}
              theme="dark"
            />

            <CorrectButtonComponent
              theme="dark"
              checkFunction={checkFunction}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default SecondryWordRhymeComponent;
