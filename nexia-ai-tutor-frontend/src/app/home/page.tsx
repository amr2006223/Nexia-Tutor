"use client";
import { useState, useEffect } from 'react';
import Footer from "@/components/home/Footer";
import HeaderMain from "@/components/home/HeaderMain";
import HeaderTop from "@/components/home/HeaderTop";
import Hero from "@/components/home/Hero";
import Button from "@/components/home/homeScreeningTestButton";
import Map from "@/components/home/map";
import MobNavbar from "@/components/home/MobNavbar";
import Navbar from "@/components/home/Navbar"; 
import Image from "next/image";

export default function Home() {
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.scrollY;
      setIsScrolled(scrollTop > 0);
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <main>
      <HeaderTop />
      <MobNavbar />
      <HeaderMain />
      <Navbar isScrolled={isScrolled} /> 
      <Hero />
      <Map />
      <Button />
      <Footer />
    </main>
  );
}
