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
        borderTop: "2px solid #3e4772",
        justifyContent: "space-around",
        display: "flex",
        flexDirection: "row",
        position: "fixed",
        alignItems: "center",
        marginTop: "3px",
        padding: "0.75rem",
        backgroundColor: "white",
        width: "100%",
        bottom: "0",
      }}
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
