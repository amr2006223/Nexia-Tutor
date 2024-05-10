import Navbar from "@/components/home/Navbar";
import TodayLessonPage from "@/components/tutoring/today-lesson/todayLessonPage";
import { checkLoginAndGetUserName } from "@/services/auth/auth";
import { redirect } from "next/navigation";
import { checkIsUserTested } from "@/services/report/userTest";

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
        <TodayLessonPage />
      </div>
    </div>
  );
};

export default page;
