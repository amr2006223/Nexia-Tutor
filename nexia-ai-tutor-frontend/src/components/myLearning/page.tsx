"use client";
import Head from "next/head";
import { FiFileText, FiPlay, FiPlus, FiSearch, FiTrash2 } from "react-icons/fi";
import { useEffect, useState } from "react";
import { useLessonStore } from "@/shared/state/uploadedLessons";
import { keyWordExtractionFromFile } from "@/services/keywords/keyWordExtraction";
import { useRouter } from "next/navigation";
import {
  appendFilesToLocalStorage,
  getFilesFromLocalStorage,
  removeFilesFromLocalStorage,
} from "@/services/files/fileUpload";
import ProgressBarComponent from "@/shared/components/progress/progressBar";
import { lessonFileData } from "@/types/lessons/fileData";
import { useUserStore } from "@/shared/state/user";

export default function LearningPage(): JSX.Element {
  const router = useRouter();
  const userState = useUserStore();
  const lessonState = useLessonStore();

  const [uploadedFiles, setUploadedFiles] = useState<lessonFileData[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const files = getFilesFromLocalStorage();
    setUploadedFiles(files);
    setLoading(false);
  }, []);

  const handleKeyowrdExtraction = async (
    fileData: lessonFileData
  ): Promise<void> => {
    try {
      await lessonState.setLessonDetails(fileData.name, fileData.keywords);
      // console.log("Keywords:", keywords);
      router.push("/tutoring/TodayLesson");
    } catch (error) {
      console.error("Error uploading PDF:", error);
    }
  };

  const getFileKeyWords = async (file: File) => {
    try {
      // Create FormData object to send file
      const formData = new FormData();
      formData.append("file", file); // Append file data with name

      // Send POST request to backend endpoint
      const keywords = await keyWordExtractionFromFile(formData);
      return keywords;
    } catch (error) {
      // Handle errors
      console.error("Error uploading PDF:", error);
    }
  };

  const handleFileUpload = async (
    event: React.ChangeEvent<HTMLInputElement>
  ): Promise<void> => {
    const files = event.target.files;
    if (files) {
      const pdfFiles = Array.from(files).filter(
        (file) => file.type === "application/pdf"
      );

      // 1. get file data from api
      const keyWords: string[] = await getFileKeyWords(pdfFiles[0]);

      const fileData: lessonFileData = {
        name: pdfFiles[0].name,
        keywords: keyWords,
      };

      // 2. save file data to local storage
      await appendFilesToLocalStorage(fileData);
      // 3. get file data from local storage

      const allFiles = getFilesFromLocalStorage();
      setUploadedFiles(allFiles);
    }
  };

  const handleRemoveFile = async (index: number): Promise<void> => {
    await removeFilesFromLocalStorage(index);
    const files = getFilesFromLocalStorage();
    setUploadedFiles(files);
  };

  return (
    <>
      {loading ? (
        <ProgressBarComponent />
      ) : (
        <div className="bg-[#CDEBC5] flex flex-col items-center justify-center min-h-screen py-6 ">
          <Head>
            <title>MyLearning</title>
          </Head>
          <div className="flex items-center">
            <h1 className="text-2xl font-bold mr-4">
              Welcome Back, {userState.user.username}
            </h1>
            <img
              src="/assets/images/potato-heart.png"
              alt="Potato Heart"
              className="w-24 h-16"
            />
          </div>

          {/* Rectangle 1 */}
          {/* <div className="w-full max-w-2xl mt-8 p-8 bg-[#E3FFDC] rounded-lg text-2xl font-bold flex-1 justify-between items-center">
            <div className="flex items-center">
              <div className="bg-green-500 rounded-2xl p-4 px-12 flex items-center">
                <div className="text-center">
                  <h1 className="text-lg flex-row inline">Your Coins</h1>
                  <p>75</p>
                </div>
                <img
                  src="/assets/images/coins.png"
                  alt="Coins"
                  className="h-12 w-auto ml-2"
                />
              </div>
              <button className="mb-12 ml-2 hover:bg-[#CDEBC5] bg-[#175EA7] bg-opacity-20 border-[#3E4772] border-2 text-[#3E4772] px-2 py-2 rounded-xl font-bold hover:bg-opacity-40 transition duration-300 ease-in-out flex items-center">
                <FiFileText className="text-2xl mr-2" />
                <span className="mr-4 font-bold text-sm">Test Report</span>
              </button>
            </div>

            <div className="flex items-center space-x-4">
              <button className="mt-4 hover:bg-[#85B6E8] bg-[#85B6E8] bg-opacity-50 border-[#85B6E8] border-2 text-[#3E4772] px-2 py-2 rounded-2xl font-bold hover:bg-opacity-40 transition duration-300 ease-in-out flex items-center">
                <FiPlay className="text-2xl" />
                <span className=" ml-1 font-bold text-sm">
                  Continue where you left
                </span>
              </button>
            </div>
          </div> */}

          {/* Rectangle 2 */}
          <div className="w-full max-w-2xl mt-8 p-8 bg-[#E3FFDC] rounded-lg">
            <div className="flex justify-between items-center">
              <h2 className="text-xl font-semibold text-[#3E4772]">
                Your Contents
              </h2>
              <div className="flex items-center bg-[#CDEBC5] rounded-full border-b-2 border-[#3E4772]">
                <FiSearch className="ml-2 h-4 w-4 text-[#3E4772]" />
                <input
                  type="text"
                  placeholder="Search..."
                  className="rounded-full bg-transparent pl-4 outline-none"
                />
              </div>
              <label htmlFor="file-upload" className="cursor-pointer">
                <FiPlus className="h-6 w-6 text-[#3E4772] hover:text-[#3E4772]" />
                <input
                  id="file-upload"
                  type="file"
                  className="hidden"
                  onChange={handleFileUpload}
                  accept="application/pdf"
                />
              </label>
            </div>
            <hr className="mt-2 border-2 rounded-full border-[#3E4772]" />

            {/* Scrollable area */}
            <div className="mt-4 h-96 overflow-y-auto">
              {/* Render uploaded files dynamically */}
              {uploadedFiles.map((file, index) => (
                <div
                  key={index}
                  className="flex items-center mt-4 bg-[#CDEBC5] rounded-lg p-4 drop-shadow-lg"
                >
                  <p className="ml-2">{file.name}</p>
                  <button
                    className="ml-auto bg-[#CDEBC5] text-[#3E4772]"
                    onClick={() => handleKeyowrdExtraction(file)}
                  >
                    Continue
                  </button>
                  {/* You can render different icons based on the file type */}

                  <FiFileText className="ml-2 h-6 w-6" fill="#3182CE" />

                  <button
                    className="ml-2 text-red-500"
                    onClick={() => handleRemoveFile(index)}
                  >
                    <FiTrash2 className="h-5 w-5" />{" "}
                    {/* Increased size of the trash icon */}
                  </button>
                </div>
              ))}
            </div>
            <hr className="mt-4 border-2 rounded-full border-[#3E4772]" />
          </div>
        </div>
      )}
    </>
  );
}
