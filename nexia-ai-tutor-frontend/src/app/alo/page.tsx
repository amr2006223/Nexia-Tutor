"use client";
import { getReport } from "@/services/report/downloadReport";
import React from "react";

const page = () => {
  const handleSubmit = async () => {
    await getReport();
  };
  return (
    <div
      className="
      flex 
      flex-col 
      items-center 
      justify-center 
      min-h-screen 
      p-4
      gap-4
    "
    >
      <div className="bg-primary text-black p-4 rounded-lg">primary</div>
      <div className="bg-secondary text-black p-4 rounded-lg">secondary</div>
      <div className="bg-background text-black p-4 rounded-lg">background</div>
      <div className="bg-light text-black p-4 rounded-lg">light</div>
      <div className="bg-success text-black p-4 rounded-lg">success</div>
      <div className="bg-info text-black p-4 rounded-lg">info</div>
      <div className="bg-warning text-black p-4 rounded-lg">warning</div>
      <div className="bg-danger text-black p-4 rounded-lg">danger</div>
      <div className="bg-blackish text-black p-4 rounded-lg">blackish</div>
    </div>
  );
};

export default page;
