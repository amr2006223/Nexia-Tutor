"use client";
import React from "react";
import BingoBoard from "@/components/games/tutoring-games/bingo/board";
import { Paper } from "@mui/material";
import useWordSearchParams from "@/shared/hooks/useWordSearchParams";

const BingoGamePage = () => {
  const { keywordParams } = useWordSearchParams();
  const keyWord = keywordParams.split("");

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

export default BingoGamePage;
