import React from "react";
import WordBoxComponent from "./wordBox";

type BoxesMatrixComponentProps = {
  wordList: string[];
};

const BoxesMatrixComponent = (props: BoxesMatrixComponentProps) => {
  const wordList = props.wordList;
  // Calculate number of rows and columns
  const numRows = Math.ceil(Math.sqrt(wordList.length));
  const numCols = Math.ceil(wordList.length / numRows);

  // Slice the wordList into smaller arrays representing rows
  const rows = [];
  for (let i = 0; i < wordList.length; i += numCols) {
    rows.push(wordList.slice(i, i + numCols));
  }

  // Find the maximum word length
  const maxWordLength = Math.max(...wordList.map((word) => word.length));

  // Calculate a base unit size based on a desired font size and padding
  const baseUnit = 20; // Adjust this value to control box size

  // Calculate individual box width based on max word length and base unit
  const boxWidth = (maxWordLength + 2) * baseUnit; // Add 2 for padding

  return (
    <div className="flex flex-col">
      {rows.map((row, rowIndex) => (
        <div key={rowIndex} className="flex">
          {row.map((word, colIndex) => (
            <div
              key={`${rowIndex}-${colIndex}`}
              style={{
                marginRight: "10px",
                marginBottom: "10px",
              }}
            >
              <WordBoxComponent
                word={word}
                onClick={() => {
                  console.log(`Clicked on ${word}`);
                }}
                width={boxWidth}
              />
            </div>
          ))}
        </div>
      ))}
    </div>
  );
};

export default BoxesMatrixComponent;
