import axios from "axios";

export const keyWordExtractionFromFile = async (data: FormData) => {
  const response = await axios.post(`${process.env.KEYWORD_EXTRACTION_API}upload_pdf`, data, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data.keywords;
};
