import type { Metadata } from "next";
import "./globals.css";
import Utilities from "@/components/home/utilities/utilities";

export const metadata: Metadata = {
  title: "Nexia AI Tutor",
  description: "Dyslexia Learning System for Children",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <Utilities />
        {children}
      </body>
    </html>
  );
}
