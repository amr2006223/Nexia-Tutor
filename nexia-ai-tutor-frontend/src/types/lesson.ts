import { GameModel } from "./game";
import { KeywordModel } from "./keyword";

export type LessonGamesModel = {
  lesson_name: string;
  keywords: KeywordModel[];
  games: GameModel[];
};
