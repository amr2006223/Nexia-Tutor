import { GameModel } from "@/types/game";
import WordComponent from "./wordComponent";

type Props = {
  keywords: string[];
  games: GameModel[];
};

const FileWords = async (props: Props) => {
  return (
    <div className="w-full max-w-xl mt-8 p-8 bg-[#E3FFDC] rounded-lg">
      <div className="mt-4 h-96 overflow-y-auto">
        {props.keywords.map((word, index) => (
          <WordComponent key={index} word={word} games={props.games} />
        ))}
      </div>
    </div>
  );
};

export default FileWords;
