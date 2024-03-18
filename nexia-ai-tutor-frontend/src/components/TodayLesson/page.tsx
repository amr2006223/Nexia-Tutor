import { useLessonStore } from "@/shared/state/uploadedLessons";
import Link from "next/link";
import WordComponent from "./wordComponent";
import { useEffect, useState } from "react";
import ProgressBarComponent from "@/shared/components/progress/progressBar";
import { getFilesFromLocalStorage } from "@/services/files/fileUpload";
import { getGamesForUser } from "@/services/games/getGamesForUser";
import { useGameState } from "@/shared/state/game";
import { GameModel } from "@/types/game";

export default function TodayLesson() {
  
  const [loading, setLoading] = useState(true);
  const [fileName, setFilename] = useState("");
  const [keywords, setkeywords] = useState([""]);
  const lessonState = useLessonStore();
  const gameState = useGameState()
const handleGames = async () => {
  const gamesData = await getGamesForUser();
  const games: GameModel[] = gamesData.map((game:any) => ({
    game_id: game.id,
    game_name: game.game_name,
  }));
  gameState.setGames(games);
  console.log(games);
};
  useEffect(() => {
    handleGames();
    const fileName = lessonState.fileName;
    if (fileName === "") {
      // get the data of first file from local storage
      const files = getFilesFromLocalStorage();
      const file = files[0];
      setFilename(file.name);
      setkeywords(file.keywords);
    } else {
      const keywords = lessonState.keywords;
      setFilename(fileName.split(".")[0]);
      setkeywords(keywords);
    }
    setLoading(false);
  }, []);

  return (
    <>
      {loading ? (
        <ProgressBarComponent />
      ) : (
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
              {/* <div className="flex justify-between items-center"> */}
              {/* <h2 className="text-xl font-semibold text-[#3E4772] text-center"> */}
              {/* Lesson 1{" "} */}
              {/* </h2> */}
              {/* {"\n"} */}
              {/* <div></div> */}
              {/* </div> */}
              {/* Scrollable area */}
              <div className="mt-4 h-96 overflow-y-auto">
                {keywords.map((word, index) => (
                  <WordComponent key={index} word={word} />
                ))}
              </div>
            </div>
          </div>
        </>
      )}
    </>
  );
}
