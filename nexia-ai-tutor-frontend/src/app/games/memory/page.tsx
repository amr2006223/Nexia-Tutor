"use client";
import ProgressBarComponent from "@/shared/progress/progressBar";
import React, { useEffect, useState } from "react";

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
  const [loading, setLoading] = useState<boolean>(true);
  const [progress, setProgress] = useState<number>(0);
  const [infoText, setInfoText] = useState<string>("Memorize the Pictures");
  const [selectedImage, setSelectedImage] = useState<selectedImage>();
  const [boxes, setBoxes] = useState<Boxes[]>([]);
  const [changeColor, setChangeColor] = useState<boolean>(false);

  const loadingImages = async () => {
    // fetch images from server
    const images = await fetchImages();
    // set boxes
    const _boxes = images.map((image, index) => ({
      id: index + 1,
      image,
      color: "bg-gray",
    }));
    setBoxes(_boxes);
    // get random image and set it as selected image
    const randomImage = _boxes[Math.floor(Math.random() * images.length)];

    setSelectedImage({
      id: randomImage.id,
      image: randomImage.image,
      show: false,
    });
  };

  const fetchImages = async () => {
    // set timeout to simulate fetching images from server
    await new Promise((resolve) => setTimeout(resolve, 1000));
    return [
      "https://picsum.photos/id/234/96",
      "https://picsum.photos/id/235/96",
      "https://picsum.photos/id/236/96",
      "https://picsum.photos/id/237/96",
      // "https://picsum.photos/id/238/96",
      // "https://picsum.photos/id/239/96",
    ];
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
    // 1. fetch images
    await loadingImages();
    setLoading(false);
    await new Promise((resolve) => setTimeout(resolve, 1000));

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

    // 4. start progress
    // setProgress(0);
    // await new Promise((resolve) => setTimeout(resolve, 1000));
    // await startProgress(4);

    // // 5. change the color of the box that has the id = id of the selected image
    // setChangeColor(true);
  };

  const checkAnswer = (id: number) => {
    if(changeColor) return;
    if (id === selectedImage!.id) {
      setInfoText("Correct!");
      setChangeColor(true);
    } else {
      setInfoText("Wrong!");
    }
  };

  useEffect(() => {
    run();
  }, []);

  useEffect(() => {
    const _boxes = boxes.map((box) => {
      if (box.id === selectedImage!.id) {
        return {
          ...box,
          color: "text-white bg-green",
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
                      className={`flex items-center justify-center rounded-lg h-24 w-24 text-2xl cursor-pointer font-bold border-2 border-black ${box.color}-300 hover:${box.color}-700 hover:text-white transition duration-300 ease-in-out`}
                    >
                      {box.id}
                    </div>
                  ))}
                </>
              ) : (
                <>
                  {boxes.map((box, index) => (
                    <img key={index} src={box.image} />
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
