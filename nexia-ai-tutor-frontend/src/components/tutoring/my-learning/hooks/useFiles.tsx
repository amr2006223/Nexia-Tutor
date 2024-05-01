"use client";

import {
  appendFilesToLocalStorage,
  getFilesFromLocalStorage,
  removeFilesFromLocalStorage,
} from "@/services/files/fileUpload";
import { keyWordExtractionFromFile } from "@/services/keywords/keyWordExtraction";
import { lessonFileData } from "@/types/lessons/fileData";
import { useEffect, useState } from "react";

const useFiles = () => {
  const [files, setFiles] = useState<lessonFileData[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      const files: lessonFileData[] = await getFilesFromLocalStorage();
      setFiles(files);
    };
    fetchData();
  }, []);

  const getFileKeyWords = async (file: File) => {
    try {
      // Create FormData object to send file
      const formData = new FormData();
      formData.append("file", file); // Append file data with name

      // Send POST request to backend endpoint
      const keywords = await keyWordExtractionFromFile(formData);
      return keywords;
    } catch (error) {
      // Handle errors
      console.error("Error uploading PDF:", error);
    }
  };

  const handleFileUpload = async (
    event: React.ChangeEvent<HTMLInputElement>
  ): Promise<void> => {
    const files = event.target.files;

    if (files) {
      const pdfFiles = Array.from(files).filter(
        (file) => file.type === "application/pdf"
      );

      // 1. get file data from api
      const keyWords: string[] = await getFileKeyWords(pdfFiles[0]);

      const fileData: lessonFileData = {
        name: pdfFiles[0].name,
        keywords: keyWords,
      };

      // 2. save file data to local storage
      await appendFilesToLocalStorage(fileData);
      // 3. get file data from local storage

      const allFiles = await getFilesFromLocalStorage();
      setFiles(allFiles);
    }
  };

  const handleRemoveFile = async (index: number) => {
    await removeFilesFromLocalStorage(index);
    const files = await getFilesFromLocalStorage();
    setFiles(files);
  };

  return { files, handleFileUpload, handleRemoveFile };
};

export default useFiles;
