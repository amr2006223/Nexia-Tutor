import axios from "axios";

export const getTextSound = async (word: string) => {
  // let key = process.env.NEXT_PUBLIC_GOOGLE_API_KEY;
  // const response = await axios.post(
  //   "https://texttospeech.googleapis.com/v1/text:synthesize",
  //   {
  //     input: { text: word },
  //     voice: {
  //       languageCode: "en-US",
  //       // ssmlGender: "MALE",
  //     },
  //     //   audioConfig: { audioEncoding: "LINEAR16" },
  //     audioConfig: { audioEncoding: "MP3", speakingRate: 0.7 },
  //   },
  //   {
  //     headers: {
  //       "Content-Type": "application/json",
  //       "x-goog-api-key": key,
  //     },
  //   }
  // );
  // return response.data.audioContent;

  const response = await axios.get(
    `${process.env.WEB_SCRAPING_API}get_audio_word?word=${word}`
  );
  return response.data.sound;
};

export const getTextSoundFemale = async (word: string) => {
  // let key = process.env.NEXT_PUBLIC_GOOGLE_API_KEY;
  // const response = await axios.post(
  //   "https://texttospeech.googleapis.com/v1/text:synthesize",
  //   {
  //     input: { text: word },
  //     voice: {
  //       languageCode: "en-US",
  //       ssmlGender: "FEMALE",
  //     },
  //     audioConfig: {
  //       audioEncoding: "MP3",
  //       speakingRate: 0.85,
  //     },
  //   },
  //   {
  //     headers: {
  //       "Content-Type": "application/json",
  //       "x-goog-api-key": key,
  //     },
  //   }
  // );
  // return response.data.audioContent;

  const response = await axios.get(
    `${process.env.WEB_SCRAPING_API}get_audio_word?word=${word}`
  );
  return response.data.sound;
};
