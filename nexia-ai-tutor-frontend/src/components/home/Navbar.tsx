"use client";
import LoggedInNavbar from "./navbars/loggedInNavbar";
import NotLoggedInNavbar from "./navbars/notLoggedInNavbar";
import LogoButton from "@/shared/components/buttons/logoButton";
import NavbarLink from "./navbars/navbarLink";
import { checkIsUserTested } from "@/services/report/userTest";

type NavbarProps = {
  isLoggedIn: boolean;
  userName: string;
};

const Navbar = (props: NavbarProps) => {
  const handleclcik = async () => {
    await checkIsUserTested();
  };

  return (
    <div
      className="lg:fixed lg:justify-between w-full flex bg-[#3E4772] shadow-md items-center"
      style={{
        zIndex: 999,
      }}
    >
      {/* left */}
      <LogoButton />

      {/* Center */}
      <div className="flex-grow flex justify-center">
        {props.isLoggedIn && (
          <div className="flex gap-10 font-medium py-4 text-[#CDEBC5]">
            <NavbarLink href="/tutoring/my-learning">My Learning</NavbarLink>
            <NavbarLink href="/screening/games/1">Test</NavbarLink>
            <button onClick={handleclcik}>alo</button>
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
