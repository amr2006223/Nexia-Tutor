"use client";
import { GameModel } from "@/types/game";
import BackButton from "./components/backButton";
import FileDetails from "./components/fileDetails";
import FileWords from "./components/fileWords";

type Props = {
  fileName: string;
  keywords: string[];
  games: GameModel[];
};

const TodayLessonPage = (props: Props) => {
  return (
    <div>
      <BackButton />

      <div className="bg-[#CDEBC5] flex flex-col items-center justify-center min-h-screen py-6 ">
        <FileDetails fileName={props.fileName} />
        <FileWords keywords={props.keywords} games={props.games} />
      </div>
    </div>
  );
};

export default TodayLessonPage;
