"use client";
import { faEnvelope, faLock } from "@fortawesome/free-solid-svg-icons";
import LogoButton from "@/shared/components/buttons/logoButton";
import LoginTextfield from "./components/loginTextfield";
import useLogin from "./hooks/useLogin";
import PasswordTextfield from "./components/passwordTextField";

const LoginSectionComponent = () => {
  const {
    username,
    password,
    handleInputChange,
    handleSubmit,
    handleSpeechRecognition,
  } = useLogin();

  return (
    <div className="xl:w-3/5 p-5 md:shrink-0">
      <div className="flex">
        <LogoButton />
      </div>

      <div className="py-10">
        <h2 className="text-3xl font-bold mb-2">Sign in to your account</h2>
        {buildHorizentalLine()}

        <div className="flex flex-col items-center">
          <LoginTextfield
            name="username"
            field={username}
            icon={faEnvelope}
            handleInputChange={handleInputChange}
            handleVoiceInput={handleSpeechRecognition}
          />

          <PasswordTextfield
            name="password"
            field={password}
            icon={faLock}
            handleInputChange={handleInputChange}
            handleVoiceInput={handleSpeechRecognition}
          />

          <button
            className="border-2 border-primary rounded-full px-12 py-2 inline-block font-bold hover:bg-primary hover:text-white"
            onClick={() => handleSubmit()}
          >
            Sign in
          </button>
        </div>
      </div>
    </div>
  );

  function buildHorizentalLine() {
    return (
      <div className="border-2 w-1/3 border-[#2A304D] inline-block mb-2"></div>
    );
  }
};

export default LoginSectionComponent;
