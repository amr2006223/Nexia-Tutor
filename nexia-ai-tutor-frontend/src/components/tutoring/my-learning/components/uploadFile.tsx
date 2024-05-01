import { FiPlus } from "react-icons/fi";

type UploadFileProps = {
  handleFileUpload: (event: React.ChangeEvent<HTMLInputElement>) => void;
};

const UploadFile = (props: UploadFileProps) => {
  return (
    <label htmlFor="file-upload" className="cursor-pointer">
      <FiPlus className="h-6 w-6 text-[#3E4772] hover:text-[#3E4772]" />
      <input
        id="file-upload"
        type="file"
        className="hidden"
        onChange={props.handleFileUpload}
        accept="application/pdf"
      />
    </label>
  );
};

export default UploadFile;
