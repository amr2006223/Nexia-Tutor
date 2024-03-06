import axios from "axios";
import { getTokenValue } from "../auth/auth";

export const getGamesForUser = async (
) => {
  // post request to get games for lesson\
  //   console.log("lessonName: ", lessonName);
  //   console.log("user_id: ", user_id);
  const token = await getTokenValue();
  const response = await axios.post(
    `${process.env.NEXIA_API}api/games/token/${token}`,
  );
  return response.data;
};
