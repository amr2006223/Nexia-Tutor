import React from "react";
import { ColorCodes } from "@/shared/colors";

type WordBoxComponentProps = {
  word: string;
  width: number;
  onClick: () => void;
};

const WordBoxComponent = (props: WordBoxComponentProps) => {
  return (
    <div
      onClick={props.onClick}
      style={{ width: props.width }}
      className={`px-4 py-2 rounded-xl text-4xl font-bold text-center items-center cursor-pointer border-4 border-[${ColorCodes.purple}] bg-[${ColorCodes.lightGreen}]`}
    >
      {props.word}
    </div>
  );
};

export default WordBoxComponent;
