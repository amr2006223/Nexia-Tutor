"use client";
import { useRouter } from "next/navigation";

const LogoButton = () => {
  const router = useRouter();

  return (
    <div
      className="flex items-center pl-4 cursor-pointer"
      onClick={() => {
        router.push("/");
      }}
    >
      <img
        src="/assets/images/logo.png"
        alt="logo"
        className="cursor-pointer h-12 w-12"
      />
    </div>
  );
};

export default LogoButton;
