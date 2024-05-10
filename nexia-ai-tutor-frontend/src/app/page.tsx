"use client";
import React, { useState, useEffect } from "react";
import Navbar from "@/components/home/Navbar";
import Utilities from "@/components/home/utilities/utilities";
import Hero from "@/components/home/Hero";
import Map from "@/components/home/map";
import Footer from "@/components/home/Footer";
import { checkLoginAndGetUserName } from "@/services/auth/auth";

const HomePage = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userName, setUserName] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      const { isLoggedIn, userName } = await checkLoginAndGetUserName();
      setIsLoggedIn(isLoggedIn);
      setUserName(userName);
    };
    fetchData();
  }, []);

  return (
    <div>
      <Navbar isLoggedIn={isLoggedIn} userName={userName} />
      <Utilities />
      <Hero />
      <Map />
      <Footer />
    </div>
  );
};

export default HomePage;
