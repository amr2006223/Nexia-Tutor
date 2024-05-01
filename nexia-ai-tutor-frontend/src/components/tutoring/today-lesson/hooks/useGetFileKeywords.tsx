import { getGamesForUser } from "@/services/games/getGamesForUser";
import { useGameState } from "@/shared/state/game";
import { useLessonStore } from "@/shared/state/uploadedLessons";
import { GameModel } from "@/types/game";

const useGetFileKeywords = () => {
  const gameState = useGameState();

  const handleGames = async () => {
    const gamesData = await getGamesForUser();
    const games: GameModel[] = gamesData.map((game: any) => ({
      game_id: game.id,
      game_name: game.game_name,
    }));
    gameState.setGames(games);
  };

  handleGames();

  const lessonState = useLessonStore();
  const keywords = lessonState.keywords;

  return keywords;
};

export default useGetFileKeywords;
