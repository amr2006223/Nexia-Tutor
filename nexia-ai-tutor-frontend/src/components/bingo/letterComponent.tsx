"use client";
import { LetterDetail } from "@/types/bingo";
import React, { useEffect, useState } from "react";

type Props = {
  letter: LetterDetail;
  pressLetter: () => void;
};

const LetterComponentBingo = ({ letter, pressLetter }: Props) => {
  const [color, setColor] = useState("from-lime-600 to-green-300");

  useEffect(() => {
    if (letter.isClicked) {
      setColor("from-yellow-300 to-yellow-600");
    }
  }, [letter]);

  return (
    <div
      className={`py-6 rounded-2xl text-center cursor-pointer bg-gradient-to-b ${color}`}
      onClick={pressLetter}
    >
      <div className="text-4xl font-bold">
        <div>{letter.value}</div>
      </div>
    </div>
  );
};

export default LetterComponentBingo;
