"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { login } from "@/services/auth/auth";
import Swal from "sweetalert2";
import { LoginData } from "@/types/auth";

const useLogin = () => {
  const router = useRouter();

  const [formData, setFormData] = useState<LoginData>({
    username: "",
    password: "",
  });

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;

    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleSubmit = async () => {
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

  return { formData, handleInputChange, handleSubmit };
};

export default useLogin;
