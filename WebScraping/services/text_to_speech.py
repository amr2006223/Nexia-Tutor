import requests
import json
import base64
class TextToSpeech:
    def ElevenLabsAudio(self, text):
        CHUNK_SIZE=1024
        # Define the API endpoint and headers
        url = "https://api.elevenlabs.io/v1/text-to-speech/21m00Tcm4TlvDq8ikWAM"  # Replace with your actual voice ID

        headers = {
            "Accept": "audio/mpeg",
            "Content-Type": "application/json",
            "xi-api-key": "61f6b07a495b4c211ac8ee0c96a662a0"  # Replace with your actual API key
        }

        # Define the request payload
        data = {
            "text": text,
            "model_id": "eleven_monolingual_v1",
            "voice_settings": {
                "stability": 1,
                "similarity_boost": 0.1
            }
        }

        # Make the POST request to the ElevenLabs API
        response = requests.post(url, json=data, headers=headers)

        # Check if the request was successful
        if response.status_code == 200:
            # Return the audio content (binary data) directly
            base64_audio = base64.b64encode(response.content).decode('utf-8')
            return base64_audio
            # with open('output.mp3', 'wb') as f:
            #     for chunk in response.iter_content(chunk_size=CHUNK_SIZE):
            #         if chunk:
            #             f.write(chunk)
            # return response.content
        else:
            # Handle errors and log the error message for debugging
            print(f"Failed to get audio: {response.status_code}, {response.text}")
            return "None"
        # with open('output.mp3', 'wb') as f:
        #     for chunk in response.iter_content(chunk_size=CHUNK_SIZE):
        #         if chunk:
        #             f.write(chunk)
        
    def get_audio(self,text):
        # return self.ElevenLabsAudio(text)
        url = 'https://texttospeech.googleapis.com/v1/text:synthesize'
        dict_to_send = {
        "input": { "text": text },
        "voice": {
            "languageCode": "en-US",
            "ssmlGender": "FEMALE"
        },
        "audioConfig": {
            "audioEncoding": "MP3",
            "speakingRate": 0.85
        },}

        # Adding custom headers
        headers = {
            'Content-Type': 'application/json',
            'x-goog-api-key': 'AIzaSyDZeuCMi8U4ZJqB19mu5GnnlACi6app3b4'
        }
        res = requests.post(url, json=dict_to_send, headers=headers)
        audio = json.loads(res.text)
        return(audio.get('audioContent'))