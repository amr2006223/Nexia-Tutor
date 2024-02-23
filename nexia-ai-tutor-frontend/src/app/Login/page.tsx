import Head from "next/head";
import Image from "next/image";
import styles from "../styles/Home.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope, faLock } from "@fortawesome/free-solid-svg-icons";

export default function Login() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen xl:py-2 ">
      <Head>
        <title>Nexia Tutor Login Page</title>
        <meta name="description" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main className="flex flex-col items-center justify-center w-full flex-1 xl:px-20 text-center">
        {/* Sign in section */}
        <div className="bg-white rounded-2xl shadow-2xl md:flex xl:w-2/3 max-w-4xl">
          <div className="xl:w-3/5 p-5 md:shrink-0">
            <div className="font-bold text-[#2A304D] text-center sm:text-left">
              <span>Logo</span>
            </div>
            <div className="py-10 text-[#2A304D]">
              <h2 className="text-3xl font-bold mb-2">Sign in to Parent Account</h2>
              <div className="border-2 w-10 border-[#2A304D] inline-block mb-2"></div>
              {/* social media section */}
              {/* <div className="flex justify-center my-2">
                <a href="#" className="border-2 rounded-full p-2 mx-1">
                  <FontAwesomeIcon icon={faFacebookF} />
                </a>
                <a href="#" className="border-2 rounded-full p-2 mx-1">
                  <FontAwesomeIcon icon={faInstagram} />
                </a>
                <a href="#" className="border-2 rounded-full p-2 mx-1">
                  <FontAwesomeIcon icon={faLinkedin} />
                </a>
              </div> */}
              {/* <p className="text-gray-400">or use your email account</p> */}
              <div className="flex flex-col items-center">
                <div className="bg-gray-100 flex items-center w-64 p-2 mb-3 relative">
                  <FontAwesomeIcon icon={faEnvelope} className="text-gray-400 m-2" />
                  
                  <input
                    type="text"
                    name="username"
                    placeholder="username"
                    className="text-sm bg-gray-100 outline-none flex-1"
                  />
                </div>
                <div className="bg-gray-100 flex items-center w-64 p-2 mb-3">
                  <FontAwesomeIcon icon={faLock} className="text-gray-400 m-2" />
                  <input
                    type="password"
                    name="password"
                    placeholder="password"
                    className="text-sm bg-gray-100 outline-none flex-1"
                  />
                </div>
                <div className="flex w-64 mb-5 justify-between">
                  <label htmlFor="#" className="text-xs flex items-center">
                    <input type="checkbox" name="remember" className="mr-1" />
                    Remember me
                  </label>
                  <a href="#" className="text-xs">
                    <p>Forgot Password?</p>
                  </a>
                </div>
                <a
                  href="#"
                  className="border-2 border-[#2A304D] rounded-full px-12 py-2 inline-block font-semibold hover:bg-[#2A304D] hover:text-gray-100"
                >
                  Sign in
                </a>
              </div>
            </div>
          </div>
          {/* sing up section */}
          {/* blue */}
          <div className="xl:w-2/5 bg-[#2A304D] text-[#CDEBC5] rounded-tr-2xl rounded-br-2xl py-36 px-12">
            <h2 className="text-3xl font-bold mb-2">Welcome !</h2>
            <div className="border-2 w-10 border-white inline-block mb-2 "></div>
            <p className="mb-2 text-[#D9DEFF]">Fill up personal information as a parent, add your child account, and start journey with us.</p>
            <a
              href="#"
              className="border-2 border-white rounded-full px-12 py-2 inline-block font-semibold hover:bg-gray-100 hover:text-[#2A304D]"
            >
              Sign Up
            </a>
          </div>
        </div>
      </main>
    </div>
  );
}
