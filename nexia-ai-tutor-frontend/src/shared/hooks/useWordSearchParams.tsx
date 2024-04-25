import { useSearchParams } from "next/navigation";

const useWordSearchParams = () => {
  const searchParams = useSearchParams();
  const keywordValue = searchParams.get("word");
  const keywordParams = keywordValue ? keywordValue.toUpperCase() : "";

  return { keywordParams };
};

export default useWordSearchParams;
