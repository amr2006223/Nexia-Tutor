import Navbar from "@/components/home/Navbar";
import { checkLoginAndGetUserName } from "@/services/auth/auth";
import { redirect } from "next/navigation";
import { checkIsUserTested } from "@/services/report/userTest";
import GamesStatsPage from "@/components/tutoring/user-analysis/gamesStats";
const page = async () => {
  const response = await checkIsUserTested();

  // if user did not take the test, redirect to home page
  if (response == "false") {
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
