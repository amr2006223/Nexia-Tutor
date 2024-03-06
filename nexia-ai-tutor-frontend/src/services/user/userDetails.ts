import axios from "axios";
import { getTokenValue } from "../auth/auth";

export const getUserDetailsService = async () => {
    try{
       const token = await getTokenValue();
        const response = await axios.get(
            `${process.env.NEXIA_API}api/user/get/${token}`
        );
        console.log(response.data);
        return response.data;
    }catch(e){
        console.log(e)
    }
}