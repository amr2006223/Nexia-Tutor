import {create} from "zustand"

interface UserState {
    user: any,
    setUser: (user: any) => void;
}

export const useUserStore = create<UserState>((set) => ({
  user: {},
  setUser: (user: any) => {
    set({ user: user });
  },
}));