"use client";
import { useState, useEffect } from "react";
import Footer from "@/components/home/Footer";
import Hero from "@/components/home/Hero";
import Map from "@/components/home/map";
import Navbar from "@/components/home/Navbar";

export default function Home() {
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
      {/* <div className="h-64 bg-red-400">alo</div>
      <div className="h-64">alo</div>
      <div className="h-64">alo</div>
      <div className="h-64 bg-blue-400">alo</div> */}
      <Hero />
      <Map />
      <Footer />
    </main>
  );
}
