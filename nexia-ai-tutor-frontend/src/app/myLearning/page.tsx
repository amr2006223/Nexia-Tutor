"use client";

import HeaderMain from "@/components/home/HeaderMain";
import MobNavbar from "@/components/home/MobNavbar";
import Learn from "@/components/myLearning/learn";
import LearningPage from "@/components/myLearning/page";

export default function TodayLesson() {

  return (
    <main>
      <HeaderMain />
      <MobNavbar />
      <LearningPage />
    </main>
  );
}
