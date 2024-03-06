"use server";
import { RegisterData, LoginData } from "@/types/auth";
import axios from "axios";
import { cookies } from "next/headers";

export const register = async (data: RegisterData) => {
  try {
    const response = await axios.post(
      `${process.env.NEXIA_API}api/auth/register`,
      data
    );
    // Save token to local storage
    // localStorage.setItem("token", response.data.token);
    cookies().set("token", response.data.token);
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
    cookies().set("token", response.data.token);
    return true;
  } catch (error) {
    console.error(error);
    return false;
  }
};

export const checkLoggedInService = async (): Promise<boolean> => {
  const token = cookies().get("token");
  console.log(token);
  if (token) {
    return true;
  } else {
    return false;
  }
};

export const logoutService = async () => {
  cookies().delete("token");
};
