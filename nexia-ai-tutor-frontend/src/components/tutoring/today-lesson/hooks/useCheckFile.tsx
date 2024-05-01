// import { getFilesFromLocalStorage } from "@/services/files/fileUpload";
import { useLessonStore } from "@/shared/state/uploadedLessons";
import { useRouter } from "next/navigation";

const useCheckFile = () => {
  const router = useRouter();
  const lessonState = useLessonStore();
  let fileName = "";
  fileName = lessonState.fileName;
  // fileName.split(".")[0]

  // if (fileName === "") {
  //   const files = getFilesFromLocalStorage();
  //   const file = files[0];
  //   fileName = file.name;
  // }

  if (fileName === "") {
    router.push("/tutoring/my-learning");
  }

  return fileName;
};

export default useCheckFile;
