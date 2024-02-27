import React from 'react'
import { FiMic, FiPlay } from 'react-icons/fi';
type props = {word:string}
const WordComponent = ({word} : props) => {
  return (
    <div className="flex items-center justify-center mt-4 bg-[#CDEBC5] rounded-lg p-4 drop-shadow-lg">
      <FiMic className="ml-2 h-6 w-6" />
      <p className="ml-2 text-xl ">
        <strong>{word}</strong>
      </p>
      <button className="ml-auto bg-[#CDEBC5] text-[#3E4772]">start</button>
      <FiPlay className="ml-2 h-6 w-6" fill="#3182CE" />
    </div>
  );
}

export default WordComponent