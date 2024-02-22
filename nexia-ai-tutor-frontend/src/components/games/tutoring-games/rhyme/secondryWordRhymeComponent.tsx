"use client";
import CorrectButtonComponent from "@/shared/components/buttons/correctButtonComponent";
import SpeakerButtonComponent from "@/shared/components/buttons/speakerButtonComponent";
import { RhymingWord } from "@/types/tutoring-games/rhyme/rhyme";
import React from "react";
import "animate.css";
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
      className=" flex flex-col items-center justify-center m-2 bg-green-100 "
    >
      {/* <h1 className="animate__animated animate__bounce">An animated element</h1> */}

      {secondaryWord.showImage && (
        <div className="animate__animated animate__zoomIn flex flex-col items-center justify-center">
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
