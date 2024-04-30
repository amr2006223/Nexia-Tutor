"use client";
import { predictScreening } from "@/services/screening/screening";
import useFireworksForReport from "@/shared/hooks/useFireworksForReport";
import { useScreeningGamesStore } from "@/shared/state/screening-games";
import { Button, Typography } from "@mui/material";
import Pride from "react-canvas-confetti/dist/presets/pride";

const FinishScreeningTest = () => {
  const { playAnimations, onInit } = useFireworksForReport();
  const gamesData = useScreeningGamesStore();

  const handleSubmit = async () => {
    const response = await predictScreening(gamesData.games_result);
    console.log(response);

    if (response) {
      playAnimations();
    }
  };

  return (
    <div>
      <Typography
        component="h1"
        variant="h5"
        className="py-4 text-center bold-text"
      >
        Finish Screening Test
      </Typography>
      <Button
        type="submit"
        variant="contained"
        style={{
          backgroundColor: "#3E4772",
          color: "#CDEBC5",
        }}
        fullWidth
        onClick={handleSubmit}
      >
        Submit
      </Button>
      <Pride onInit={onInit} />
      <br />
    </div>
  );
};

export default FinishScreeningTest;
