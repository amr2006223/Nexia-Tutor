import CloseIcon from "@mui/icons-material/Close";
import React from "react";

const CounterComponent = (props: CounterProps) => {
  const { count, color, icon } = props;
  return (
    <div
      className={`flex flex-row items-center border border-${color}-500 rounded-lg`}
    >
      <div className={`bg-${color}-500 rounded-md`}>
        {React.createElement(icon, {
          fontSize: "large",
          style: { color: "white" },
        })}
      </div>
      <div className="px-2">{count}</div>
    </div>
  );
};

export default CounterComponent;
