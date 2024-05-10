import React, { useEffect, useState } from "react";
import { FiMic, FiPlay, FiMenu } from "react-icons/fi";
import { IconButton, Menu, MenuItem } from "@mui/material";
import { useRouter } from "next/navigation";
import { getTextSound } from "@/services/text-to-speech/textSound";
import SpeakerButtonComponent from "@/shared/components/buttons/speakerButtonComponent";
import { useGameState } from "@/shared/state/game";
import { GameModel } from "@/types/game";

type props = { word: string };

const menuItemStyle = { marginBlock: "2px" };

const WordComponent = ({ word }: props) => {
  const router = useRouter();
  const [wordSound, setWordSound] = useState("");
  const gameState = useGameState();
  const customActionsCell = () => {
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

    const handleMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
      setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
      setAnchorEl(null);
    };
    const handlePlay = (game_name: String) => {
      handleMenuClose();
      router.push(`games/${game_name}?word=${word}`);
    };
    return (
      <div>
        <IconButton
          aria-label="more"
          id="long-button"
          aria-controls="long-menu"
          aria-expanded={anchorEl ? "true" : undefined}
          aria-haspopup="true"
          onClick={handleMenuOpen}
        >
          <FiMenu />
        </IconButton>
        <Menu
          id="long-menu"
          MenuListProps={{
            "aria-labelledby": "long-button",
          }}
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={handleMenuClose}
        >
          {/* firs  t item in menu */}
          {gameState.games.map((game: GameModel) => (
            <MenuItem
              onClick={() => handlePlay(game.game_name)}
              sx={menuItemStyle}
            >
              <FiPlay className="text-2xl" />
              <span className="mx-2">{game.game_name}</span>
            </MenuItem>
          ))}
        </Menu>
      </div>
    );
  };

  useEffect(() => {
    const getSound = async () => {
      const sound = await getTextSound(word);
      setWordSound(sound);
    };

    getSound();
  }, []);

  return (
    <div className="flex items-center justify-between mt-4 bg-[#CDEBC5] rounded-lg p-4 drop-shadow-lg">
      <div className="flex items-center ml-2 text-xl ">
        {/* <FiMic className="m-2 h-6 w-6" />
         */}
        <div className="m-2 h-6 w-6">
          <SpeakerButtonComponent
            sound={wordSound}
            from_google={true}
            theme={"dark"}
          />
        </div>
        <strong>{word}</strong>
      </div>
      <div>{customActionsCell()}</div>
    </div>
  );
};

export default WordComponent;
