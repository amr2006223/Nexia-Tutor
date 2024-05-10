"use client";
import { useState, SyntheticEvent, useEffect } from "react";
import { GameStatsData } from "@/types/user-analysis/gameData";
import { Grid } from "@mui/material";

// MUI imports
import Box from "@mui/material/Box";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import TabContext from "@mui/lab/TabContext";
import { styled } from "@mui/material/styles";
import MuiTab, { TabProps } from "@mui/material/Tab";
import ExtensionIcon from "@mui/icons-material/Extension";
import StatsTabsData from "./tabsData";
type Props = {
  data: GameStatsData[];
  tab: string;
};

const Tab = styled(MuiTab)<TabProps>(({ theme }) => ({
  minHeight: 48,
  flexDirection: "row",
  "& svg": {
    marginBottom: "0 !important",
    marginRight: theme.spacing(1),
  },
}));

const GamesStatsComponent = (props: Props) => {
  const [activeTab, setActiveTab] = useState<string>(props.tab);

  const handleChange = (event: SyntheticEvent, value: string) => {
    setActiveTab(value);
  };

  useEffect(() => {
    if (props.tab && props.tab !== activeTab) {
      setActiveTab(props.tab);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.tab]);

  return (
    <div>
      <Grid item xs={12} md={8} lg={9}>
        <TabContext value={activeTab}>
          <TabList
            variant="scrollable"
            scrollButtons="auto"
            onChange={handleChange}
            sx={{
              borderBottom: (theme) => `1px solid ${theme.palette.divider}`,
            }}
          >
            {/* <Tab value="overview" label="Overview" icon={<ExtensionIcon />} />
             */}
            {props.data.map((game, index) => (
              <Tab
                key={index}
                value={game.gameData.game_name}
                label={game.gameData.game_name}
                icon={<ExtensionIcon />}
              />
            ))}
          </TabList>
          <Box sx={{ mt: 3 }}>
            {props.data.map((game, index) => (
              <TabPanel
                key={game.gameData.game_name}
                sx={{ p: 0 }}
                value={game.gameData.game_name}
              >
                <StatsTabsData key={index} data={game} />
              </TabPanel>
            ))}
          </Box>
        </TabContext>
      </Grid>
    </div>
  );
};

export default GamesStatsComponent;
