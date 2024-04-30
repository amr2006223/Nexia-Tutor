import Link from "next/link";

type NavbarLinkProps = {
  href: string;
  children: string;
};

const NavbarLink = (props: NavbarLinkProps) => {
  return (
    <Link href={props.href} className="navbar__link relative">
      {props.children}
    </Link>
  );
};

export default NavbarLink;