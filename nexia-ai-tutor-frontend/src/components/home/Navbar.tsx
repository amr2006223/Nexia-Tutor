import Link from "next/link";
import React, { useState, useEffect } from "react";
import styles from './Navbar.module.css';

interface NavbarProps {
  isScrolled: boolean;
}

const Navbar: React.FC<NavbarProps> = ({ isScrolled }) => {
  return (
    <div className={`hidden lg:block ${isScrolled ? ' z-40  fixed top-0 left-0 right-0 bg-[#3E4772] shadow-md' : ''}`}>
      <div className="container bg-[#3E4772]">
        <div className="bg-[#3E4772] flex w-fit gap-10 mx-auto font-medium py-4 text-[#CDEBC5]">
          <Link className="navbar__link relative" href="#">
            Home
          </Link>
          <Link className="navbar__link relative" href="#">
            About Nexia
          </Link>
          <Link className="navbar__link relative" href="#">
            Roadmap
          </Link>
          <Link className="navbar__link relative" href="#">
            Start Now
          </Link>
          <Link className="navbar__link relative" href="#">
            Help
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
