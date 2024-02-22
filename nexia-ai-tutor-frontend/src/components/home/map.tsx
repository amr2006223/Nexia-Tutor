import React from 'react';

const RectanglePage = () => {
  const isSmallScreen = window.innerWidth <= 767;

  return (
    <div className="flex justify-center items-center h-screen">
      <div
        className="custom-map-width h-96 border border-solid border-[#3E4772] border-t-12 border-r-12 border-b-12 border-l-0 relative"
        style={{
          maxWidth: isSmallScreen ? "25%" : "100%",
          minWidth: "280px",
          maxHeight: isSmallScreen ? "25%" : "100%",
          minHeight: "280px",
          borderRightWidth: isSmallScreen ? "30px" : "60px", // Border on the right
          borderTopWidth: isSmallScreen ? "30px" : "60px", // Border on the top
          borderBottomWidth: isSmallScreen ? "30px" : "60px", // Border on the bottom
          position: 'relative', // Add this to the main rectangle to make sure absolute positioning works properly
        }}
      >
        
 
        {/* Small rectangle at top right */}
        <div
          className="bg-[#CDEBC5] w-56 h-32 rounded-3xl absolute top-0 right-0 border border-solid border-[#3E4772] flex justify-center items-center"
          style={{
            borderWidth: isSmallScreen ? "2px" : "8px",
            transform: "translate(50%, -50%)",
            marginTop: isSmallScreen ? "-10px" : "-20px",
            marginRight: isSmallScreen ? "-10px" : "-20px",
            fontSize: isSmallScreen ? "14px" : "18px", // Adjust font size based on screen size
          }}
        >
          <span>Choose today’s lesson</span>
          {/* <span className="tooltip-icon">ℹ️</span> */}
          
        </div>

        {/* Small rectangle at bottom right */}
        <div
          className="bg-[#CDEBC5] w-56 h-32 rounded-3xl absolute bottom-0 right-0 border border-solid border-[#3E4772] flex justify-center items-center"
          style={{
            borderWidth: isSmallScreen ? "2px" : "8px",
            transform: "translate(50%, 50%)",
            marginBottom: isSmallScreen ? "-10px" : "-20px",
            marginRight: isSmallScreen ? "-10px" : "-20px",
            fontSize: isSmallScreen ? "14px" : "18px", // Adjust font size based on screen size
          }}
        >
          <span>Learn while playing!</span>
          {/* <span className="tooltip-icon">ℹ️</span>
          <div className="tooltip">Tooltip text here</div> */}
        </div>

        {/* Small rectangle at the middle of the upper side */}
        <div
          className="bg-[#CDEBC5] w-56 h-32 rounded-3xl absolute top-0 left-1/2 transform -translate-x-1/2 border border-solid border-[#3E4772] flex justify-center items-center"
          style={{
            borderWidth: isSmallScreen ? "2px" : "8px",
            marginTop: isSmallScreen ? "-10px" : "-85px",
            fontSize: isSmallScreen ? "14px" : "18px", // Adjust font size based on screen size
          }}
        >
          <span>Take the screening test</span>
          {/* <span className="tooltip-icon">ℹ️</span>
          <div className="tooltip">Tooltip text here</div> */}
        </div>

        {/* Small rectangle at the middle of the lower side */}
        <div
          className="bg-[#CDEBC5] w-56 h-32 rounded-3xl absolute bottom-0 left-1/2 transform -translate-x-1/2 border border-solid border-[#3E4772] flex justify-center items-center"
          style={{
            borderWidth: isSmallScreen ? "2px" : "8px",
            marginBottom: isSmallScreen ? "-10px" : "-85px",
            fontSize: isSmallScreen ? "14px" : "18px", // Adjust font size based on screen size
          }}
        >
          <span>Keep Learning</span>
          {/* <span className="tooltip-icon">ℹ️</span>
          <div className="tooltip">Tooltip text here</div> */}
        </div>

        {/* Small circle at bottom left */}
       
       <div
  className="bg-[#CDEBC5] w-32 h-32 rounded-full relative flex items-center justify-center border-[#3E4772]"
  style={{
    borderWidth: isSmallScreen ? "2px" : "8px",
    marginTop: isSmallScreen ? "-10px" : "-85px",
    marginLeft: isSmallScreen ? "-10px" : "-85px",
  }}
>
  <div
    className="bg-[#87B17C] w-16 h-16 rounded-full absolute transform "
    style={{
      top: "50%",
      left: "50%",
      transform: "translate(-50%, -50%)",
    }}
  ></div>
</div>

        {/* Dashed line rectangle inside the main rectangle */}
        <div
          className="absolute inset-0 border-dashed border-6 border-[#CDEBC5]"
          style={{
            zIndex: "-1",
            margin: isSmallScreen ? "30px" : "60px",
            borderRadius: "12px",
          }}
        ></div>

        {/* Small circle at bottom left */}
        <div
          className="bg-[#CDEBC5] w-32 h-32 rounded-full absolute bottom-0 left-0 transform border border-solid border-[#3E4772]"
          style={{
            borderWidth: isSmallScreen ? "2px" : "8px",
            marginBottom: isSmallScreen ? "-10px" : "-85px",
            marginLeft: isSmallScreen ? "-10px" : "-85px",
          }}
        >

<div
    className="bg-[#5969B6] w-16 h-16 rounded-full absolute transform "
    style={{
      top: "50%",
      left: "50%",
      transform: "translate(-50%, -50%)",
    }}
  ></div>
        </div>
      </div>
      <div
        className="mr-18 custom-map-width-dashed h-[340px] border-dashed border-solid border-[#CDEBC5] border-l-0 relative"
        style={{
          maxWidth: isSmallScreen ? "25%" : "80%",
          minWidth: "280px",
          // maxHeight: isSmallScreen ? "65%" : "290%",
          // minHeight: "280px",
          borderRightWidth: isSmallScreen ? "3px" : "6px", // Border on the right
          borderTopWidth: isSmallScreen ? "3px" : "6px", // Border on the top
          borderBottomWidth: isSmallScreen ? "3px" : "6px", // Border on the bottom
          position: 'absolute', // Add this to the main rectangle to make sure absolute positioning works properly
        }}
      ></div>
    </div>
  );
};

export default RectanglePage;
