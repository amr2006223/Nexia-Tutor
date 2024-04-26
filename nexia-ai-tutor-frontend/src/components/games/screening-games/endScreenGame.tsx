import { Box } from "@mui/material";
import React from "react";
import { useRouter } from "next/navigation";
type EndScreenGameComponentProps = {
  nextGameLink: string;
  lastGame: boolean;
};

const EndScreenGameComponent = (props: EndScreenGameComponentProps) => {
  const router = useRouter();
  return (
    <div
      style={{
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        width: "50%",
        height: "85%",
        background: "white",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        boxShadow: "0 0 10px 0 rgba(0, 0, 0, 0.1)",
        zIndex: 100,
      }}
    >
      <Box
        sx={{
          position: "relative",
          zIndex: 0,
          background: "white",
        }}
      >
        <button
          className="btn btn-primary select-none"
          onClick={() => {
            if (props.lastGame) {
              router.push(`/screening/end-screen`);
              return;
            }
            router.push(`/screening/games/${props.nextGameLink}`);
          }}
        >
          Go to Next Game
        </button>
      </Box>
    </div>
  );
};

export default EndScreenGameComponent;
