export type GameStatsData = {
  gameData: GameData; //
  maxTimeTaken: number; //
  averageTimeTaken: number;//
  averageMisses: number;//
  minMisses: number;//
  minTimeTaken: number;
  totalTimeTaken: number;
  maxMisses: number;
  totalMisses: number;
};

export type GameData = {
  id: number;
  game_name: string;
};
