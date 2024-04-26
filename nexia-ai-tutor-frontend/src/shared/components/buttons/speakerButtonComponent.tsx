import React from "react";
import VolumeUpRoundedIcon from "@mui/icons-material/VolumeUpRounded";
import { playMp3Sound, playSoundFromGoogle } from "@/shared/utils/play-sounds";

type SpeakerButtonComponentProps = {
  sound: string;
  from_google: boolean;
  theme: "dark" | "light";
};

const SpeakerButtonComponent = ({
  sound,
  from_google,
  theme,
}: SpeakerButtonComponentProps) => {
  const playSound = async () => {
    try {
      if (from_google) {
        await playSoundFromGoogle(sound);
      } else {
        await playMp3Sound(sound);
      }
    } catch (e) {
      console.error(e);
    }
  };

  const iconColor = theme === "dark" ? "#3e4772" : "#cdebc5";

  return (
    <VolumeUpRoundedIcon
      className="cursor-pointer "
      onClick={() => playSound()}
      style={{ color: iconColor, fontSize: "2rem" }}
      
    />
  );
};

export default SpeakerButtonComponent;
