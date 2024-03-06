import { checkLoggedInService, logoutService } from "@/services/auth/auth";
import { Button } from "@mui/material";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";

interface NavbarProps {
  isScrolled: boolean;
}

const Navbar: React.FC<NavbarProps> = ({ isScrolled }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userName, setUserName] = useState("");
  const router = useRouter();

  const checkLogin = async () => {
    if (await checkLoggedInService()) {
      setIsLoggedIn(true);
    } else {
      setIsLoggedIn(false);
    }
  };

  const handleLogout = () => {
    logoutService();
    setIsLoggedIn(false);
    router.push("/login");
  };

  useEffect(() => {
    checkLogin();
  }, []);

  useEffect(() => {
    if (isLoggedIn) {
      // get user name
      setUserName("User Name");
    }
  }, [isLoggedIn]);

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
        {isLoggedIn ? (
          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <div className="text-[#CDEBC5]">Hi, {userName}</div>
              <div className="text-red-500">|</div>
              <Button variant="contained" onClick={handleLogout}>
                Logout
              </Button>
            </div>
          </div>
        ) : (
          <div className="flex items-center gap-4">
            <Link href="/login">
              <Button variant="contained">Login</Button>
            </Link>
            <Link href="/register">
              <Button variant="contained">Register</Button>
            </Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default Navbar;
