import axios from "axios";
import { getTokenValue } from "../auth/auth";

export const checkIsUserTested = async () => {
  const token = await getTokenValue();

  try {
    const response = await axios.get(
      `${process.env.REPORT_API}isTested/${token}`
    );

    return response.data.hasUserTakenTest;
  } catch (error) {
    console.error("request error", error);
  }
};
