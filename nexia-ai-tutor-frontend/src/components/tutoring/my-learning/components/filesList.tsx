import { lessonFileData } from "@/types/lessons/fileData";
import FileRow from "./fileRow";

type FilesListProps = {
  files: lessonFileData[];
  handleRemoveFile: (index: number) => void;
};

const FilesList = (props: FilesListProps) => {
  return (
    <div className="mt-4 h-96 overflow-y-auto">
      {props.files.map((file, index) => (
        <FileRow
          key={index}
          index={index}
          file={file}
          handleRemoveFile={props.handleRemoveFile}
        />
      ))}
    </div>
  );
};

export default FilesList;
