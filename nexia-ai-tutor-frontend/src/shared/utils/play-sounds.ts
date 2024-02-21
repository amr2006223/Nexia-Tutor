export async function playMp3Sound(sound: string) {
  try {
    const audio = new Audio(sound);
    await audio.play();
    await new Promise((resolve) => {
      audio.onended = resolve;
    });
  } catch (e) {
    console.error(e);
  }
}

export async function playSoundFromGoogle(sound: string) {
  try {
    const audio = new Audio("data:audio/wav;base64," + sound);
    await audio.play();
    await new Promise((resolve) => {
      audio.onended = resolve;
    });
  } catch (e) {
    console.error(e);
  }
}
