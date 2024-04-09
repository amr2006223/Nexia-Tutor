import axios from "axios";

export const getGamesForLesson = async (
  lessonName: string,
  user_id: number
) => {
  // post request to get games for lesson\
  //   console.log("lessonName: ", lessonName);
  //   console.log("user_id: ", user_id);
  const response = await axios.post(
    `${process.env.NEXIA_API}api/games/${lessonName}`,
    {
      id: user_id,
    },
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  return response.data;
};
