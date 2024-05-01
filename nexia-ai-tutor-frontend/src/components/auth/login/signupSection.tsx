"use client";
import Link from "next/link";

const SignupSectionComponent = () => {
  return (
    <div className="xl:w-2/5 bg-primary text-light rounded-tr-2xl rounded-br-2xl py-36 px-12">
      <h2 className="text-3xl font-bold mb-2">New user?</h2>
      {buildHorizentalLine()}
      <p className="mb-2 text-light">Create an account now</p>
      <Link
        href="/auth/register"
        className="border-2 border-light rounded-full px-12 py-2 inline-block font-bold hover:bg-light hover:text-primary"
      >
        Sign Up
      </Link>
    </div>
  );

  function buildHorizentalLine() {
    return (
      <div className="border-2 w-1/3 border-light inline-block mb-2"></div>
    );
  }
};

export default SignupSectionComponent;
