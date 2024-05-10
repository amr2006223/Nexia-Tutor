"use server";
import { RegisterData, LoginData } from "@/types/auth";
import axios from "axios";
import { cookies } from "next/headers";
import { getUserDetailsService } from "../user/userDetails";

export const register = async (data: RegisterData) => {
  try {
    await axios.post(`${process.env.IDENTITY_API}auth/register`, data, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return true;
  } catch (error) {
    console.error(error);
    return false;
  }
};

export const login = async (data: LoginData) => {
  try {
    const response = await axios.post(
      `${process.env.IDENTITY_API}auth/login`,
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
  return checkTokenExpiation();
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

export const checkTokenExpiation = async () => {
  const token = await getTokenValue();
  try {
    if (token) {
      const response = await fetch(`${process.env.IDENTITY_API}auth/validate`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ token: token }),
      });
      const responseData = await response.json();
      if (responseData.status === "valid") {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  } catch (error) {
    console.error(error);
    return false;
  }
};
