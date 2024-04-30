import type { Metadata } from "next";
import "./globals.css";

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
      <body>{children}</body>
    </html>
  );
}
