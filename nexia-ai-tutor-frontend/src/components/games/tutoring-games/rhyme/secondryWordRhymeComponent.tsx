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
        <div
          className="animate__animated animate__zoomIn flex flex-col items-center justify-center"
          style={{ width: "130px", height: "130px" }}
        >
          <img
            className="rounded-md m-2 bg-blue-200"
            src={secondaryWord.data.image}
            alt={secondaryWord.data.word}
            style={{ width: "90px", height: "90px" }}
          />
          <div className="w-full px-2 flex justify-between items-center text-2xl font-bold">
            <SpeakerButtonComponent
              sound={secondaryWord.data.sound}
              from_google={true}
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
