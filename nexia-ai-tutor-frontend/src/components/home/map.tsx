"use client";
import React, { useEffect } from "react";

const RectanglePage = () => {
  const isSmallScreen = window.innerWidth <= 767;
  useEffect(() => {
    const observerOptions = {
      root: null,
      rootMargin: "0px",
      threshold: 0.5,
    };

    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add("animate-zoom-in");
        } else {
          entry.target.classList.remove("animate-zoom-in");
        }
      });
    }, observerOptions);

    document.querySelectorAll(".animated-element").forEach((element) => {
      observer.observe(element);
    });

    return () => observer.disconnect();
  }, []);

  return (
    <div>
      {/* Title and Image */}
      <div className="text-center">
        <h1 className="text-4xl font-bold">Roadmap</h1>
        <div
          className={`flex  ${isSmallScreen ? "mb-[-148px]" : ""}  ${
            isSmallScreen ? "flex-col" : "items-center"
          } ${isSmallScreen ? "items-center" : "justify-center"} `}
        >
          {/* <div className="flex items-center justify-center"> */}
          <img
            src="/assets/images/excited-potato.png"
            alt="Excited Potato"
            className="w-32 h-24 mr-2"
          />
          <p>
            <span
              className={`flex  ${isSmallScreen ? "whitespace-pre-line" : ""} `}
            >
              Mr. Potato will be your {"\n"} Journey’s support system!
            </span>
          </p>
        </div>
      </div>

      <div className="flex justify-center items-center h-screen relative">
        {/* Blue shape container */}
        <div
          className="custom-map-width h-96 border border-solid border-[#3E4772] border-t-12 border-r-12 border-b-12 border-l-0 relative"
          style={{
            maxWidth: isSmallScreen ? "25%" : "100%",
            minWidth: "280px",
            maxHeight: isSmallScreen ? "25%" : "100%",
            minHeight: "280px",
            borderRightWidth: isSmallScreen ? "30px" : "60px",
            borderTopWidth: isSmallScreen ? "30px" : "60px",
            borderBottomWidth: isSmallScreen ? "30px" : "60px",
            position: "relative",
          }}
        >
          {/* Small rectangle at the middle of the upper side */}
          <div
            className="bg-[#CDEBC5] w-56 h-32 rounded-3xl absolute top-0 left-1/2 transform -translate-x-1/2 border border-solid border-[#3E4772] flex justify-center items-center hover:scale-125"
            style={{
              zIndex: 1,
              maxWidth: "25%",
              minWidth: "100px",
              maxHeight: "25%",
              minHeight: "100px",
              borderWidth: isSmallScreen ? "2px" : "8px",
              marginTop: isSmallScreen ? "-60px" : "-85px",
              marginLeft: isSmallScreen ? "-12px" : "-85px",
              fontSize: isSmallScreen ? "14px" : "18px",
            }}
          >
            <span style={{ whiteSpace: "pre-line", textAlign: "center" }}>
              Take the{"\n"}screening{"\n"}test
            </span>

            {/* Image at the left side */}
            <img
              src="/assets/images/thinking-potato.png"
              alt="Flying Potato"
              className={`${isSmallScreen ? "w-16" : "w-24"} ${
                isSmallScreen ? "h-16" : "h-24"
              } ${
                isSmallScreen ? "left-[-39px]" : "left-[-70px]"
              } absolute left-[-80px] bottom-1/2 transform translate-y-1/2 animate-zoom-in`}
              style={{
                animationDelay: "1.5s",
                animationName: "zoomInAnimation",
                animationDuration: "0.5s",
              }} // Add animation properties here
            />
          </div>

          {/* Small rectangle at top right */}
          <div
            className={`bg-[#CDEBC5] w-56 h-32 rounded-3xl absolute top-0 right-0 border border-solid border-[#3E4772] flex justify-center items-center
  hover:scale-125 transform -translate-x-[-50%] -translate-y-[50%] "
}`}
            style={{
              zIndex: 1,
              maxWidth: "25%",
              minWidth: "100px",
              maxHeight: "25%",
              minHeight: "100px",
              borderWidth: isSmallScreen ? "2px" : "8px",
              marginTop: isSmallScreen ? "-10px" : "-20px",
              marginRight: isSmallScreen ? "-10px" : "-20px",
              fontSize: isSmallScreen ? "12px" : "18px",
            }}
          >
            <span style={{ whiteSpace: "pre-line", textAlign: "center" }}>
              Choose{"\n"}today’s{"\n"}lesson
            </span>

            {/* Image at the left side */}
            <img
              src="/assets/images/clever-potato.png"
              alt="Flying Potato"
              className={`${isSmallScreen ? "w-18" : "w-32"} ${
                isSmallScreen ? "h-18" : "h-32"
              } ${
                isSmallScreen ? "left-[-53px]" : "left-[-80px]"
              } absolute  bottom-1/2 transform translate-y-1/2 animate-zoom-in`}
              style={{
                animationDelay: "3s",
                animationName: "zoomInAnimation",
                animationDuration: "0.5s",
              }}
            />
          </div>

          {/* Small rectangle at bottom right */}
          <div
            className=" hover:scale-125 transform -translate-x-[-50%] -translate-y-[-50%] bg-[#CDEBC5] w-56 h-32 rounded-3xl absolute bottom-0 right-0 border border-solid border-[#3E4772] flex justify-center items-center hover:scale-125"
            style={{
              zIndex: 1,
              maxWidth: "25%",
              minWidth: "100px",
              maxHeight: "25%",
              minHeight: "100px",
              borderWidth: isSmallScreen ? "2px" : "8px",

              marginBottom: isSmallScreen ? "-10px" : "-20px",
              marginRight: isSmallScreen ? "-10px" : "-20px",
              fontSize: isSmallScreen ? "14px" : "18px",
            }}
          >
            <span style={{ whiteSpace: "pre-line", textAlign: "center" }}>
              Learn{"\n"}while{"\n"}playing!
            </span>
            {/* Image at the left side */}
            <img
              src="/assets/images/potato-flying.png"
              alt="Flying Potato"
              className={`${isSmallScreen ? "w-18" : "w-32"} ${
                isSmallScreen ? "h-18" : "h-32"
              } ${
                isSmallScreen ? "left-[-52px]" : "left-[-80px]"
              } absolute left-[-80px] bottom-1/2 transform translate-y-1/2 animate-zoom-in`}
              style={{
                animationDelay: "4.5s",
                animationName: "zoomInAnimation",
                animationDuration: "0.5s",
              }}
            />
          </div>

          {/* Small rectangle at the middle of the lower side */}
          <div
            className="hover:scale-125 bg-[#CDEBC5] w-56 h-32 rounded-3xl absolute bottom-0 left-1/2 transform -translate-x-1/2 border border-solid border-[#3E4772] flex justify-center items-center"
            style={{
              zIndex: 1,
              maxWidth: "25%",
              minWidth: "100px",
              maxHeight: "25%",
              minHeight: "100px",
              borderWidth: isSmallScreen ? "2px" : "8px",
              marginBottom: isSmallScreen ? "-60px" : "-85px",
              marginLeft: isSmallScreen ? "-12px" : "-85px",
              fontSize: isSmallScreen ? "14px" : "18px",
            }}
          >
            <span style={{ whiteSpace: "pre-line", textAlign: "center" }}>
              Keep{"\n"}Learning
            </span>

            {/* Image at the left side */}
            <img
              src="/assets/images/studying-potato.png"
              alt="Flying Potato"
              className={`${isSmallScreen ? "w-16" : "w-28"} ${
                isSmallScreen ? "h-16" : "h-28"
              } ${
                isSmallScreen ? "left-[-50px]" : "left-[-75px]"
              } absolute  bottom-1/2 transform translate-y-1/2 animate-zoom-in`}
              style={{
                animationDelay: "6s",
                animationName: "zoomInAnimation",
                animationDuration: "0.5s",
              }}
            />
          </div>

          {/* Small circle at top left */}
          <div
            className={`${isSmallScreen ? "w-12" : "w-32"} ${
              isSmallScreen ? "h-12" : "h-32"
            } bg-[#CDEBC5] rounded-full relative flex items-center justify-center border-[#3E4772]`}
            style={{
              zIndex: 1,
              borderWidth: isSmallScreen ? "2px" : "8px",
              position: "absolute",
              top: isSmallScreen ? "-40px" : "-85px",
              left: isSmallScreen ? "-30px" : "-60px",
            }}
          >
            <div
              className={`${isSmallScreen ? "w-6" : "w-16"} ${
                isSmallScreen ? "h-6" : "h-16"
              } bg-[#87B17C] rounded-full absolute transform`}
              style={{
                top: "50%",
                left: "50%",
                transform: "translate(-50%, -50%)",
              }}
            ></div>
          </div>

          {/* Small circle at bottom left */}
          <div
            className={`${isSmallScreen ? "w-12" : "w-32"} ${
              isSmallScreen ? "h-12" : "h-32"
            } bg-[#CDEBC5] rounded-full relative flex items-center justify-center border-[#3E4772]`}
            style={{
              zIndex: 1,
              borderWidth: isSmallScreen ? "2px" : "8px",
              position: "absolute",
              bottom: isSmallScreen ? "-40px" : "-95px",
              left: isSmallScreen ? "-30px" : "-60px",
            }}
          >
            <div
              className={`${isSmallScreen ? "w-6" : "w-16"} ${
                isSmallScreen ? "h-6" : "h-16"
              } bg-[#5969B6] rounded-full absolute transform`}
              style={{
                top: "50%",
                left: "50%",
                transform: "translate(-50%, -50%)",
              }}
            ></div>
          </div>
        </div>

        {/* Dashed line */}
        <div
          className={`${isSmallScreen ? "ml-[4px]" : "ml-[-25px]"} ${
            isSmallScreen ? "h-[32%]" : "h-[55%]"
          } absolute top-1/2 left-1/2  transform -translate-x-1/2 -translate-y-1/2 h-[60%] w-[62%] border-dashed border-solid border-[#CDEBC5] border-l-0 `}
          style={{
            maxWidth: "80%",
            minWidth: "100px",
            minHeight: "20px",
            borderRightWidth: isSmallScreen ? "3px" : "6px",
            borderTopWidth: isSmallScreen ? "3px" : "6px",
            borderBottomWidth: isSmallScreen ? "3px" : "6px",
          }}
        ></div>
      </div>
    </div>
  );
};

export default RectanglePage;
