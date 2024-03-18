import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export async function middleware(request: NextRequest) {
  const token = request.cookies.get("token");
  if (token) {
    return NextResponse.next();
  } else {
    return NextResponse.redirect(new URL("/home", request.url));
  }
}

export const config = {
  matcher: [
    "/",
    "/FormInterface",
    "/games/:path*",
    "/lessons/:path*",
    "/myLearning",
    "/screening/:path*",
    "/TodayLesson",
  ],
};
