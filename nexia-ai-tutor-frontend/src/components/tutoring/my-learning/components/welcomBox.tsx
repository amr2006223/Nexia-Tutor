import React from "react";

const WelcomBox = ({ username }: { username: string }) => {
  return (
    <div className="flex items-center">
      <h1 className="text-2xl font-bold mr-4">Welcome Back, {username}</h1>
      <img
        src="/assets/images/potato-heart.png"
        alt="Potato Heart"
        className="w-24 h-16"
      />
    </div>
  );
};

export default WelcomBox;
