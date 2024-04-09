/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false,

  env: {
    NEXIA_API: "http://localhost:8080/nexia-tutor/",
    REPORT_API: "http://localhost:8080/report-generation/",
    KEYWORD_EXTRACTION_API: "http://localhost:8080/extracting/",
    WEB_SCRAPING_API: "http://localhost:8080/scraping/",
    SCREENING_API: "http://localhost:8080/screening/",
  },
};

module.exports = nextConfig;
