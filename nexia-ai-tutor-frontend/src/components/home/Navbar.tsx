import { checkLoggedInService, logoutService } from "@/services/auth/auth";
import { getUserDetailsService } from "@/services/user/userDetails";
import { useUserStore } from "@/shared/state/user";
import { Button } from "@mui/material";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";

const Navbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userName, setUserName] = useState("");
  const router = useRouter();
  const userState = useUserStore();
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
  const handleGetUserDetails = async () => {
    // get user name
    const user = await getUserDetailsService();
    userState.setUser(user);
    setUserName(user.username);
  };
  useEffect(() => {
    if (isLoggedIn) {
      handleGetUserDetails();
    }
  }, [isLoggedIn]);

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
      <div className="bg-[#3E4772] flex-grow flex justify-center">
        {/* Center */}
        <div className="flex gap-10 font-medium py-4 text-[#CDEBC5]">
          <Link className="navbar__link relative" href="/">
            Home
          </Link>
          {/* <Link className="navbar__link relative" href="#">
            Roadmap
          </Link> */}
          <Link className="navbar__link relative" href="/myLearning">
            My Learning
          </Link>
          <Link className="navbar__link relative" href="/screening/games/1">
            Test
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
