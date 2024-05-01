import { FiSearch } from "react-icons/fi";

const SearchFiles = () => {
  return (
    <div className="flex items-center bg-[#CDEBC5] rounded-full border-b-2 border-[#3E4772]">
      <FiSearch className="ml-2 h-4 w-4 text-[#3E4772]" />
      <input
        type="text"
        placeholder="Search..."
        className="rounded-full bg-transparent pl-4 outline-none"
      />
    </div>
  );
};

export default SearchFiles;
