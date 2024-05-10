import { GameData, GameStatsData } from "@/types/user-analysis/gameData";
import axios from "axios";
import { getTokenValue } from "../auth/auth";

export const getGamesStats = async (): Promise<GameStatsData[]> => {
  const token = await getTokenValue();
  const games: GameData[] = await getGamesIds();

  const data: GameStatsData[] = [];

  for (let i = 0; i < games.length; i++) {
    let response = await axios.get(
      `${process.env.NEXIA_API}analysis/${token}/${games[i].id}`
    );
    if (response.data == "No Records Found") {
      continue;
    }

    let stats: GameStatsData = {
      gameData: {
        id: games[i].id,
        game_name: games[i].game_name,
      },
      maxTimeTaken: response.data.maxTimeTaken,
      averageTimeTaken: response.data.averageTimeTaken,
      averageMisses: response.data.averageMisses,
      minMisses: response.data.minMisses,
      minTimeTaken: response.data.minTimeTaken,
      totalTimeTaken: response.data.totalTimeTaken,
      maxMisses: response.data.maxMisses,
      totalMisses: response.data.totalMisses,
    };

    data.push(stats);
  }

  return data;
};

const getGamesIds = async (): Promise<GameData[]> => {
  const resposne = await axios.get(
    `${process.env.NEXIA_API}api/games/getAllGames`
  );

  return resposne.data;
};
