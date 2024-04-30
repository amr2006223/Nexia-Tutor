"use server";
import { RegisterData, LoginData } from "@/types/auth";
import axios from "axios";
import { cookies } from "next/headers";
import { getUserDetailsService } from "../user/userDetails";

export const register = async (data: RegisterData) => {
  try {
    await axios.post(
      `${process.env.NEXIA_API}api/auth/register`,
      data,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    return true;
  } catch (error) {
    console.error(error);
    return false;
  }
};

export const login = async (data: LoginData) => {
  try {
    const response = await axios.post(
      `${process.env.NEXIA_API}api/auth/login`,
      data
    );
    // Save token to local storage
    // localStorage.setItem("token", response.data.token);
    if (response.status == 200) {
      cookies().set("token", response.data.token);
      // console.log("signed id");
      return true;
    }
    return false;
  } catch (error) {
    console.error(error);
    return false;
  }
};

export const checkLoggedInService = async (): Promise<boolean> => {
  const token = cookies().get("token");
  // console.log(token);
  if (token) {
    return true;
  } else {
    return false;
  }
};

export const logoutService = async () => {
  cookies().delete("token");
};
export const getTokenValue = async () => {
  return cookies().get("token")?.value;
};

export const checkLoginAndGetUserName = async () => {
  const isLoggedIn = await checkLoggedInService();
  if (isLoggedIn) {
    const user = await getUserDetailsService();
    return { isLoggedIn: true, userName: user.username };
  } else {
    return { isLoggedIn: false, userName: "" };
  }
};
