"use client";
import ProgressBarComponent from "@/shared/components/progress/progressBar";
import SpeakerButtonComponent from "@/shared/components/buttons/speakerButtonComponent";
import { Button } from "@mui/material";
import React, { useEffect } from "react";
import { playMp3Sound } from "@/shared/utils/play-sounds";
import { RhymingWord } from "@/types/tutoring-games/rhyme/rhyme";
import SecondryWordRhymeComponent from "@/components/games/tutoring-games/rhyme/secondryWordRhymeComponent";
import CloseIcon from "@mui/icons-material/Close";
import CheckIcon from "@mui/icons-material/Check";
import CounterComponent from "@/shared/components/counter/CounterComponent";
import FlagIcon from "@mui/icons-material/Flag";

type RhymingGameProps = {
  keyword: RhymingWord;
  otherWords: RhymingWord[];
  numberOfCorrectAnswers: number;
};

const RhymingGamePage = () => {
  const [response, setResponse] = React.useState<RhymingGameProps | null>(null);
  const [loading, setLoading] = React.useState(true);
  const [startGame, setStartGame] = React.useState(false);
  const [correctAnswers, setCorrectAnswers] = React.useState(0);
  const [wrongAnswers, setWrongAnswers] = React.useState(0);

  const getData = async () => {
    await new Promise((resolve) => setTimeout(resolve, 1000));

    const keyword: RhymingWord = {
      data: {
        word: "car",
        sound: "/car.mp3",
        image:
          "https://static.vecteezy.com/system/resources/previews/023/524/637/original/red-sport-car-design-transparent-background-free-png.png",
      },
      showImage: false,
      rhymed: true,
    };

    const otherWords: RhymingWord[] = [
      {
        data: {
          word: "Star",
          sound: "/star.mp3",
          image: "https://clipart-library.com/img/2184494.png",
        },
        showImage: false,
        rhymed: true,
      },
      {
        data: {
          word: "Guitar",
          sound: "/guitar.mp3",
          image:
            "https://png.pngtree.com/png-clipart/20230217/ourmid/pngtree-guitar-cartoon-png-image_6604793.png",
        },
        showImage: false,
        rhymed: false,
      },
    ];
    const props = { keyword, otherWords, numberOfCorrectAnswers: 1 };
    setResponse(props);
    setLoading(false);
  };

  const showOtherWords = async () => {
    if (response) {
      for (let i = 0; i < response.otherWords.length; i++) {
        const word = response.otherWords[i];
        word.showImage = true;
        setResponse({ ...response });
        await new Promise((resolve) => setTimeout(resolve, 500));
        await playMp3Sound(word.data.sound);
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
      await playMp3Sound(response.keyword.data.sound);

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
    <div style={{ minHeight: "100vh", position: "relative" }}>
      {loading && <ProgressBarComponent />}
      {startGame ? (
        <div style={{ paddingBottom: "60px" }}>
          {response && (
            <div className="flex flex-col justify-center items-center mt-3">
              <div className="flex flex-row items-center">
                <SpeakerButtonComponent
                  sound="/what_rhymes.mp3"
                  from_google={false}
                  theme="dark"
                />
                <div className="flex text-3xl font-bold ">
                  What word rhymes with
                </div>
                <img
                  className="ml-2"
                  src={response.keyword.data.image}
                  alt={response.keyword.data.word}
                  width={70}
                  height={70}
                />
              </div>

              <div className="flex flex-row">
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
  );
};

export default RhymingGamePage;
