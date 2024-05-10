import Navbar from "@/components/home/Navbar";
import TodayLessonPage from "@/components/tutoring/today-lesson/todayLessonPage";
import { checkLoginAndGetUserName } from "@/services/auth/auth";
import { redirect } from "next/navigation";
import { checkIsUserTested } from "@/services/report/userTest";
import {
  getFileNameServerSide,
  getKeywordsServerSide,
} from "@/services/files/fileName";
import { getGamesFromCookies } from "@/services/games/getGamesForUser";

const page = async () => {
  const response = await checkIsUserTested();

  // if user did not take the test, redirect to home page
  if (response == "false") {
    redirect("/");
  }

  const fileName = await getFileNameServerSide();
  const keywords = await getKeywordsServerSide();
  if (!fileName || keywords.length === 0) {
    redirect("/tutoring/my-learning");
  }
  const games = await getGamesFromCookies();

  const { isLoggedIn, userName } = await checkLoginAndGetUserName();

  return (
    <div>
      <Navbar isLoggedIn={isLoggedIn} userName={userName} />
      <div className="pt-14">
        <TodayLessonPage
          fileName={fileName}
          keywords={keywords}
          games={games}
        />
      </div>
    </div>
  );
};

export default page;
