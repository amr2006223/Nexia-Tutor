import axios from "axios";
import { getTokenValue } from "../auth/auth";

export const checkIsUserTested = async () => {
  const token = await getTokenValue();
  let data = {
    token: token,
  };
  // let data = `{"token":"${token}"}`; // "token":"

  try {
    // const response = await axios.post(
    //   // `${process.env.REPORT_API}isTested`,
    //   "http://localhost:8080/report-generation/isTested",
    //   JSON.stringify(data),
    //   {
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //   }
    // );
    //   return response.data;
    await fetch("http://localhost:8080/report-generation/isTested", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
  } catch (error) {
    console.error("request error", error);
  }
  // try {
  //   const response = await axios.post(
  //     // `${process.env.REPORT_API}isTested`,
  //     "http://localhost:8080/report-generation/test"
  //   );
  //   //   return response.data;
  // } catch (error) {
  //   console.error("request error", error);
  // }
};
