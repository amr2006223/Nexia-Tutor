import React, { useState, useEffect } from "react";
import { ColorCodes } from "@/shared/colors";
import useTimer from "@/shared/hooks/useTimer";

// Inline SVG markup
const clockSvg = (
  <svg
    width="90px"
    height="90px"
    viewBox="0 0 48 48"
    xmlns="http://www.w3.org/2000/svg"
  >
    <title>70 Basic icons by Xicons.co</title>
    <path
      d="M24,46A22,22,0,1,1,46,24,22,22,0,0,1,24,46Z"
      fill="#38b1e7"
    />
    <circle cx="24" cy="24" r="16" fill="#b7e3f6" />
    <path
      d="M29.09,29.78a2,2,0,0,1-1.19-.39l-5.09-3.78A2,2,0,0,1,22,24V16a2,2,0,0,1,4,0v7l4.29,3.18A2,2,0,0,1,29.09,29.78Z"
      fill="#495660"
    />
  </svg>
);

type TimerComponentProps = {
  timeOnSeconds: number;
};

const TimerComponent: React.FC<TimerComponentProps> = ({ timeOnSeconds }) => {
  const [animationState, setAnimationState] = useState<
    "zoomed" | "shaken" | "pulsed"
  >("zoomed");
  const timeLeft = timeOnSeconds;

  // Calculate minutes and seconds
  const minutes = Math.floor(timeLeft / 60);
  const seconds = timeLeft % 60;

  // Construct the display time string
  const displayTime = `${minutes < 10 ? `0${minutes}` : minutes}:${
    seconds < 10 ? `0${seconds}` : seconds
  }`;

  useEffect(() => {
    const interval = setInterval(() => {
      setAnimationState((prevState) => {
        switch (prevState) {
          case "zoomed":
            return "shaken";
          case "shaken":
            return "pulsed";
          default:
            return "zoomed";
        }
      });
    }, 800);

    return () => clearInterval(interval);
  }, []); // Empty dependency array ensures the effect runs only once

  return (
    <div
      className="clock-container"
      style={{
        position: "relative", // Position relative for absolute positioning of clock icon
        border: `4px solid ${ColorCodes.purple}`,
        background:
          animationState === "pulsed" ? ColorCodes.white : ColorCodes.lightGreen,
        borderRadius: "10px",
        padding: "10px",
        display: "inline-block",
        fontSize: "3rem",
        fontFamily: "Arial, sans-serif",
        boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        transform:
          animationState === "zoomed"
            ? "scale(1.2) translate(-2px, -2px)"
            : "scale(1) translate(0, 0)", // Zoom and shake effect
        transition: "transform 0.5s ease-in-out, background 0.5s ease-in-out", // Smooth transition
      }}
    >
      {/* Clock icon positioned absolutely at the bottom vertex */}
      <div
        style={{
          position: "absolute",
          bottom: "-20px",
          right: "135%",
          marginBottom: "85px",
          // transform: "translateX(-100%)", // Center horizontally
          width: "24px",
          height: "24px",
        }}
      >
        {clockSvg}
      </div>
      {displayTime}
    </div>
  );
};

export default TimerComponent;
