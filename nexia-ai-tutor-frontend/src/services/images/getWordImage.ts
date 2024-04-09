import axios from "axios";

export const getWordImage = async (word: string) => {
  const response = await axios.get(
    `${process.env.WEB_SCRAPING_API}get_image_word?word=${word}`
  );
  return response.data.image;
};
