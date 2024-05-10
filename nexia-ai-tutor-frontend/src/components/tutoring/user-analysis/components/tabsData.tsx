import { GameStatsData } from "@/types/user-analysis/gameData";
import StatsCard from "./statsCard";
import AccessAlarmIcon from "@mui/icons-material/AccessAlarm";
type Props = {
  data: GameStatsData;
};
const StatsTabsData = (props: Props) => {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      <StatsCard
        data_name="Maximum Time Taken"
        data_value={props.data.maxTimeTaken}
        icon={AccessAlarmIcon}
      />

      <StatsCard
        data_name="Average Time Taken"
        data_value={props.data.averageTimeTaken}
        icon={AccessAlarmIcon}
      />

      <StatsCard
        data_name="Average Misses"
        data_value={props.data.averageMisses}
        icon={AccessAlarmIcon}
      />

      <StatsCard
        data_name="Minimum Misses"
        data_value={props.data.minMisses}
        icon={AccessAlarmIcon}
      />

      <StatsCard
        data_name="Minimum Time Taken"
        data_value={props.data.minTimeTaken}
        icon={AccessAlarmIcon}
      />
      <StatsCard
        data_name="Total Time Taken"
        data_value={props.data.totalTimeTaken}
        icon={AccessAlarmIcon}
      />
      <StatsCard
        data_name="Maximum Misses"
        data_value={props.data.maxMisses}
        icon={AccessAlarmIcon}
      />
      <StatsCard
        data_name="Total Misses"
        data_value={props.data.totalMisses}
        icon={AccessAlarmIcon}
      />
    </div>
  );
};

export default StatsTabsData;
