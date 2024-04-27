"use client";

import { useScreeningGamesStore } from "@/shared/state/screening-games";

const GamesDataReport = () => {
  const gamesData = useScreeningGamesStore();
  return (
    <div>
      {gamesData.games_result.map((gameArray, index) => (
        <div key={index}>
          <div>game - {index + 1}</div>
          {gameArray.map((game, index2) => (
            <div key={index2}>{game} , </div>
          ))}
          <br />
          <br />
        </div>
      ))}
    </div>
  );
};

export default GamesDataReport;
