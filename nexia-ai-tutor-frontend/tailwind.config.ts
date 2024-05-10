import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      container: {
        center: true,
        padding: "25px",
      },
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic":
          "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
      },
      colors: {
        primary: "var(--color-primary)",
        primaryLight: "var(--color-primaryLight)",
        secondary: "var(--color-secondary)",
        background: "var(--color-background)",
        light: "var(--color-light)",
        success: "var(--color-success)",
        info: "var(--color-info)",
        warning: "var(--color-warning)",
        danger: "var(--color-danger)",
        blackish: "var(--color-blackish)",
      },
    },
  },
  plugins: [],
};
export default config;
