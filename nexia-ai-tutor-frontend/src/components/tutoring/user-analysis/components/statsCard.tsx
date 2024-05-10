import React from "react";

type Props = {
  data_name: string;
  data_value: number;
  icon: React.ElementType;
};

const StatsCard = (props: Props) => {
  return (
    <div className="bg-light p-4 m-5 rounded-md shadow-md transform transition duration-500 hover:scale-110">
      {/* <div className="flex flex-row items-center">
        {React.createElement(props.icon, {
          fontSize: "large",
          style: { color: "white" },
        })}
      </div> */}

      <div className="text-lg font-bold text-primary">{props.data_name}</div>
      <div className="text-base font-semibold text-info">
        {props.data_value}
      </div>
    </div>
  );
};

export default StatsCard;
