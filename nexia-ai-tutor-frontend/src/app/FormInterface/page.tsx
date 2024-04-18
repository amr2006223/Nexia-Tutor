// FormInterface.tsx
"use client";
import React, { useEffect, useState } from "react";
import { Button, Container, Typography } from "@mui/material";
import Navbar from "@/components/home/Navbar";
import Swal from "sweetalert2";
import "animate.css";
import { TConductorInstance } from "react-canvas-confetti/dist/types";
import Pride from "react-canvas-confetti/dist/presets/pride";
import { useRouter } from "next/navigation";
import { getReport } from "@/services/report/downloadReport";
import { getTokenValue } from "@/services/auth/auth";
import { useScreeningGamesStore } from "@/shared/state/screening-games";
interface FormData {
  record: string;
}

const FormInterface: React.FC = () => {
  const gamesData = useScreeningGamesStore();
  const router = useRouter();
  const [record2, setRecord] = useState('{"record":{}}');
  const [isScrolled, setIsScrolled] = useState(false);
  const [conductor, setConductor] = useState<TConductorInstance>();
  let record = "";
  const [formData, setFormData] = useState<FormData>({
    record: "",
  });

  const getStringRecord = () => {
    for (let i = 0; i < gamesData.games_result.length; i++) {
      let game = i + 1;
      let clicks = gamesData.games_result[i][0];
      let hits = gamesData.games_result[i][1];
      let misses = gamesData.games_result[i][2];
      let score = gamesData.games_result[i][3];
      let accuracy = gamesData.games_result[i][4];
      let missrate = gamesData.games_result[i][5];

      record += `"Clicks${game}":"${clicks}","Hits${game}":"${hits}","Misses${game}":"${misses}","Score${game}":"${score}","Accuracy${game}":"${accuracy}","Missrate${game}":"${missrate}",`;

      // remove last comma
      if (i === gamesData.games_result.length - 1) record = record.slice(0, -1);
    }
  };
  const getToken = async () => {
    getStringRecord();
    const record2 = `{"id":"${await getTokenValue()}","username":"test","record":{"Gender":"2","Nativelang":"0","Otherlang":"0","Age":"8", ${record}, "Clicks10":"6","Hits10":"6","Misses10":"0","Score10":"6","Accuracy10":"1","Missrate10":"0","Clicks11":"2","Hits11":"1","Misses11":"1","Score11":"1","Accuracy11":"0.5","Missrate11":"0.5","Clicks12":"0","Hits12":"0","Misses12":"0","Score12":"0","Accuracy12":"0","Missrate12":"0","Clicks13":"0","Hits13":"0","Misses13":"0","Score13":"0","Accuracy13":"0","Missrate13":"0","Clicks14":"2","Hits14":"0","Misses14":"2","Score14":"0","Accuracy14":"0","Missrate14":"1","Clicks15":"3","Hits15":"3","Misses15":"0","Score15":"3","Accuracy15":"1","Missrate15":"0","Clicks16":"5","Hits16":"4","Misses16":"1","Score16":"4","Accuracy16":"0.8","Missrate16":"0.2","Clicks17":"3","Hits17":"3","Misses17":"0","Score17":"3","Accuracy17":"1","Missrate17":"0","Clicks18":"2","Hits18":"2","Misses18":"0","Score18":"2","Accuracy18":"1","Missrate18":"0","Clicks19":"2","Hits19":"2","Misses19":"0","Score19":"2","Accuracy19":"1","Missrate19":"0","Clicks20":"4","Hits20":"1","Misses20":"3","Score20":"1","Accuracy20":"0.25","Missrate20":"0.75","Clicks21":"3","Hits21":"3","Misses21":"0","Score21":"3","Accuracy21":"1","Missrate21":"0","Clicks22":"3","Hits22":"3","Misses22":"0","Score22":"3","Accuracy22":"1","Missrate22":"0","Clicks23":"3","Hits23":"3","Misses23":"0","Score23":"3","Accuracy23":"1","Missrate23":"0","Clicks24":"1","Hits24":"1","Misses24":"0","Score24":"1","Accuracy24":"1","Missrate24":"0","Clicks25":"3","Hits25":"1","Misses25":"2","Score25":"1","Accuracy25":"0.333333","Missrate25":"0.666667","Clicks26":"2","Hits26":"2","Misses26":"0","Score26":"2","Accuracy26":"1","Missrate26":"0","Clicks27":"1","Hits27":"0","Misses27":"1","Score27":"0","Accuracy27":"0","Missrate27":"1","Clicks28":"0","Hits28":"0","Misses28":"0","Score28":"0","Accuracy28":"0","Missrate28":"0","Clicks29":"25","Hits29":"1","Misses29":"2","Score29":"1","Accuracy29":"0.04","Missrate29":"0.08","Clicks30":"2","Hits30":"2","Misses30":"0","Score30":"2","Accuracy30":"1","Missrate30":"0","Clicks31":"29","Hits31":"2","Misses31":"0","Score31":"2","Accuracy31":"0.0689655","Missrate31":"0","Clicks32":"12","Hits32":"1","Misses32":"2","Score32":"1","Accuracy32":"0.0833333","Missrate32":"0.166667"}}`;
    console.log("record2", record2);
    setRecord(record2);
  };
  getToken();
  const handleInputChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ): void => {
    setFormData({
      // ...formData,
      record: record2,
      // record: event.target.value,
    });
  };

  const handleSubmit = async (
    event: React.FormEvent<HTMLFormElement>
  ): Promise<void> => {
    event.preventDefault();
    console.log("Form submitted:", record2);
    // You can perform further actions here
    try {
      const response = await fetch(`${process.env.SCREENING_API}predict`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(record2),
      });

      if (!response.ok) {
        throw new Error("API request failed");
      }

      const responseData = await response.json();
      console.log("API response:", responseData);

      playAnimations();
      //  router.push("/screening/report?prediction="+responseData);
    } catch (error) {
      console.error("Error making API request:", error);
    }
  };
  const downloadReport = async () => {
    const token = await getTokenValue();
    if (token) await getReport(token);
    else console.error("Token is undefined");
  };
  const playAnimations = () => {
    firewroks();
    Swal.fire({
      icon: "success",
      width: 600,
      title: "Congratulations! You have completed the test",
      showCancelButton: true,
      confirmButtonText: "Start learning",
      cancelButtonText: "Download report",
      showClass: {
        popup: `
          animate__animated
          animate__rollIn
          animate__slow
        `,
      },
      hideClass: {
        popup: `
          animate__animated
          animate__backOutDown
          animate__slow
        `,
      },
      customClass: {
        confirmButton: "swal2-confirm-custom",
        cancelButton: "swal2-cancel-custom",
      },
      // closeOnCancel:false,
      allowOutsideClick: false,
      allowEscapeKey: false,
    }).then((result) => {
      if (result.isConfirmed) {
        router.push("/myLearning");
      } else {
        downloadReport();
        router.push("/myLearning");
      }
      onPause();
    });
  };

  const firewroks = () => {
    conductor?.run({ speed: 25, duration: 8000 });
  };

  const onInit = ({ conductor }: { conductor: TConductorInstance }) => {
    setConductor(conductor);
  };

  const onPause = () => {
    conductor?.pause();
  };

  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.scrollY;
      setIsScrolled(scrollTop > 0);
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <>
      <Navbar isScrolled={isScrolled} />
      <Container component="main" maxWidth="xs">
        <Typography
          component="h1"
          variant="h5"
          className="py-4 text-center bold-text"
        >
          Finish Screening Test
        </Typography>
        <form onSubmit={handleSubmit}>
          {/* <TextField
            fullWidth
            margin="normal"
            label="Record"
            variant="outlined"
            id="record"
            name="record"
            value={formData.record}
            onChange={handleInputChange}
            InputProps={{ style: { color: "black", backgroundColor: "beige" } }}
          /> */}
          <Button
            type="submit"
            variant="contained"
            style={{
              backgroundColor: "#3E4772",
              color: "#CDEBC5",
            }}
            fullWidth
          >
            Submit
          </Button>
        </form>
        <br />
        {gamesData.games_result.map((gameArray, index) => (
          <div>
            <div>game - {index + 1}</div>
            {gameArray.map((game) => (
              <div>{game} , </div>
            ))}
            <br />
            <br />
          </div>
        ))}
        <Pride onInit={onInit} />
      </Container>
    </>
  );
};

export default FormInterface;

const RecordTable = ({ record }: { record: any }) => {
  const recordObj = JSON.parse(record);

  return (
    <table
      style={{
        width: "100%",
        border: "1px solid black",
        borderCollapse: "collapse",
      }}
    >
      <tbody
        style={{
          border: "1px solid black",
          borderCollapse: "collapse",
        }}
      >
        {Object.entries(recordObj.record).map(([key, value]) => (
          <tr
            key={key}
            style={{
              border: "1px solid black",
              borderCollapse: "collapse",
            }}
          >
            <td
              style={{
                textAlign: "center",
              }}
            >
              {key}
            </td>
            <td
              style={{
                textAlign: "center",
              }}
            >
              {value as string}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
