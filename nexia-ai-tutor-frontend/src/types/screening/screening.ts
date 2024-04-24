export type GameData = {
  clicks: string;
  hits: string;
  misses: string;
  score: string;
  accuracy: string;
  missrate: string;
};

export type ScreeningData = {
  id: string;
  username: string;
  record: GameData[][];
};

/*
"id":"${token}","username":"test","record"
*/
