import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMicrophone } from "@fortawesome/free-solid-svg-icons";
import useUsernameSpeechRecognition from "@/shared/hooks/speech-recognition/useUsernameSpeechRecognition";
import VoiceListenScreen from "./voiceListenScreen";
import { useEffect } from "react";

type LoginTextfieldProps = {
  name: string;
  field: string;
  icon: IconProp;
  isPassword?: boolean;
  handleInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  handleVoiceInput: (name: string, textValue: string) => void;
};

const LoginTextfield = (props: LoginTextfieldProps) => {
  const { stopListening, isListening, startListening, text } =
  useUsernameSpeechRecognition();

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
        // type={props.isPassword ? "password" : "text"}
        type="text"
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

      {/* {isListening ? ( */}
      {/* <VoiceListenScreen
        stopListening={stopListening}
        isListening={isListening}
      /> */}
      {/* ) : null} */}
      {isListening && <VoiceListenScreen stopListening={stopListening} />}
    </div>
  );
};

export default LoginTextfield;
