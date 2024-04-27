"use client";
import Link from "next/link";
import LoggedInNavbar from "./navbars/loggedInNavbar";
import NotLoggedInNavbar from "./navbars/notLoggedInNavbar";

type NavbarProps = {
  isLoggedIn: boolean;
  userName: string;
};

const Navbar = (props: NavbarProps) => {
  return (
    <div
      className={`lg:fixed lg:justify-between w-full flex bg-[#3E4772] shadow-md items-center`}
      style={{
        zIndex: 999,
      }}
    >
      {/* left */}
      <div className="flex items-center pl-4">
        <Link href="/">
          <img
            src="/assets/images/logo.png"
            alt="logo"
            className="cursor-pointer h-12 w-12"
          />
        </Link>
      </div>

      {/* Center */}
      <div className="bg-[#3E4772] flex-grow flex justify-center">
        {props.isLoggedIn && (
          <div className="flex gap-10 font-medium py-4 text-[#CDEBC5]">
            <Link className="navbar__link relative" href="/myLearning">
              My Learning
            </Link>
            <Link className="navbar__link relative" href="/screening/games/1">
              Test
            </Link>
          </div>
        )}
      </div>

      {/* Right */}
      <div className="flex items-center pr-4">
        {props.isLoggedIn ? (
          <LoggedInNavbar userName={props.userName} />
        ) : (
          <NotLoggedInNavbar />
        )}
      </div>
    </div>
  );
};

export default Navbar;
