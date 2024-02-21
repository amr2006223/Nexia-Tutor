import React from "react";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";

type CorrectButtonComponentProps = {
  checkFunction: Function;
  theme: "dark" | "light";
};

const CorrectButtonComponent = ({
  theme,
  checkFunction,
}: CorrectButtonComponentProps) => {
  const iconColor = theme === "dark" ? "#3e4772" : "#cdebc5";
  const backgroundColor = theme === "dark" ? "#cdebc5" : "#3e4772";

  return (
    <CheckCircleIcon
      className="cursor-pointer rounded-full "
      onClick={() => checkFunction()}
      style={{
        color: iconColor,
        // background: backgroundColor
      }}
    />
  );
};

export default CorrectButtonComponent;
