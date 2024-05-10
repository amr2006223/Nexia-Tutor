// CustomAlert.tsx
import React from "react";

interface CustomAlertProps {
  message: string;
  onClose: () => void; // Callback function to close the alert
}

const CustomAlert: React.FC<CustomAlertProps> = ({ message, onClose }) => {
  const alertStyle = {
    padding: "20px",
    backgroundColor: "#f44336",
    color: "white",
    marginBottom: "15px",
    width: "100%",
    zIndex: 999, // Adjust the value as needed
  };

  const closeBtnStyle = {
    marginLeft: "15px",
    color: "white",
    fontWeight: "bold",
    cssFloat: "right",
    fontSize: "22px",
    lineHeight: "20px",
    cursor: "pointer",
  };

  return (
    <div style={alertStyle}>
      <span style={closeBtnStyle} onClick={onClose}>
        &times;
      </span>
      {message}
    </div>
  );
};

export default CustomAlert;
