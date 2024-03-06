import { GameModel } from "@/types/game";
import {create} from "zustand"

interface GameState {
    games:GameModel[]
    setGames: (games: GameModel[]) => void;
}

export const useGameState = create<GameState>((set) => ({
  games: [],
  setGames: (games: GameModel[]) => {
    set({ games: games });
  },
}));