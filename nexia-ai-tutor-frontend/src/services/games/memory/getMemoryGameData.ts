import axios from "axios";

export const getMemoryGameData = async (keyword: string) => {
  const response = await axios.get(
    `${process.env.WEB_SCRAPING_API}get_memory_game_data?word=${keyword}`
  );

  return response.data;
};
