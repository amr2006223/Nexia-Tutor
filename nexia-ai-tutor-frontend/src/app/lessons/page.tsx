"use client";
import Button from "@mui/material/Button";
import Link from "next/link";
import { useRouter } from "next/navigation";

export default function AllLessons() {
  const router = useRouter();
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <Button
        onClick={() => router.back()}
        className="font-bold text-base"
        variant="contained"
        style={{
          backgroundColor: "#3E4772",
          color: "#CDEBC5",
          position: "absolute",
          top: "10px",
          left: "10px",
        }}
      >
        Back
      </Button>
      <Link href="/lessons/Animals">
        <Button
          className="font-bold text-lg"
          variant="contained"
          style={{
            backgroundColor: "#3E4772",
            color: "#CDEBC5",
          }}
        >
          Lesson 1
        </Button>
      </Link>
    </main>
  );
}
