import Navbar from "@/components/home/Navbar";
import TodayLessonPage from "@/components/tutoring/today-lesson/todayLessonPage";
import { checkLoginAndGetUserName } from "@/services/auth/auth";

const page = async () => {
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
