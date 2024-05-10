import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { checkTokenExpiation } from "./services/auth/auth";

export async function middleware(request: NextRequest) {
  const tokenIsvalid = await checkTokenExpiation();
  console.log("tokenIsvalid from middleware: ", tokenIsvalid);

  if (tokenIsvalid) {
    return NextResponse.next();
  } else {
    return NextResponse.redirect(new URL("/", request.url));
  }
}

export const config = {
  matcher: ["/screening/:path*", "/tutoring/:path*"],
};
