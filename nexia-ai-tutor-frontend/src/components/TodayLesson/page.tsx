import { useLessonStore } from "@/shared/state/uploadedLessons";
import Head from "next/head";
import Link from "next/link";
import { FiPlay, FiPlus, FiSearch, FiMic } from "react-icons/fi";
import WordComponent from "./wordComponent";
import { useEffect, useState } from "react";

export default function TodayLesson() {
  const [loading, setLoading] = useState(true);
  const [fileName,setFilename] = useState("")
  const [keywords, setkeywords] = useState([""]);
  const lessonState = useLessonStore();
    useEffect(() => {
       const fileName = lessonState.fileName;
       const keywords = lessonState.keywords;
       const parts = fileName.split(".");
       // Get the substring
       setFilename(parts[0]);
       setkeywords(keywords)
       setLoading(false)
    }, []);
 
  return (
    <>
      <Link
        className=" bg-[#3E4772] font-bold text-base p-3 m-2"
        href={"/myLearning"}
        style={{
          backgroundColor: "#3E4772",
          color: "#CDEBC5",
        }}
      >
        Back
      </Link>
      <div className="bg-[#CDEBC5] flex flex-col items-center justify-center min-h-screen py-6 ">
        <div className="flex items-center">
          <h1 className="text-2xl font-bold mr-4">{fileName}</h1>
        </div>
        <p className="mt-2 text-xl text-[#5C7C54]">
          Words of {fileName} are ..
        </p>
        {/* Rectangle  */}
        <div className="w-full max-w-xl mt-8 p-8 bg-[#E3FFDC] rounded-lg">
          <div className="flex justify-between items-center">
            <h2 className="text-xl font-semibold text-[#3E4772] text-center">
              Lesson 1{" "}
            </h2>
            {/* {"\n"} */}
            <div></div>
          </div>
          {/* Scrollable area */}
          <div className="mt-4 h-96 overflow-y-auto">
            {/* word */}
            {!loading &&
              keywords.map(function (word, index) {
                return <WordComponent word={word} />;
              })}

            {/* more words */}
          </div>
        </div>
      </div>
    </>
  );
}
