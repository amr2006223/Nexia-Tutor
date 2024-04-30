import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

type LoginTextfieldProps = {
  name: string;
  field: string;
  icon: IconProp;
  isPassword?: boolean;
  handleInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
};

const LoginTextfield = (props: LoginTextfieldProps) => {
  return (
    <div className="bg-gray-100 flex items-center w-64 p-2 mb-3">
      <FontAwesomeIcon icon={props.icon} className="text-gray-400 m-2" />

      <input
        type={props.isPassword ? "password" : "text"}
        name={props.name}
        placeholder={props.name.charAt(0).toUpperCase() + props.name.slice(1)}
        className="text-sm bg-gray-100 outline-none flex-1"
        value={props.field}
        onChange={props.handleInputChange}
      />
    </div>
  );
};

export default LoginTextfield;
