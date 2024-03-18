"use client";

// import HeaderMain from "@/components/home/HeaderMain";
// import MobNavbar from "@/components/home/MobNavbar";
import Navbar from "@/components/home/Navbar";
// import Learn from "@/components/myLearning/learn";
import LearningPage from "@/components/myLearning/page";
import { useState, useEffect } from "react";

export default function TodayLesson() {
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.scrollY;
      setIsScrolled(scrollTop > 0);
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <main>
      <Navbar isScrolled={isScrolled} />
      {/* <HeaderMain />
      <MobNavbar /> */}
      <LearningPage />
    </main>
  );
}
