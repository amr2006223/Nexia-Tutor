import { lessonFileData } from "@/types/lessons/fileData";
import { FiFileText, FiTrash2 } from "react-icons/fi";
import { useRouter } from "next/navigation";
import { getGamesForUser, setGamesInCookies } from "@/services/games/getGamesForUser";
import { GameModel } from "@/types/game";
import { useState } from "react";
import ProgressBarComponent from "@/shared/components/progress/progressBar";
import { saveFileNameServerSide } from "@/services/files/fileName";

type FileRowProps = {
  index: number;
  file: lessonFileData;
  handleRemoveFile: (index: number) => void;
};

const FileRow = (props: FileRowProps) => {
  const router = useRouter();
  const [loading, setLoading] = useState(false);

  const handleContinue = async () => {
    try {
      setLoading(true);
      await saveFileNameServerSide(props.file.name, props.file.keywords);
      await handleSetGames();
      router.push("/tutoring/today-lesson");
    } catch (error) {
      console.error("Error set keywords on local state: ", error);
    }
  };

  const handleSetGames = async () => {
    const gamesData = await getGamesForUser();
    const games: GameModel[] = gamesData.map((game: any) => ({
      game_id: game.id,
      game_name: game.game_name,
    }));
    setGamesInCookies(games);
  };

  return (
    <div>
      <div
        key={props.file.name}
        className="flex items-center mt-4 bg-[#CDEBC5] rounded-lg p-4 drop-shadow-lg"
      >
        <p className="ml-2">{props.file.name}</p>
        {buildContinueButton()}

        <FiFileText className="ml-2 h-6 w-6" fill="#3182CE" />

        {buildRemoveButton()}
      </div>
      {loading && <ProgressBarComponent />}
    </div>
  );

  function buildContinueButton() {
    return (
      <button
        className="ml-auto bg-[#CDEBC5] text-[#3E4772]"
        onClick={handleContinue}
      >
        Continue
      </button>
    );
  }

  function buildRemoveButton() {
    return (
      <button
        className="ml-2 text-red-500"
        onClick={() => props.handleRemoveFile(props.index)}
      >
        <FiTrash2 className="h-5 w-5" />
      </button>
    );
  }
};

export default FileRow;
