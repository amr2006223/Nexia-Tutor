from flask import Flask, jsonify, request
from services.image_scraper import ImageScraper
from services.rhyme_scraper import RhymeScraper


app = Flask(__name__)

@app.route('/get_rhymes_game_data', methods=['GET'])
def get_rhymes_and_images():
    # Get a word from the person who is asking for rhymes and images
    word = request.args.get('word')

    # Check if the person forgot to provide a word; if they did, tell them it's needed
    if not word:
        return jsonify({'error': 'Word parameter is missing'}), 400

    # Use services to find rhymes and images for the provided word and rhyme words
    word_image_links = ImageScraper.get_image_links(word) 

    rhymes = RhymeScraper.fetch_rhymes(word)
    rhyme_image_links = [] 
    for rhyme in rhymes:
            image_links =  ImageScraper.get_image_links(rhyme)
            rhyme_image_links.extend(image_links)
   

    # Check if we found rhymes and images
    if rhymes is not None and image_links:
        return jsonify({'word': word, 'rhymes': rhymes,'word_image_links': word_image_links ,'rhyme_image_links': rhyme_image_links})
   
    else:
        # If something went wrong while finding rhymes or images, tell the person there was an issue
        return jsonify({'error': 'Failed to retrieve rhymes or images'}), 500

@app.route('/get_memory_game_data', methods=['GET'])
def get_memory_game():
    # Get a word from the person who is asking for a memory game
    word = request.args.get('word')

    # Check if the person forgot to provide a word; if they did, tell them it's needed
    if not word:
        return jsonify({'error': 'Word parameter is missing'}), 400

    # Use the ImageService to get a list of images for the memory game
    image_links = ImageScraper.get_image_links(word)

    # Return the list of image links for the memory game
    return jsonify({'word': word, 'image_links': image_links})

# Run the Flask app
if __name__ == '__main__':
    app.run(debug=True)

#http://127.0.0.1:5000/get_rhymes_game_data?word=cat
#http://127.0.0.1:5000/get_memory_game_data?word=cat
