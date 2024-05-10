import useGetFileKeywords from "../hooks/useGetFileKeywords";
import WordComponent from "./wordComponent";

const FileWords = () => {
  const keywords = useGetFileKeywords();
  return (
    <div className="w-full max-w-xl mt-8 p-8 bg-[#E3FFDC] rounded-lg">
      <div className="mt-4 h-96 overflow-y-auto">
        {keywords.map((word, index) => (
          <WordComponent key={index} word={word} />
        ))}
      </div>
    </div>
  );
};

export default FileWords;
