import axios from "axios";

export const getKeywordDataForRhymeGame = async (keyword: string) => {
  const response = await axios.get(
    `http://127.0.0.1:5001/get_rhymes_game_data?word=${keyword}`
  );

  return response.data;
};
