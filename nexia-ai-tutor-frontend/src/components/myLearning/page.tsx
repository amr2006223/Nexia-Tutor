import Head from 'next/head';
import { FiPlay, FiPlus, FiSearch } from 'react-icons/fi';

export default function LearningPage() {
  return (
    <div className="bg-[#CDEBC5] flex flex-col items-center justify-center min-h-screen py-6 ">
      <Head>
        <title>MyLearning</title>
      </Head>
      <div className="flex items-center">
        <h1 className="text-2xl font-bold mr-4">Welcome Back, Nancy</h1>
        <img src="/assets/images/potato-heart.png" alt="Potato Heart" className="w-24 h-16" />
      </div>

      {/* Rectangle 1 */}
      <div className="w-full max-w-xl mt-8 p-24 bg-[#E3FFDC] rounded-lg text-2xl font-bold">
        <p className="text-center text-green-800 opacity-50">Start your Journey now!</p>
      </div>

      {/* Rectangle 2 */}
      <div className="w-full max-w-xl mt-8 p-8 bg-[#E3FFDC] rounded-lg">
        <div className="flex justify-between items-center">
          <h2 className="text-xl font-semibold text-[#3E4772]">Your Contents</h2>
          <div className="flex items-center bg-[#CDEBC5] rounded-full border-b-2 border-[#3E4772]">
            <FiSearch className="ml-2 h-4 w-4 text-[#3E4772]" />
            <input type="text" placeholder="Search..." className="rounded-full bg-transparent pl-4 outline-none" />
          </div>
          <label htmlFor="file-upload" className="cursor-pointer">
            <FiPlus className="h-6 w-6 text-[#3E4772] hover:text-[#3E4772]" />
            <input id="file-upload" type="file" className="hidden" />
          </label>
        </div>
        <hr className="mt-2 border-2 rounded-full border-[#3E4772]" />
        
        {/* Scrollable area */}
        <div className="mt-4 h-96 overflow-y-auto">
          {/* Small rectangles */}
          <div className="flex items-center mt-4 bg-[#CDEBC5] rounded-lg p-4 drop-shadow-lg">
            <p className="ml-2">Content 1</p>
            <button className="ml-auto bg-[#CDEBC5] text-[#3E4772]">Continue</button>
            <FiPlay className="ml-2 h-6 w-6" fill="#3182CE" /> {/* Set the fill property for solid icon */}
          </div>
          {/* Add more small rectangles here if needed */}
        </div>
        <hr className="mt-4 border-2 rounded-full border-[#3E4772]" />
      </div>
    </div>
  );
}
