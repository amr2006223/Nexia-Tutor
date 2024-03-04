import axios from "axios";

export const getWordImage = async (word: string) => {
  const response = await axios.get(
    `http://localhost:5001/get_image_word?word=${word}`
  );
  return response.data.image;
};
