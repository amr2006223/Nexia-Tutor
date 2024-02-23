import { useEffect, useRef, useState } from "react";
import { FiPlay } from 'react-icons/fi';

const Hero = () => {
  const girlRef = useRef<HTMLImageElement>(null);
  const overlayRef = useRef<HTMLDivElement>(null);
  const textRef = useRef<HTMLDivElement>(null);
  const sectionRef = useRef<HTMLDivElement>(null);
  const [containerHeight, setContainerHeight] = useState<number | null>(null);
  const [textLeft, setTextLeft] = useState<number>(64);
  const [textFontSize, setTextFontSize] = useState<number>(24);
  const [textTop, setTextTop] = useState<number>(12);
  const [buttonWidth, setButtonWidth] = useState<string>("auto");
  const [buttonBorderRadius, setButtonBorderRadius] = useState<string>("6px");
  const [buttonFontSize, setButtonFontSize] = useState<number>(14);
  const [buttonPadding, setButtonPadding] = useState<string>("4px 8px");
  const [lineHeight, setLineHeight] = useState<number>(1.5);
  const [textMarginLeft, setTextMarginLeft] = useState<string>("ml-6");

  useEffect(() => {
    const adjustContainerSize = () => {
      const heroContainer = document.getElementById("hero-container");
      const girlImage = girlRef.current;
      const textContainer = textRef.current;
      if (girlImage && heroContainer && textContainer) {
        setContainerHeight(girlImage.clientHeight);
        heroContainer.style.width = "100%";


        const windowWidth = window.innerWidth;
        const newLeft = windowWidth <= 767 ? 6 : 96;
        setTextLeft(newLeft);

        const newFontSize = windowWidth <= 767 ? 16 : 32;
        setTextFontSize(newFontSize);

        const newTop = windowWidth <= 767 ? 48 : 84;
        setTextTop(newTop);

        const newLineHeight = windowWidth <= 767 ? 1.2 : 1.2;
        setLineHeight(newLineHeight);


        const newButtonWidth = windowWidth <= 767 ? "84px" : "120px";
        setButtonWidth(newButtonWidth);

        const newButtonBorderRadius = windowWidth <= 767 ? "3px" : "6px";
        setButtonBorderRadius(newButtonBorderRadius);

        const newButtonFontSize = windowWidth <= 767 ? 12 : 14;
        setButtonFontSize(newButtonFontSize);

        const newButtonPadding = windowWidth <= 767 ? "2px 4px" : "4px 8px";

        const newTextMarginLeft = windowWidth <= 767 ? "4px" : "64px";
      }
    };

    adjustContainerSize();

    window.addEventListener("resize", adjustContainerSize);

    return () => {
      window.removeEventListener("resize", adjustContainerSize);
    };
  }, []);

  return (
    <section ref={sectionRef} >
      <div
        id="hero-container"
        className="relative w-full overflow-hidden"
        style={{ height: containerHeight ? `${containerHeight}px` : "auto", paddingTop: "10px", paddingBottom: "10px" }}
      >

        <img
          src="assets/images/hero.gif"
          alt="Hero GIF"
          className="absolute inset-0 w-full h-full object-cover shadow-xl"
          style={{ top: "-20%" }}
        />
        <div
          className="absolute w-full h-full object-cover inset-0 bg-[#3E4772] opacity-50"
          style={{ top: "-20%", height: containerHeight ? `${containerHeight}px` : "auto", paddingTop: "10px", paddingBottom: "10px" }}
        ></div>

        <div
          ref={overlayRef}
          className="absolute top-0 right-20 w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4 h-48 "
          style={{ top: "2%", height: containerHeight ? `${containerHeight}px` : "auto" }}
        >
          <div className="top-0 right-20 rounded-full w-12 h-48 absolute light-circle animate-light-effect"></div>
        </div>


        {/* Girl image */}
        <img
          ref={girlRef}
          id="girl-container"
          src="assets/images/girl.png"
          alt="Girl Image"
          onLoad={() => {
            setContainerHeight(girlRef.current?.clientHeight || null);
          }}
          className="absolute top-0 right-0 mr-8 lg:mr-16 w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4 h-auto object-scale-down"
          style={{
            maxWidth: "25%",
            minWidth: "200px",
            right: "10%",
            left: window.innerWidth <= 767 ? "45%" : "auto",
            ...(window.innerWidth <= 767 && {
              right: "0%"
            })
          }}
        />

        {/* Text content */}
        <div ref={textRef} className="absolute text-[#CDEBC5] text-center animate-text" style={{ left: `${textLeft}px`, top: `${textTop}px`, lineHeight: `${lineHeight}`, marginLeft: `${textMarginLeft}` }}>
          <p className="text-4xl font-semibold mb-2 bold-text" style={{ fontSize: `${textFontSize}px`, lineHeight: `${lineHeight}` }}>Fueling language joy <br /> for dyslexic stars</p>
          <p className="text-xl" style={{ color: "#CDEBC5", fontFamily: 'OpenDyslexic-Regular', fontSize: `${textFontSize - 8}px`, lineHeight: `${lineHeight}` }}>
            Elevate confidence, conquer challenges—the<br /> path to language brilliance unfolds here!
          </p>

          <div className="mt-4 flex justify-center">
            <button className="hover:bg-[#CDEBC5] border border-[#CDEBC5] border-2 text-[#CDEBC5] px-4 py-2 rounded-2xl font-bold hover:bg-opacity-40 transition duration-300 ease-in-out" style={{ fontFamily: 'OpenDyslexic-Regular', fontSize: `${buttonFontSize}px`, padding: `${buttonPadding}`, width: `${buttonWidth}` }}>
              <FiPlay className="inline text-2xl" />
              <span className="inline ml-1 font-bold text-lg" style={{ fontSize: `${buttonFontSize - 2}px` }}>Demo</span>
            </button>
            <button className="w-28 bg-[#CDEBC5] text-[#3E4772] px-4 py-2 rounded-2xl ml-4 font-bold text-lg hover:scale-110 shadow-lg transition duration-300 ease-in-out" style={{ fontFamily: 'OpenDyslexic-Regular', fontSize: `${buttonFontSize}px`, padding: `${buttonPadding}`, width: `${buttonWidth}` }}> Start</button>
          </div>
        </div>

      </div>
    </section>
  );
};

export default Hero;
