import useSpeechRecognition from "@/shared/hooks/speech-recognition/useUsernameSpeechRecognition";
import { Box } from "@mui/material";

type VoiceListenScreenProps = {
  stopListening: () => void;
};

const VoiceListenScreen = (props: VoiceListenScreenProps) => {
  return (
    <div
      style={{
        display: "flex",
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        width: "30%",
        height: "40%",
        background: "white",
        justifyContent: "center",
        alignItems: "center",
        boxShadow: "0 0 10px 0 rgba(0, 0, 0, 0.1)",
        zIndex: 100,
      }}
    >
      <Box
        sx={{
          position: "relative",
          zIndex: 0,
          background: "white",
        }}
      >
        <div className="flex items-center justify-center mb-4">
          <div className="w-4 h-4 bg-red-600 rounded-full mr-2 animate-pulse"></div>
          <p className="text-gray-800 font-bold">Listening</p>
        </div>
        <button
          className="border-2 border-primary rounded-full p-2 inline-block font-bold hover:bg-primary hover:text-white"
          onClick={props.stopListening}
        >
          Stop Listening
        </button>
      </Box>
    </div>
  );
};

export default VoiceListenScreen;
