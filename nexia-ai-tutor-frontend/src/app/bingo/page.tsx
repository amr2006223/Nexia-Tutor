"use client";
import BingoBoard from "@/components/bingo/board";
import { Paper } from "@mui/material";
import { useSearchParams } from "next/navigation";

const Index = () => {
  const searchParams = useSearchParams();
  const keywordValue = searchParams.get("keyword");
  const uppercaseKeyWord = keywordValue ? keywordValue.toUpperCase() : "";
  const keyWord = uppercaseKeyWord ? uppercaseKeyWord.split("") : [];

  const size = keyWord.length;
  const EnglishAlphabet: string[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

  return (
    <div>
      {size > 5 ? (
        <Paper>Size is too big</Paper>
      ) : (
        <BingoBoard size={size} letters={EnglishAlphabet} keyword={keyWord} />
      )}
    </div>
  );
};

export default Index;
