import axios from "axios";

export const keyWordExtractionFromFile = async (data: FormData) => {
  const response = await axios.post("http://127.0.0.1:5000/upload_pdf", data, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data.keywords;
};
