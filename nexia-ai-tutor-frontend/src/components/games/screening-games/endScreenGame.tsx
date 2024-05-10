import { Box } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

type EndScreenGameComponentProps = {
  nextGameLink: string;
  lastGame: boolean;
};

const EndScreenGameComponent = (props: EndScreenGameComponentProps) => {
  const router = useRouter();
  const [showAnimation, setShowAnimation] = useState(false);

  useEffect(() => {
    // Trigger animation after component is mounted
    setShowAnimation(true);
  }, []);

  const handleNextGameClick = () => {
    if (props.lastGame) {
      router.push(`/screening/end-screen`);
    } else {
      router.push(`/screening/games/${props.nextGameLink}`);
    }
  };

  return (
    <div
      style={{
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        width: "30%",
        height: "65%",
        background: "#e6f7e1",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        boxShadow: "0 0 10px 0 rgba(0, 0, 0, 0.1)",
        zIndex: 100,
        padding: "20px",
        borderRadius: "10px",
      }}
    >
      <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
        <img
          src="/assets/images/excited-blue-potato.png"
          alt="Hero"
          style={{
            width: "100px",
            height: "auto",
            marginBottom: "20px",
            animation: showAnimation ? "stirAnimation 0.5s ease-in-out infinite" : "none",
          }}
        />
        Good Job!
        <Box sx={{ marginBottom: "20px" }}>
          <button
            className="bg-primary text-light rounded-md p-2 hover:bg-primaryLight"
            onClick={handleNextGameClick}
          >
            {"Go to Next Game"}
          </button>
        </Box>
      </div>
      {/* Define the keyframe animation */}
      <style jsx>{`
        @keyframes stirAnimation {
          0% {
            transform: rotate(-3deg);
          }
          50% {
            transform: rotate(3deg);
          }
          100% {
            transform: rotate(-3deg);
          }
        }
      `}</style>
    </div>
  );
};

export default EndScreenGameComponent;
