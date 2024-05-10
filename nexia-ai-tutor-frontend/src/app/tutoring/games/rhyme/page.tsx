"use client";
import ProgressBarComponent from "@/shared/components/progress/progressBar";
import SpeakerButtonComponent from "@/shared/components/buttons/speakerButtonComponent";
import { Button } from "@mui/material";
import React, { useEffect } from "react";
import { playSoundFromGoogle, playMp3Sound } from "@/shared/utils/play-sounds";
import { RhymingWord } from "@/types/tutoring-games/rhyme/rhyme";
import SecondryWordRhymeComponent from "@/components/games/tutoring-games/rhyme/secondryWordRhymeComponent";
import CloseIcon from "@mui/icons-material/Close";
import CheckIcon from "@mui/icons-material/Check";
import CounterComponent from "@/shared/components/counter/CounterComponent";
import FlagIcon from "@mui/icons-material/Flag";
import { getKeywordDataForRhymeGame } from "@/services/games/rhyme/getKeywordData";
import useWordSearchParams from "@/shared/hooks/useWordSearchParams";
import { useRouter } from "next/navigation";
type RhymingGameProps = {
  keyword: RhymingWord;
  otherWords: RhymingWord[];
  numberOfCorrectAnswers: number;
};

const RhymingGamePage = () => {
  const router = useRouter();
  const { keywordParams } = useWordSearchParams();
  const [response, setResponse] = React.useState<RhymingGameProps | null>(null);
  const [loading, setLoading] = React.useState(true);
  const [startGame, setStartGame] = React.useState(false);
  const [correctAnswers, setCorrectAnswers] = React.useState(0);
  const [wrongAnswers, setWrongAnswers] = React.useState(0);

  const getData = async () => {
    try {
      // await new Promise((resolve) => setTimeout(resolve, 1000));

      const response = await getKeywordDataForRhymeGame(keywordParams);
      // console.log(response);
      const { keyWord, not_rhymes, rhymes } = response;
      // console.log("keyWordResponse", keyWord);
      // console.log("not_rhymesResponse", not_rhymes);
      // console.log("rhymesResponse", rhymes);
      // return;
      const allWords = [...not_rhymes, ...rhymes];

      const keyword: RhymingWord = {
        data: {
          word: keyWord.text,
          sound: keyWord.sound,
          image: keyWord.image,
        },
        showImage: false,
        rhymed: true,
      };

      const otherWords: RhymingWord[] = allWords.map(
        (item: { text: any; sound: any; image: any; rhyme: any }) => {
          return {
            data: {
              word: item.text,
              sound: item.sound,
              image: item.image,
            },
            rhymed: item.rhyme,
            showImage: false,
          };
        }
      );

      // shuffle the array
      otherWords.sort(() => Math.random() - 0.5);
      console.log("otherWords", otherWords);
      const props = { keyword, otherWords, numberOfCorrectAnswers: 3 };

      setResponse(props);
      setLoading(false);
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  };

  const showOtherWords = async () => {
    if (response) {
      for (let i = 0; i < response.otherWords.length; i++) {
        const word = response.otherWords[i];
        word.showImage = true;
        setResponse({ ...response });
        await new Promise((resolve) => setTimeout(resolve, 500));
        await playSoundFromGoogle(word.data.sound);
        await new Promise((resolve) => setTimeout(resolve, 1000));
      }
    }
  };

  const startTheGame = async () => {
    if (response) {
      setStartGame(true);
      // 1. play what word rhymes with the keyword
      await playMp3Sound("/what_rhymes.mp3");

      // // 2. play the keyword sound
      await playSoundFromGoogle(response.keyword.data.sound);

      // // 3. show the keyword image
      await showOtherWords();
    }
  };

  const ckeckIfRhyming = (word: RhymingWord) => {
    if (correctAnswers === response!.numberOfCorrectAnswers) {
      console.log("Game Over");
      return;
    }

    if (word.rhymed) {
      console.log("Correct");
      setCorrectAnswers(correctAnswers + 1);
    } else {
      console.log("Wrong");
      setWrongAnswers(wrongAnswers + 1);
    }
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <>
      {loading ? (
        <ProgressBarComponent />
      ) : (
        <div style={{ minHeight: "100vh", position: "relative" }}>
          {startGame ? (
            <div style={{ paddingBottom: "60px" }}>
              {response && (
                <div className="flex flex-col justify-center items-center mt-3">
                  <div>
                    <Button
                      onClick={() => router.back()}
                      className="font-bold text-base"
                      variant="contained"
                      style={{
                        backgroundColor: "#3E4772",
                        color: "#CDEBC5",
                        position: "absolute",
                        top: "10px",
                        left: "10px",
                      }}
                    >
                      Back
                    </Button>
                  </div>
                  <div className="flex flex-row items-center">
                    <div className="flex text-3xl font-bold ">
                      What word rhymes with
                    </div>
                    <img
                      className="ml-2"
                      src={response.keyword.data.image}
                      alt={response.keyword.data.word}
                      style={{ width: "50px", height: "50px" }}
                    />
                    <SpeakerButtonComponent
                      sound={response.keyword.data.sound}
                      from_google={true}
                      theme="dark"
                    />
                  </div>

                  <div className="grid grid-cols-3 grid-flow-row gap-4">
                    {response.otherWords.map((word, index) => (
                      <SecondryWordRhymeComponent
                        key={index}
                        index={index}
                        secondaryWord={word}
                        checkFunction={() => ckeckIfRhyming(word)}
                      />
                    ))}
                  </div>
                  <div
                    style={{
                      position: "fixed",
                      bottom: 0,
                      width: "100%",
                      backgroundColor: "#fff",
                      padding: "10px",
                      borderTop: "2px solid #3e4772",
                    }}
                    className="flex flex-row justify-between items-center mt-3"
                  >
                    <CounterComponent
                      count={wrongAnswers}
                      color="red"
                      icon={CloseIcon}
                    />
                    <CounterComponent
                      count={correctAnswers}
                      color="green"
                      icon={CheckIcon}
                    />
                    <CounterComponent
                      count={response.numberOfCorrectAnswers}
                      color="purple"
                      icon={FlagIcon}
                    />
                  </div>
                </div>
              )}
            </div>
          ) : (
            <div className="flex flex-col justify-center items-center mt-3">
              <Button
                onClick={() => startTheGame()}
                variant="contained"
                color="primary"
              >
                Start Game
              </Button>
            </div>
          )}
        </div>
      )}
    </>
  );
};

export default RhymingGamePage;
