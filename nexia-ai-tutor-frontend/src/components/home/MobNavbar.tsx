import React from "react";
import { IoMenuOutline, IoHomeOutline, IoInformationCircleOutline, IoLayersOutline, IoRocketOutline } from "react-icons/io5";
import { RiQuestionLine } from "react-icons/ri";

const MobNavbar = () => {
  return (
    <div className="z-40 lg:hidden fixed bottom-0 w-full bg-white left-[50%] -translate-x-[50%] max-w-[500px] mob_navbar px-8">
      <div className="flex justify-between text-[28px] py-2">
        <IoMenuOutline />
        <IoHomeOutline />
        <IoInformationCircleOutline />
        <IoLayersOutline />
        <IoRocketOutline />
        <RiQuestionLine />
      </div>
    </div>
  );
};

export default MobNavbar;
