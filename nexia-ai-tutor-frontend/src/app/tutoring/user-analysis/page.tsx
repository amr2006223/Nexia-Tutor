import Navbar from "@/components/home/Navbar";
import { checkLoginAndGetUserName } from "@/services/auth/auth";
import { redirect } from "next/navigation";
import { checkIsUserTested } from "@/services/report/userTest";
import GamesStatsPage from "@/components/tutoring/user-analysis/gamesStats";
import { getGamesStats } from "@/services/user-analysis/getGameStats";
const page = async () => {
  const response = await checkIsUserTested();

  // if user did not take the test, redirect to home page
  if (response == "false") {
    redirect("/");
  }

  console.log("response", response);
  const gamesStas = await getGamesStats();
  console.log("gamesStas", gamesStas);
  if (gamesStas.length === 0) {
    redirect("/");
  }

  const { isLoggedIn, userName } = await checkLoginAndGetUserName();

  return (
    <div>
      <Navbar isLoggedIn={isLoggedIn} userName={userName} />
      <div className="pt-14">
        <GamesStatsPage />
      </div>
    </div>
  );
};

export default page;
