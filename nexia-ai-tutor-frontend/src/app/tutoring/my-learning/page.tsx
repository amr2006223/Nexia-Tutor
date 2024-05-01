import Navbar from "@/components/home/Navbar";
import MyLearningPage from "@/components/tutoring/my-learning/myLearningPage";
import { checkLoginAndGetUserName } from "@/services/auth/auth";

const page = async () => {
  const { isLoggedIn, userName } = await checkLoginAndGetUserName();

  return (
    <div>
      <Navbar isLoggedIn={isLoggedIn} userName={userName} />
      <div className="pt-14">
        <MyLearningPage username={userName} />
      </div>
    </div>
  );
};

export default page;
