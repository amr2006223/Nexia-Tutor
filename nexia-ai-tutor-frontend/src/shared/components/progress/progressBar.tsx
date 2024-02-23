import { CircularProgress } from "@mui/material";
import React from "react";

const ProgressBarComponent = () => {
  return (
    <div
      className="flex justify-center items-center h-screen"
      style={{ width: "100%" }}
    >
      <CircularProgress
        style={{ width: "100px", height: "100px" }}
        color="primary"
      />
    </div>
  );
};

export default ProgressBarComponent;
