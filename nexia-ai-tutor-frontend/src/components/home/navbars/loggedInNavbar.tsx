"use client";
import { logoutService } from "@/services/auth/auth";
import { clearFilesFromLocalStorage } from "@/services/files/fileUpload";
import { useRouter } from "next/navigation";

type LoggedInNavbarProps = {
  userName: string;
};

const LoggedInNavbar = (props: LoggedInNavbarProps) => {
  const router = useRouter();
  const handleLogout = async () => {
    logoutService();
    await clearFilesFromLocalStorage();
    router.refresh();
    router.push("/auth/login");
  };

  return (
    <div className="flex items-center gap-4">
      <div className="flex items-center gap-2">
        <div className="text-[#CDEBC5]">Hi, {props.userName}</div>
        <div className="text-red-500">|</div>
        <div
          onClick={handleLogout}
          className="cursor-pointer text-[#CDEBC5] font-medium bg-[#3E4772] px-4 py-2 rounded-md hover:bg-[#2E3452] transition duration-300 ease-in-out"
        >
          Logout
        </div>
      </div>
    </div>
  );
};

export default LoggedInNavbar;
