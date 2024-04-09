import { create } from "zustand";

interface ScreeningGamesState {
  games_result: number[][];
  append: (new_game_result: number[]) => void;
}

export const useScreeningGamesStore = create<ScreeningGamesState>((set) => ({
  games_result: [],
  append: (new_game_result: number[]) => {
    set((state) => {
      return {
        games_result: [...state.games_result, new_game_result],
      };
    });
  },
}));
