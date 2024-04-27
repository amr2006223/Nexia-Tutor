import React from "react";
import { ColorCodes } from "@/shared/colors";
import useTimer from "@/shared/hooks/useTimer";

type TimerComponentProps = {
  timeOnSeconds: number;
};

const TimerComponent: React.FC<TimerComponentProps> = ({ timeOnSeconds }) => {
  const timeLeft = timeOnSeconds;

  // Calculate minutes and seconds
  const minutes = Math.floor(timeLeft / 60);
  const seconds = timeLeft % 60;

  // Construct the display time string
  const displayTime = `${minutes < 10 ? `0${minutes}` : minutes}:${
    seconds < 10 ? `0${seconds}` : seconds
  }`;

  return (
    <div
      className={`px-4 py-2 rounded-xl text-3xl font-bold text-center items-center border-4 border-[${ColorCodes.purple}] bg-[${ColorCodes.lightGreen}]`}
    >
      {displayTime}
    </div>
  );
};

export default TimerComponent;
