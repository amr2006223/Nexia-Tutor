import React from "react";

const CounterComponent = (props: CounterProps) => {
  const { count, color, icon } = props;

  return (
    <div
      className={`flex flex-row items-center border rounded-lg mx-4`}
      style={{
        borderColor: `${color}`,
      }}
    >
      <div
        className={`rounded-md`}
        style={{
          backgroundColor: `${color}`,
        }}
      >
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
