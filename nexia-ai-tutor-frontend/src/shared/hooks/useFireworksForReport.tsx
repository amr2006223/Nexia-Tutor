"use client";
import { useState } from "react";
import { TConductorInstance } from "react-canvas-confetti/dist/types";
import "animate.css";
import Swal from "sweetalert2";
import { useRouter } from "next/navigation";
import { getReport } from "@/services/report/downloadReport";
const useFireworksForReport = () => {
  const router = useRouter();
  const [conductor, setConductor] = useState<TConductorInstance>();

  const onInit = ({ conductor }: { conductor: TConductorInstance }) => {
    setConductor(conductor);
  };
  const onPause = () => {
    conductor?.pause();
  };
  const firewroks = () => {
    conductor?.run({ speed: 25, duration: 8000 });
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
    }).then(async (result) => {
      if (result.isConfirmed) {
        router.push("/myLearning");
      } else {
        await getReport();
        router.push("/myLearning");
      }
      onPause();
    });
  };

  return { playAnimations, onInit };
};

export default useFireworksForReport;
