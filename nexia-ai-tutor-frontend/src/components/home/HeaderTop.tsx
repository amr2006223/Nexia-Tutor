import React from "react";

import { BsFacebook, BsTwitter, BsInstagram, BsLinkedin } from "react-icons/bs";

const HeaderTop = () => {
  return (
    
    <div className=" hidden sm:block">
      <div className="container py-2">
        <div className="flex justify-between items-center">
          <div className="hidden lg:flex gap-1">
            <div className="header_top__icon_wrapper">
              <BsFacebook />
            </div>
            <div className="header_top__icon_wrapper">
              <BsTwitter />
            </div>
            <div className="header_top__icon_wrapper">
              <BsInstagram />
            </div>
            <div className="header_top__icon_wrapper">
              <BsLinkedin />
            </div>
          </div>

          <div className="text-[#3E4772] text-[12px]">
            <b>STAY TUNED</b> NEW LEVELS WILL BE ADDED THIS THIS WEEK 
          </div>

          <div className="flex gap-4">
         

            <select
              className="text-[#3E4772] text-[12px] w-[80px] bg-[#F0FFEB]"
              name="language"
              id="language"
            >
              <option value="English">English</option>
              <option value="French">Arabic</option>
            </select>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HeaderTop;
