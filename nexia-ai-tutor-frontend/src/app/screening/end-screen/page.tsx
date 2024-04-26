"use client";
import React, { useState } from "react";
import { Button, Container, Typography } from "@mui/material";
import Navbar from "@/components/home/Navbar";
import Swal from "sweetalert2";
import "animate.css";
import { TConductorInstance } from "react-canvas-confetti/dist/types";
import Pride from "react-canvas-confetti/dist/presets/pride";
import { useRouter } from "next/navigation";
import { getReport } from "@/services/report/downloadReport";
import { getTokenValue } from "@/services/auth/auth";
import { useScreeningGamesStore } from "@/shared/state/screening-games";
import { predictScreening } from "@/services/screening/screening";

const ScreeningEndScreenPage: React.FC = () => {
  const gamesData = useScreeningGamesStore();
  const router = useRouter();
  const [conductor, setConductor] = useState<TConductorInstance>();

  const handleSubmit = async () => {
    const response = await predictScreening(gamesData.games_result);
    console.log(response);

    if (response) {
      playAnimations();
    }
  };

  const downloadReport = async () => {
    const token = await getTokenValue();
    if (token) await getReport(token);
    else console.error("Token is undefined");
  };

  const playAnimations = () => {
    firewroks();
    Swal.fire({
      icon: "success",
      width: 600,
      title: "Congratulations! You have completed the test",
      showCancelButton: true,
      confirmButtonText: "Start learning",
      cancelButtonText: "Download report",
      showClass: {
        popup: `
          animate__animated
          animate__rollIn
          animate__slow
        `,
      },
      hideClass: {
        popup: `
          animate__animated
          animate__backOutDown
          animate__slow
        `,
      },
      customClass: {
        confirmButton: "swal2-confirm-custom",
        cancelButton: "swal2-cancel-custom",
      },
      // closeOnCancel:false,
      allowOutsideClick: false,
      allowEscapeKey: false,
    }).then((result) => {
      if (result.isConfirmed) {
        router.push("/myLearning");
      } else {
        downloadReport();
        router.push("/myLearning");
      }
      onPause();
    });
  };

  const firewroks = () => {
    conductor?.run({ speed: 25, duration: 8000 });
  };

  const onInit = ({ conductor }: { conductor: TConductorInstance }) => {
    setConductor(conductor);
  };

  const onPause = () => {
    conductor?.pause();
  };

  return (
    <>
      <Navbar />

      <Container component="main" maxWidth="xs" className="pt-14">
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
        <br />
        {gamesData.games_result.map((gameArray, index) => (
          <div key={index}>
            <div>game - {index + 1}</div>
            {gameArray.map((game, index2) => (
              <div key={index2}>{game} , </div>
            ))}
            <br />
            <br />
          </div>
        ))}
        <Pride onInit={onInit} />
      </Container>
    </>
  );
};

export default ScreeningEndScreenPage;