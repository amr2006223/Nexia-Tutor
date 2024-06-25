import { Button } from "@mui/material";
import React from "react";
import { useRouter } from "next/navigation";

const BackButtonComponent = () => {
  const router = useRouter();
  return (
    <div>
      <Button
        onClick={() => router.back()}
        className="font-bold text-base"
        variant="contained"
        style={{
          backgroundColor: "#3E4772",
          color: "#CDEBC5",
          position: "absolute",
          top: "10px",
          left: "10px",
        }}
      >
        Back
      </Button>
    </div>
  );
};

export default BackButtonComponent;
