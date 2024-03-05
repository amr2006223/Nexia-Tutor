import { Button } from "@mui/material";
import Link from "next/link";
import React from "react";

interface NavbarProps {
  isScrolled: boolean;
}

const Navbar: React.FC<NavbarProps> = ({ isScrolled }) => {
  return (
    <div
      className={`hidden lg:flex lg:justify-between w-full bg-[#3E4772] shadow-md ${
        isScrolled && "z-40 fixed top-0"
      }`}
    >
      {/* left */}
      <div className="flex items-center pl-4">
        <div className="text-black">logo</div>
      </div>
      <div className="bg-[#3E4772] flex-grow flex justify-center">
        {/* Center */}
        <div className="flex gap-10 font-medium py-4 text-[#CDEBC5]">
          <Link className="navbar__link relative" href="#">
            Home
          </Link>
          <Link className="navbar__link relative" href="#">
            Roadmap
          </Link>
          <Link className="navbar__link relative" href="#">
            About Nexia
          </Link>
        </div>
      </div>
      <div className="flex items-center pr-4">
        {/* Right */}
        <Link href="/Login">
          <Button
            variant="contained"
            color="primary"
            size="large"
            className="text-[#3E4772] bg-[#CDEBC5] hover:bg-[#A3D9A5] hover:text-[#3E4772]"
          >
            Login
          </Button>
        </Link>
      </div>
    </div>
  );
};

export default Navbar;
