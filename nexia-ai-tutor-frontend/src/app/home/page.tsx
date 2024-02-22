// Home component (home.js)
"use client";
import Footer from "@/components/home/Footer";
import HeaderMain from "@/components/home/HeaderMain";
import HeaderTop from "@/components/home/HeaderTop";
import Hero from "@/components/home/Hero";
import Map from "@/components/home/map";
import MobNavbar from "@/components/home/MobNavbar";
import Navbar from "@/components/home/Navbar";
import NewProducts from "@/components/home/NewProducts";
import Testimonial from "@/components/home/Testimonial";
import Image from "next/image";

export default function Home() {
  return (
    <main>
      <HeaderTop />
          {/* Content inside the div */}
          <HeaderMain />
          <Navbar />
          <MobNavbar />
          <Hero />
         <Map />

      <Footer />
    </main>
  );
}
