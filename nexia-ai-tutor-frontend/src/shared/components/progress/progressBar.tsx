import { Box, CircularProgress, Typography } from "@mui/material";
import React from "react";

const ProgressBarComponent = () => {
  return (
    // <div
    //   className="flex justify-center items-center h-screen "
    //   style={{ width: "100%" }}
    // >
    //   <CircularProgress
    //     style={{ width: "100px", height: "100px" }}
    //     color="primary"
    //   />
    // </div>
    <div
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        width: "100%",
        height: "100%",
        background: "white",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
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
        <CircularProgress
          size={300}
          z-index={0}
          sx={{
            background: "white",
          }}
        />
        <Typography
          variant="h1"
          sx={{
            color: "#1769aa",
            marginTop: "30px",
          }}
        >
          Loading
        </Typography>
      </Box>
    </div>
  );
};

export default ProgressBarComponent;
