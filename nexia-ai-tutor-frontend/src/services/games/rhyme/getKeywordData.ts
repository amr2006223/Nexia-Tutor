import axios from "axios";

export const getKeywordDataForRhymeGame = async (keyword: string) => {
  const response = await axios.get(
    `${process.env.WEB_SCRAPING_API}get_rhymes_game_data?word=${keyword}`
  );

  return response.data;
};
