"use server";
import axios from "axios";
import { getTokenValue } from "../auth/auth";
import { GameModel } from "@/types/game";
import { cookies } from "next/headers";

export const getGamesForUser = async () => {
  const token = await getTokenValue();
  const response = await axios.post(
    `${process.env.NEXIA_API}api/games/token/${token}`
  );
  return response.data;
};

export const setGamesInCookies = async (games: GameModel[]) => {
  const gamesString = JSON.stringify(games);
  cookies().set("games", gamesString);
};

export const getGamesFromCookies = async () => {
  return JSON.parse(cookies().get("games")?.value || "[]");
};