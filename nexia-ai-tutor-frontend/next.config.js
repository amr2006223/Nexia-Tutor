/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false,

  env: {
    NEXIA_API: "http://localhost:8080/",
    REPORT_API: "http://localhost:8081/",
    KEYWORD_EXTRACTION_API: "http://localhost:5000/",
    WEB_SCRAPING_API: "http://localhost:5001/",
    SCREENING_API: "http://localhost:5002/",
  },
};

module.exports = nextConfig;
