import Navbar from "@/components/home/Navbar";
import LearningPage from "@/components/myLearning/page";
import { checkLoginAndGetUserName } from "@/services/auth/auth";

export default async function TodayLesson() {
  const { isLoggedIn, userName } = await checkLoginAndGetUserName();

  return (
    <main>
      <Navbar isLoggedIn={isLoggedIn} userName={userName} />
      <LearningPage />
    </main>
  );
}
