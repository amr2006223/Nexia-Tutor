import Navbar from "@/components/home/Navbar";
import Hero from "@/components/home/Hero";
import Map from "@/components/home/map";
import Footer from "@/components/home/Footer";
import { checkLoginAndGetUserName } from "@/services/auth/auth";

const HomePage = async () => {
  const { isLoggedIn, userName } = await checkLoginAndGetUserName();

  return (
    <div>
      <Navbar isLoggedIn={isLoggedIn} userName={userName} />
      <Hero />
      <Map />
      <Footer />
    </div>
  );
};

export default HomePage;
