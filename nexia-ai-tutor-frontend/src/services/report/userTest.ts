import axios from "axios";
import { getTokenValue } from "../auth/auth";
interface DataObject{
  token:string
}
export const checkIsUserTested = async () => {
  const token = await getTokenValue();

  // let data = `{"token":"${token}"}`; // "token":"

    
    const myDataObject: DataObject = {
      token: "yourTokenStringHere",
    };
    try {
      const response = await axios.post(
      // `${process.env.REPORT_API}isTested`,
      "http://localhost:8080/report-generation/test",
      JSON.stringify(myDataObject)
    );
    console.log(response);
    //   return response.data;
  } catch (error) {
    console.error("request error", error);
  }
  // try {

  // } catch (error) {
  //   console.error("request error", error);
  // }
// }
// }
}
