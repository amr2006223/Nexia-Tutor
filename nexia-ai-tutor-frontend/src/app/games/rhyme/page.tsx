"use client";
import ProgressBarComponent from "@/shared/progress/progressBar";
import { Button } from "@mui/material";
import React, { useEffect } from "react";

type RhymingWord = {
  word: string;
  sound: string;
  image: string;
  showImage: boolean;
};

type RhymingGameProps = {
  keyword: RhymingWord;
  otherWords: RhymingWord[];
};

const RhymingGamePage = () => {
  const [response, setResponse] = React.useState<RhymingGameProps | null>(null);
  const [loading, setLoading] = React.useState(true);
  const [startGame, setStartGame] = React.useState(false);

  const getData = async () => {
    await new Promise((resolve) => setTimeout(resolve, 1000));

    const keyword: RhymingWord = {
      word: "car",
      sound: "/car.mp3",
      image:
        "https://static.vecteezy.com/system/resources/previews/023/524/637/original/red-sport-car-design-transparent-background-free-png.png",
      showImage: false,
    };

    const otherWords: RhymingWord[] = [
      {
        word: "Star",
        sound: "/star.mp3",
        image: "https://clipart-library.com/img/2184494.png",
        showImage: false,
      },
      {
        word: "Guitar",
        sound: "/guitar.mp3",
        image:
          "https://png.pngtree.com/png-clipart/20230217/ourmid/pngtree-guitar-cartoon-png-image_6604793.png",
        showImage: false,
      },
    ];
    const props = { keyword, otherWords };
    setResponse(props);
    setLoading(false);
  };

  const playSound = async (sound: string) => {
    try {
      const audio = new Audio(sound);
      await audio.play();
      await new Promise((resolve) => {
        audio.onended = resolve;
      });
    } catch (e) {
      console.log(e);
    }
  };

  const showOtherWords = async () => {
    if (response) {
      for (let i = 0; i < response.otherWords.length; i++) {
        const word = response.otherWords[i];
        word.showImage = true;
        setResponse({ ...response });
        await new Promise((resolve) => setTimeout(resolve, 500));
        await playSound(word.sound);
        await new Promise((resolve) => setTimeout(resolve, 1000));
      }
    }
  };

  const startTheGame = async () => {
    if (response) {
      setStartGame(true);
      // 1. play what word rhymes with the keyword
      await playSound("/what_rhymes.mp3");

      // 2. play the keyword sound
      await playSound(response.keyword.sound);

      // 3. show the keyword image
      await showOtherWords();
    }
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <div>
      {loading && <ProgressBarComponent />}
      {startGame ? (
        <div>
          {response && (
            <div className="flex flex-col justify-center items-center mt-3">
              <div className="flex flex-row ">
                <div className="flex items-center text-3xl font-bold ">
                  What word rhymes with
                </div>
                <img
                  className="ml-2"
                  src={response.keyword.image}
                  alt={response.keyword.word}
                  width={70}
                  height={70}
                />
              </div>

              <div className="flex flex-row">
                {response.otherWords.map((word, index) => (
                  <div
                    key={index}
                    className="flex flex-col items-center justify-center m-2"
                  >
                    {word.showImage && (
                      <div className="flex flex-col items-center justify-center border border-dashed border-blue-500">
                        <img
                          className="rounded-full"
                          src={word.image}
                          alt={word.word}
                          width={100}
                          height={100}
                        />
                        <div className="flex items-center text-2xl font-bold"></div>
                      </div>
                    )}
                  </div>
                ))}
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
/*
    1. fetch data wich is (keywords, other words, and images links) - done
    2. get the words sounds and save them - done
    3. begin game and play the sounds 
        a. show text and then key word image and then play sound
        b. show other words and play sounds
*/
