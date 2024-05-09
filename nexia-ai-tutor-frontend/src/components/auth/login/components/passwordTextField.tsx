import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMicrophone } from "@fortawesome/free-solid-svg-icons";
import usePasswordSpeechRecognition from "@/shared/hooks/speech-recognition/usePasswordSpeechRecognition";
import VoiceListenScreen from "./voiceListenScreen";
import { useEffect } from "react";

type PasswordTextfieldProps = {
  name: string;
  field: string;
  icon: IconProp;
  handleInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  handleVoiceInput: (name: string, textValue: string) => void;
};

const PasswordTextfield = (props: PasswordTextfieldProps) => {
  const { stopListening, isListening, startListening, text } =
    usePasswordSpeechRecognition();

  useEffect(() => {
    console.log("props.name: ", props.name);

    if (text) {
      props.handleVoiceInput(props.name, text);
      stopListening();
    }
  }, [text, isListening]);

  return (
    <div className="bg-gray-100 flex items-center w-64 p-2 mb-3">
      <FontAwesomeIcon icon={props.icon} className="text-gray-400 m-2" />

      <input
        type="password"
        name={props.name}
        placeholder={props.name.charAt(0).toUpperCase() + props.name.slice(1)}
        className="text-sm bg-gray-100 outline-none flex-1"
        value={props.field || ""}
        onChange={props.handleInputChange}
      />
      <FontAwesomeIcon
        icon={faMicrophone}
        className="text-primary text-2xl m-3 flex-1 hover:text-danger cursor-pointer"
        onClick={startListening}
      />

      {isListening && <VoiceListenScreen stopListening={stopListening} />}
    </div>
  );
};

export default PasswordTextfield;
