import React from "react";
import Link from "next/link";
const NotLoggedInNavbar = () => {
  const buttonStyle =
    "cursor-pointer text-[#CDEBC5] font-medium bg-[#3E4772] px-4 py-2 rounded-md hover:bg-[#2E3452] transition duration-300 ease-in-out";
  return (
    <div className="flex items-center gap-4">
      <Link href="/auth/login">
        <div className={buttonStyle}>Login</div>
      </Link>
      <Link href="/auth/register">
        <div className={buttonStyle}>Register</div>
      </Link>
    </div>
  );
};

export default NotLoggedInNavbar;
