type FileDetailsProps = {
  fileName: string;
};

const FileDetails = (props: FileDetailsProps) => {
  return (
    <div>
      <div className="flex items-center">
        <h1 className="text-2xl font-bold mr-4 underline">{props.fileName}</h1>
      </div>
      <p className="mt-2 text-xl text-[#5C7C54]">
        Words of {props.fileName} are ..
      </p>
    </div>
  );
};

export default FileDetails;
