"use client";
import ProgressBarComponent from "@/shared/components/progress/progressBar";
import { getGamesForLesson } from "@/services/games/getGamesForLesson";
import { LessonGamesModel } from "@/types/lesson";
import { Grid, Paper } from "@mui/material";
import Link from "next/link";
import React, { useEffect, useState } from "react";
import Card from "@mui/material/Card";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import { useRouter } from "next/navigation";

const Page = ({ params: { id } }: { params: { id: string } }) => {
  const router = useRouter();
  const [games, setGames] = useState<LessonGamesModel>(); // [games, setGames

  const getGames = async () => {
    const response = await getGamesForLesson(id, 1);
    setGames(response);
  };

  useEffect(() => {
    getGames();
  }, []);

  return (
    <div>
      <div>
        <Button
          onClick={() => router.back()}
          className="font-bold text-base"
          variant="contained"
          style={{
            backgroundColor: "#3E4772",
            color: "#CDEBC5",
            position: "absolute",
            top: "10px",
            left: "10px",
          }}
        >
          Back
        </Button>
      </div>
      <div className="container mx-auto">
        <br />
        <div
          className="my-2 text-center font-bold text-2xl"
          style={{ color: "#3E4772" }}
        >
          Lesson Name: {id}
        </div>

        {games ? (
          <Grid container spacing={3} className="p-6" component="div">
            {games.keywords.map((keyword) => (
              <Grid item key={keyword.keyword_name} xs={12}>
                <Paper className="p-4 bg-green-100 rounded-lg">
                  <h1
                    className="text-xl font-bold"
                    style={{ color: "#3E4772" }}
                  >
                    Keyword: {keyword.keyword_name}
                  </h1>
                  <h2 className="text-lg font-medium text-gray-700">
                    Learn this keyword with these games:
                  </h2>
                  <br />
                  <Grid container spacing={6} component="ul">
                    {games.games.map((game) => (
                      <Grid item key={game.game_name} xs={6} md={6} lg={6}>
                        <Card>
                          <CardContent>
                            <Typography variant="h6" sx={{ mb: 2 }}>
                              {game.game_name} Game
                            </Typography>
                          </CardContent>
                          <CardActions className="card-action-dense">
                            <Link
                              href={{
                                pathname: `/games/${game.game_name}`,
                                query: {
                                  keyword: keyword.keyword_name,
                                  imgLink: keyword.images[0].image_url,
                                },
                              }}
                            >
                              <Button
                                className="font-bold text-lg"
                                variant="contained"
                                style={{
                                  backgroundColor: "#3E4772",
                                  color: "#CDEBC5",
                                }}
                              >
                                Play
                              </Button>
                            </Link>
                          </CardActions>
                        </Card>
                      </Grid>
                    ))}
                  </Grid>
                  <hr className="my-4 border-gray-300" />
                </Paper>
              </Grid>
            ))}
          </Grid>
        ) : (
          <ProgressBarComponent />
        )}
      </div>
    </div>
  );
};

export default Page;
