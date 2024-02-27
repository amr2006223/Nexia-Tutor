import {create} from "zustand"
interface LessonsFile{
    fileName: string,
    keywords: string[],
    setLessonDetails:  (fileName: string, keywords: string[])  => void;
}
export const useLessonStore = create<LessonsFile>((set) => ({
  fileName: "",
  keywords: [],

  setLessonDetails: (fileName: string, keywords: string[]) => {
    set({ fileName: fileName, keywords: keywords });
  },
}));