from flask import Flask, jsonify, request
from flask_cors import CORS
from openai import OpenAIError
from services.image_scraper import ImageScraper
from services.text_to_speech import TextToSpeech
from services.open_ai import OpenAI
from services.utils import Utils
from services.threading import Threading

app = Flask(__name__)
CORS(
    app,
    resources={r"/*": {"origins": "*"}},
    headers={
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers": "Content-Type",
    },
)
with app.app_context():
    imageScraper = ImageScraper()
    textToSpeech = TextToSpeech()
    utils = Utils()
    threads = Threading()
    openAI = OpenAI()

@app.route('/scraping/get_rhymes_game_data', methods=['GET'])
def get_rhymes_and_images():
    # Initialize an empty dictionary to hold the results.
    result = {}
    
    # Retrieve the 'word' parameter from the GET request. This is the word the user wants rhymes and images for.
    word = request.args.get('word')
    
    # Define a list of functions to be executed concurrently. Each function takes 'result' and 'word' as arguments:
    functions = [
        (utils.getUserWordImage, (result, word)),
        (utils.getRhymesForWord, (result, word)),
        (utils.getNotRhymesForWord, (result,))
    ]
    
    # Execute the functions concurrently using the 'executeThread' method from the 'threads' module.
    threads.executeThread(functions)
    
    try:
        # Convert the 'result' dictionary into a JSON response.
        response = jsonify(result)
        
        # Add a header to the response to allow cross-origin resource sharing (CORS).
        response.headers.add('Access-Control-Allow-Origin', '*')
        
        # Return the response object.
        return response
    except:
        # In case of any exception, return an error message as a JSON response with a 500 status code.
        return jsonify({'error': 'Failed to retrieve rhymes or images'}), 500
        

@app.route('/scraping/get_memory_game_data', methods=['GET'])
def get_memory_game():
    # Retrieve the 'word' parameter from the GET request.
    word = request.args.get('word')
    
    # Validate the input word. If the 'word' parameter is missing, return an error response.
    if not word:
        return jsonify({'error': 'Word parameter is missing'}), 400

    # Use a utility function to retrieve image links for the provided word.
    word_images = utils.get_images_for_word(word)
    
    # Use a utility function to generate a list of other words and their corresponding images.
    other_words = utils.generate_other_words_and_images()

    # Create a dictionary to hold the response data, including the image links for the provided word
    # and the list of other words with their images.
    response_data = {
        'keyword': word_images,
        'other_words': other_words
    }
    
    # Convert the response data dictionary to a JSON response object.
    response = jsonify(response_data)
    
    # Add a header to the response to allow cross-origin resource sharing (CORS).
    response.headers.add('Access-Control-Allow-Origin', '*')

    # Return the response object.
    return response

@app.route('/scraping/web/get_image_word', methods=['GET'])
def get_image_word():
    # Retrieve the 'word' parameter from the GET request.
    word = request.args.get('word')
    
    # Initialize a variable for the image. It will hold the first image link found.
    image = None
    
    # Use a loop to repeatedly attempt to find an image link for the provided word.
    # This will continue indefinitely until an image link is found (if any).
    while True:
        # Use the imageScraper to retrieve image links for the provided word.
        image = imageScraper.get_image_links(word)
        
        # If an image link is found (image is not empty), exit the loop.
        if image:
            break
    
    # Return a JSON response containing the first image link found.
    return jsonify({'image': image[0]['image_link']})

@app.route('/scraping/get_audio_word', methods=['GET'])
def get_audio_word():
    # Retrieve the 'word' parameter from the GET request.
    word = request.args.get('word')
    
    # Use a utility function from the text-to-speech module to get an audio representation of the provided word.
    sound = textToSpeech.get_audio(word)
    
    # Return a JSON response containing the audio data (or link) for the provided word.
    return jsonify({'sound': sound})

# @app.route('/scraping/test', methods=['GET'])
# def test():
#     word = request.args.get('word')
#     rhymes = rhymeScraper.fetch_rhymes(word)
#     return jsonify({'rhymes': rhymes})

# Define a route for generating images based on a word
@app.route('/scraping/get_image_word', methods=['GET'])
def generate_image():
    # Retrieve parameters from the request URL
    word = request.args.get('word', default='', type=str)
    model = request.args.get('model', default='dall-e-2', type=str)
    size = request.args.get('size', default='1024x1024', type=str)

    try:
        # Call the function to generate an image with retry logic
        result = openAI.generate_image_with_retry(word, model, size)

        # Extract the image URL from the result
        image_url = result.data[0].url  # Assuming there is only one image URL in the list

        # Prepare the response data as a dictionary
        response_data = {'image': image_url} 

        # Return the response data as JSON
        return jsonify(response_data)

    except OpenAIError as e:
        # Handle OpenAI errors and return an error response
        print(f"Error from OpenAI API: {e}")
        return jsonify({'error': str(e)}), 500
    except Exception as e:
        # Handle unexpected errors and return an error response
        print(f"Unexpected error: {e}")
        return jsonify({'error': 'Unexpected error occurred.'}), 500

# Run the Flask app
if __name__ == '__main__':
    app.run(debug=True,port=5001)
