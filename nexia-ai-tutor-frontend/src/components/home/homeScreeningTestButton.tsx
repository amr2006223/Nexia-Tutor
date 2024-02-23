import React from 'react';
import { HiOutlineDocumentText } from 'react-icons/hi';

const Button = () => {
  const isSmallScreen = window.innerWidth <= 767;
  return (
    <div className={` ${isSmallScreen ? 'min-h-48' : 'min-h-screen'}  bg-[#3E4772] flex justify-center items-center`}>

      <button className={`bg-[#CDEBC5] hover:scale-125 bg-opacity-20 hover:bg-opacity-40 border-[#CDEBC5] border-4 rounded-2xl flex items-center space-x-2  
    ${isSmallScreen ? 'py-2' : 'py-8'}
     ${isSmallScreen ? 'px-2' : 'px-24'}
     ${isSmallScreen ? 'ml-4' : ''}
     ${isSmallScreen ? 'mr-4' : ''}
     `}>

        <HiOutlineDocumentText color="#CDEBC5" size={isSmallScreen ? 46 : 64} />
        <span className={`text-[#CDEBC5] 
    ${isSmallScreen ? 'text-lg' : 'text-2xl'}
    ${isSmallScreen ? 'ml-2' : ''}
    ${isSmallScreen ? 'mr-2' : ''}
    `}>
          Take the screening test now</span>
      </button>
    </div>
  );
};

export default Button;
