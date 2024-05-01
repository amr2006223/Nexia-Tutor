import LoginSectionComponent from "@/components/auth/login/loginSection";
import SignupSectionComponent from "@/components/auth/login/signupSection";

export default function Login() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen xl:py-2 ">
      <main className="flex flex-col items-center justify-center w-full flex-1 xl:px-20 text-center">
        <div className="bg-white rounded-2xl shadow-2xl md:flex xl:w-2/3 max-w-4xl">
          <LoginSectionComponent />
          <SignupSectionComponent />
        </div>
      </main>
    </div>
  );
}
