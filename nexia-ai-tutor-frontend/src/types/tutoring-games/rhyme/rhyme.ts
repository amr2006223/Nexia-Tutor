export type Word = {
  word: string;
  sound: string;
  image: string;
};

export type RhymingWord = {
  data: Word;
  rhymed: boolean;
  showImage: boolean;
};
