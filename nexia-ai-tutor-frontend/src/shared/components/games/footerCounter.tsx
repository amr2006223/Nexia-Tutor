import CounterComponent from "@/shared/components/counter/CounterComponent";
import CloseIcon from "@mui/icons-material/Close";
import CheckIcon from "@mui/icons-material/Check";

type FooterCounterProps = {
  greenCounterNumber: number;
  redCounterNumber: number;
};

const FooterCounter = (props: FooterCounterProps) => {
  return (
    <div
      style={{
        position: "fixed",
        bottom: 0,
        width: "100%",
        backgroundColor: "#fff",
        padding: "10px",
        borderTop: "2px solid #3e4772",
      }}
      className="flex flex-row justify-evenly items-center mt-3"
    >
      <CounterComponent
        count={props.redCounterNumber}
        color="red"
        icon={CloseIcon}
      />
      <CounterComponent
        count={props.greenCounterNumber}
        color="green"
        icon={CheckIcon}
      />
    </div>
  );
};

export default FooterCounter;
