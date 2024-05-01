import Link from "next/link";

const BackButton = () => {
  return (
    <Link
      className=" bg-primary text-light font-bold text-base p-3 m-2"
      href={"/tutoring/my-learning"}
    >
      Back
    </Link>
  );
};

export default BackButton;
