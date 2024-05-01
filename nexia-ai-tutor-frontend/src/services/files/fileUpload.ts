import { lessonFileData } from "@/types/lessons/fileData";

export const appendFilesToLocalStorage = async (files: lessonFileData) => {
  // get files from local storage and parse to File[]
  const currentFiles = await getFilesFromLocalStorage();
  const newFiles = [...currentFiles, files];
  localStorage.setItem("files", JSON.stringify(newFiles));
};

export const getFilesFromLocalStorage = async () => {
  const files = await localStorage.getItem("files");
  if (files) {
    return JSON.parse(files) as lessonFileData[];
  }
  return [];
};

export const removeFilesFromLocalStorage = async (index: number) => {
  const files = await getFilesFromLocalStorage();
  const newFiles = files.filter((_, i) => i !== index);
  localStorage.setItem("files", JSON.stringify(newFiles));
};
