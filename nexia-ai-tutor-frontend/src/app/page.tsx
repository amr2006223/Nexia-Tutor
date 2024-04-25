"use client";
import Footer from "@/components/home/Footer";
import Hero from "@/components/home/Hero";
import Map from "@/components/home/map";
import Navbar from "@/components/home/Navbar";

export default function Home() {
  return (
    <main>
      <Navbar />
      <Hero />
      <Map />
      <Footer />
    </main>
  );
}
