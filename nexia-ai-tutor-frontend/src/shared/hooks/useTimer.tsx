import { useState, useEffect, useRef } from "react";

// Custom hook for timer functionality
const useTimer = (initialTimeSeconds: number, onTimeEnd: () => void) => {
  const [timeLeft, setTimeLeft] = useState(initialTimeSeconds);
  const timerRef = useRef<NodeJS.Timeout>();

  useEffect(() => {
    timerRef.current = setInterval(() => {
      setTimeLeft((prevTimeLeft) => {
        if (prevTimeLeft <= 0) {
          clearInterval(timerRef.current!);
          onTimeEnd();
          return 0;
        }
        return prevTimeLeft - 1;
      });
    }, 1000);

    return () => {
      clearInterval(timerRef.current!);
    };
  }, [initialTimeSeconds, onTimeEnd]);

  // Return the timeLeft and resetTimer function
  return { timeLeft };
};

export default useTimer;
