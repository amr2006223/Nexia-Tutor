import Head from 'next/head';
import { FiPlay, FiPlus, FiSearch, FiMic } from 'react-icons/fi';

export default function TodayLesson() {
  return (
    <div className="bg-[#CDEBC5] flex flex-col items-center justify-center min-h-screen py-6 ">
      <Head>
        <title>Today's Lesson</title>
      </Head>
      <div className="flex items-center">
        <h1 className="text-2xl font-bold mr-4">Today's Lesson</h1>
      </div>
      <p className="mt-2 text-xl text-[#5C7C54]">Words of today’s lesson are ..</p>

      {/* Rectangle  */}
      <div className="w-full max-w-xl mt-8 p-8 bg-[#E3FFDC] rounded-lg">
        <div className="flex justify-between items-center">
         
          <h2 className="text-xl font-semibold text-[#3E4772] text-center">Lesson 1 </h2>
          {/* {"\n"} */}
          <div></div> 
        </div>
        {/* Scrollable area */}
        <div className="mt-4 h-96 overflow-y-auto">
          {/* word */}
          <div className="flex items-center justify-center mt-4 bg-[#CDEBC5] rounded-lg p-4 drop-shadow-lg">
            <FiMic className="ml-2 h-6 w-6" />
            <p className="ml-2 text-xl "><strong>Hello</strong></p> 
            <button className="ml-auto bg-[#CDEBC5] text-[#3E4772]">start</button>
            <FiPlay className="ml-2 h-6 w-6" fill="#3182CE" /> 
          </div>
       
        {/* more words */}
        
        </div>
      </div>
    </div>
  );
}
