"use client";
import FilesList from "./components/filesList";
import SearchFiles from "./components/searchFiles";
import UploadFile from "./components/uploadFile";
import WelcomBox from "./components/welcomBox";
import useFiles from "./hooks/useFiles";

type MyLearningPageProps = {
  username: string;
};

const MyLearningPage = (props: MyLearningPageProps) => {
  const { files, handleFileUpload, handleRemoveFile } = useFiles();

  return (
    <div className="bg-[#CDEBC5]  flex flex-col items-center justify-center min-h-screen py-6">
      <WelcomBox username={props.username} />

      <div className="w-full max-w-2xl mt-8 p-8 bg-[#E3FFDC] rounded-lg">
        {buildContentBox()}
        <hr className="mt-2 border-2 rounded-full border-[#3E4772]" />
        <FilesList files={files} handleRemoveFile={handleRemoveFile} />
      </div>
    </div>
  );

  function buildContentBox() {
    return (
      <div className="flex justify-between items-center">
        {yourContents()}
        <SearchFiles />
        <UploadFile handleFileUpload={handleFileUpload} />
      </div>
    );
  }
  function yourContents() {
    return (
      <h2 className="text-xl font-semibold text-[#3E4772]">Your Contents</h2>
    );
  }
};

export default MyLearningPage;
