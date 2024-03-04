import axios from "axios";

export const getMemoryGameData = async (keyword: string) => {
  const response = await axios.get(
    `http://127.0.0.1:5001/get_memory_game_data?word=${keyword}`
  );

  return response.data;
};
