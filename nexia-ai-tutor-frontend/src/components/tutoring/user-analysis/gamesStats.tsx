import { getGamesStats } from "@/services/user-analysis/getGameStats";
import GamesStatsComponent from "@/components/tutoring/user-analysis/components/gamesStatsComponent";
const GamesStatsPage = async () => {
  const data = await getGamesStats();

  return (
    <div>
      <GamesStatsComponent data={data} tab={data[0].gameData.game_name} />
    </div>
  );
};

export default GamesStatsPage;
