"use client";
import { getMemoryGameData } from "@/services/games/memory/getMemoryGameData";
import ProgressBarComponent from "@/shared/components/progress/progressBar";
import React, { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";

type Boxes = {
  id: number;
  image: string;
  color: string;
};

type selectedImage = {
  id: number;
  image: string;
  show: boolean;
};

const page = () => {
  const searchParams = useSearchParams();
  const keywordValue = searchParams.get("word");
  const [loading, setLoading] = useState<boolean>(true);
  const [progress, setProgress] = useState<number>(0);
  const [infoText, setInfoText] = useState<string>("Memorize the Pictures");
  const [selectedImage, setSelectedImage] = useState<selectedImage>();
  const [boxes, setBoxes] = useState<Boxes[]>([]);
  const [changeColor, setChangeColor] = useState<boolean>(false);

  const loadingImages = async () => {
    const keyText = keywordValue as string;
    const response = await getMemoryGameData(keyText);
    console.log(response);

    const { keyword, other_words } = response;
    const allWords = [...keyword, ...other_words];
    // shuffle the array
    allWords.sort(() => Math.random() - 0.5);
    // add id to each word
    allWords.forEach((word, index) => {
      word.id = index + 1;
    });
    // console.log(allWords);

    const _boxes = allWords.map((word: any, index: number) => {
      return {
        id: word.id,
        image: word.image_link,
        color: "bg-white hover:bg-gray-100",
      };
    });
    setBoxes(_boxes);

    const _selectedId = allWords.find(
      (word: any) => word.query === keyText
    )!.id;

    // console.log(_selectedId);
    setSelectedImage({
      id: _selectedId,
      image: keyword[0].image_link,
      show: false,
    });
  };

  const startProgress = async (duration: number) => {
    let progress = 0;
    for (let i = 0; i < duration; i++) {
      progress += 100 / duration;
      setProgress(progress);
      await new Promise((resolve) => setTimeout(resolve, 1000));
    }
  };

  const run = async () => {
    console.log("run function called");
    // 1. fetch images
    await loadingImages();
    setLoading(false);
    // await new Promise((resolve) => setTimeout(resolve, 1000));

    // 2. start progress
    await startProgress(5);
    setInfoText("Time's up!");
    await new Promise((resolve) => setTimeout(resolve, 1000));

    // 3. hide images and show empty boxes with numbers & show selected image
    setInfoText("Where was this image?");

    setSelectedImage((prevState: selectedImage | undefined) => ({
      ...(prevState as selectedImage), // Type assertion to avoid type error
      show: true,
    }));
    // await new Promise((resolve) => setTimeout(resolve, 1000));

    // 4. start progress
    // setProgress(0);
    // await new Promise((resolve) => setTimeout(resolve, 1000));
    // await startProgress(4);

    // // 5. change the color of the box that has the id = id of the selected image
    // setChangeColor(true);
  };

  const checkAnswer = (id: number) => {
    if (changeColor) return;

    if (id === selectedImage!.id) {
      setInfoText("Correct!");
      setChangeColor(true);
    } else {
      setInfoText("Wrong!");
    }
  };

  useEffect(() => {
    run();
    console.log("useEffect");
  }, []);

  useEffect(() => {
    const _boxes = boxes.map((box) => {
      if (box.id === selectedImage!.id) {
        return {
          ...box,
          color: "bg-green-300 hover:bg-green-500 text-white",
        };
      }
      return box;
    });
    setBoxes(_boxes);
  }, [changeColor]);

  return (
    <div>
      {loading ? (
        <ProgressBarComponent />
      ) : (
        <div className="flex flex-col items-center justify-center h-screen">
          <div>
            <progress className="w-60" value={progress} max="100"></progress>
          </div>

          <div className="flex flex-row items-center justify-between">
            <div className="px-2">{infoText}</div>
            <div>
              {selectedImage?.show && (
                <img
                  src={selectedImage.image}
                  alt="selected"
                  width={75}
                  height={75}
                />
              )}
            </div>
          </div>

          <div className="bg-white shadow-lg border-2 border-black rounded-lg m-4 p-4">
            <div className={`grid grid-cols-2 gap-4`}>
              {selectedImage?.show ? (
                <>
                  {boxes.map((box, index) => (
                    <div
                      key={box.id}
                      onClick={() => checkAnswer(box.id)}
                      className={`flex items-center justify-center rounded-lg h-24 w-24 text-2xl cursor-pointer font-bold border-2 border-black ${box.color} hover:text-white transition duration-300 ease-in-out`}
                    >
                      {box.id}
                    </div>
                  ))}
                </>
              ) : (
                <>
                  {boxes.map((box, index) => (
                    <img
                      key={index}
                      src={box.image}
                      style={{
                        width: "100px",
                        height: "100px",
                      }}
                    />
                  ))}
                </>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default page;
