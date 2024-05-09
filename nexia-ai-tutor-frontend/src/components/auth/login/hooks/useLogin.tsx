"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { login } from "@/services/auth/auth";
import Swal from "sweetalert2";
import { LoginData } from "@/types/auth";

const useLogin = () => {
  const router = useRouter();

  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;

    if (name === "username") {
      setUsername(value);
    } else if (name === "password") {
      setPassword(value);
    }
  };

  const handleSubmit = async () => {
    const formData: LoginData = {
      username,
      password,
    };
    
    const response = await login(formData);
    if (response) {
      router.push("/");
    } else {
      Swal.fire({
        title: "Login Failed",
        text: "Invalid Credentials",
        icon: "error",
      });
    }
  };

  const handleSpeechRecognition = (
    fieldToUpdate: string,
    textValue: string
  ) => {
    // make textValue small case and remove spaces and dots
    let value = textValue.toLowerCase().replace(/\s/g, "").replace(/\.$/, "");
    if (fieldToUpdate === "username") {
      setUsername(value);
    } else if (fieldToUpdate === "password") {
      setPassword(value);
    }
  };

  return {
    username,
    password,
    handleInputChange,
    handleSubmit,
    handleSpeechRecognition,
  };
};

export default useLogin;
