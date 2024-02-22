from flask import Flask, jsonify, request
from services.image_scraper import ImageScraper
from services.rhyme_scraper import RhymeScraper
from services.word_generator import WordGenerator
from services.text_to_speech import TextToSpeech


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
    
    # 0. get the word and its image and sound
    word_sound = TextToSpeech.get_audio(word)
    keyWord = {'text': word, 'image': word_image_links[0]['link'], 'sound': word_sound}
    
    # 1. words that rhyme with the word
    for i in range(len(rhymes)):
        word_sound = TextToSpeech.get_audio(rhymes[i])
        rhymes[i] = {'text': rhymes[i], 'image': rhyme_image_links[i]['link'], 'rhyme': True, 'sound': word_sound}
        
    # 2. get words that doesnot rhyme with the word
    not_rhymes = WordGenerator.generate_words(3)
    
    for i in range(len(not_rhymes)):
        word_sound = TextToSpeech.get_audio(not_rhymes[i])
        not_rhymes[i] = {'text': not_rhymes[i], 'image': ImageScraper.get_image_links(not_rhymes[i])[0]['link'], 'rhyme': False, 'sound': word_sound}
    
    # all_words = rhymes + not_rhymes
    # rhymes.extend(not_rhymes)
    # random.shuffle(rhymes)
    # Check if we found rhymes and images
    if rhymes is not None and image_links:
        response =  jsonify({'keyWord': keyWord, 'rhymes': rhymes, 'not_rhymes': not_rhymes})
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response
   
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



@app.route('/test', methods=['GET'])
def test():
    return jsonify({'test': 'test'})



# Run the Flask app
if __name__ == '__main__':
    app.run(debug=True)

#http://127.0.0.1:5000/get_rhymes_game_data?word=cat
#http://127.0.0.1:5000/get_memory_game_data?word=cat
