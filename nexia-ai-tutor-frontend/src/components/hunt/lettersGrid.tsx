"use client";
import React from "react";
import ProgressBarComponent from "../../shared/progress/progressBar";

type LettersGridProps = {
  onClickLetter: (letter: string) => void;
};

const LettersGrid = ({ onClickLetter }: LettersGridProps) => {
  let alphabet = [];
  const generateAlphabet = () => {
    const alphabet = [];
    for (let i = 0; i < 26; i++) {
      alphabet.push(String.fromCharCode(65 + i));
    }
    return alphabet;
  };

  alphabet = generateAlphabet();

  return (
    <div>
      {alphabet.length > 0 ? (
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(13, 50px)",
            gap: "14px",
          }}
        >
          {alphabet.map((letter) => (
            <div
              key={letter}
              className="bg-gradient-to-br from-lime-600 to-green-300"
              style={{
                border: "1px solid #ccc",
                borderRadius: "5px",
                padding: "10px",
                textAlign: "center",
                cursor: "pointer",
                fontWeight: "bold",
                fontSize: "1.5rem",
              }}
              onClick={() => onClickLetter(letter)}
            >
              {letter}
            </div>
          ))}
        </div>
      ) : (
        <ProgressBarComponent />
      )}
    </div>
  );
};

export default LettersGrid;
