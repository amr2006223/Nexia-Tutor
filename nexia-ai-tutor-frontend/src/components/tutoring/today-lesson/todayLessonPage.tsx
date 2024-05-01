"use client";
import BackButton from "./components/backButton";
import FileDetails from "./components/fileDetails";
import FileWords from "./components/fileWords";

const TodayLessonPage = () => {
  return (
    <div>
      <BackButton />

      <div className="bg-[#CDEBC5] flex flex-col items-center justify-center min-h-screen py-6 ">
        <FileDetails />
        <FileWords />
      </div>
      
    </div>
  );
};

export default TodayLessonPage;
