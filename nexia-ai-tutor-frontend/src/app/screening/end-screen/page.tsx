import React from "react";
import { Container } from "@mui/material";
import GamesDataReport from "@/components/screening/gamesDataReport";
import FinishScreeningTest from "@/components/screening/finishScreeningTest";
import { checkLoginAndGetUserName } from "@/services/auth/auth";
import Navbar from "@/components/home/Navbar";
import Utilities from "@/components/home/utilities/utilities";

const ScreeningEndScreenPage = async () => {
  const { isLoggedIn, userName } = await checkLoginAndGetUserName();
  return (
    <div>
      <Navbar isLoggedIn={isLoggedIn} userName={userName} />
      <Utilities />
      <Container component="main" maxWidth="xs" className="pt-14">
        <FinishScreeningTest />
        <GamesDataReport />
      </Container>
    </div>
  );
};

export default ScreeningEndScreenPage;
