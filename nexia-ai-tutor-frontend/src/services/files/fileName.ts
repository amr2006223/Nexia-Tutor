"use server";
import { cookies } from "next/headers";

export const saveFileNameServerSide = async (
  fileName: string,
  keywords: string[]
) => {
  cookies().set("fileName", fileName);
  cookies().set("keywords", JSON.stringify(keywords));
};

export const getFileNameServerSide = async () => {
  return cookies().get("fileName")?.value;
};

export const getKeywordsServerSide = async () => {
  return JSON.parse(cookies().get("keywords")?.value || "[]");
};
