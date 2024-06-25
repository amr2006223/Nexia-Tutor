type StepsCardProps = {
  title: string;
  steps: string[];
};

const StepsCard = (props: StepsCardProps) => {
  return (
    <div className="max-w-sm p-4 m-1 rounded-2xl overflow-hidden shadow-lg bg-light text-blackish">
      <div className="px-6">
        <div className="font-bold text-2xl mb-2 text-primary text-center">
          {props.title}
        </div>
      </div>
      <div className="px-6 py-2">
        <ul>
          {props.steps.map((step, index) => (
            <li key={index} className="mb-1">
              <div className="flex items-center">
                <span className="text-xl font-bold mr-2">•</span>
                <span className="text-lg">{step}</span>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default StepsCard;
