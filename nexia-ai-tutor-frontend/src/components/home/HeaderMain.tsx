import React from "react";
import { Button } from "@mui/material";

const HeaderMain = () => {
  return (
    <div className="relative">
      <div
        className="w-full h-auto left-1/2 transformer"
        style={{ backgroundImage: 'url("/hero.gif")' }}
      >
        {/* Apply opacity to the background only */}
        <div className="absolute inset-0 bg-[#CDEBC5] opacity-50"></div>

        <div className="py-2">
          <div className="container sm:flex justify-between items-center">
            <div className="font-bold text-4xl text-center pb-4 sm:pb-0 text-blackish">
              Logo
            </div>

            <div className="w-full sm:w-[400px] md:w-[80%] relative underline">
              <b className="bold-text">Screening Test</b>
            </div>
    

            <div className="hidden lg:flex gap-4 text-[30px]">
              <Button
                className="font-bold text-base"
                variant="contained"
                style={{
                  backgroundColor: "#3E4772",
                  color: "#CDEBC5",
                }}
              >
                Login
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HeaderMain;
