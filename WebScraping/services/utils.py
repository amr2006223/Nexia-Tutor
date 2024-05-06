from flask import jsonify
from services.image_scraper import ImageScraper
from services.text_to_speech import TextToSpeech
from services.rhyme_scraper import RhymeScraper
from services.word_generator import WordGenerator
class Utils:
    imageScraper = ImageScraper()
    textToSpeech = TextToSpeech()
    rhymeScraper = RhymeScraper()
    wordGenerator = WordGenerator()
    
    def getUserWordImage(self,queue,word):
        # Check if the person forgot to provide a word; if they did, tell them it's needed
        if not word:
            return jsonify({'error': 'Word parameter is missing'}), 400

        # Use services to find rhymes and images for the provided word and rhyme words
        word_image_links = None
        while not word_image_links:
            word_image_links = self.imageScraper.get_image_links(word) 
        # Get the word and its image and sound
        word_sound = self.textToSpeech.ElevenLabsAudio(word)
        
        keyWord = {'text': word, 'image': word_image_links[0]['image_link'], 'sound': word_sound}
        
        queue['keyWord'] = keyWord

    def getRhymesForWord(self,queue,word):
        rhymes = self.rhymeScraper.fetch_rhymes(word)
        print(rhymes)
        # Get words that rhyme with the word
        for i in range(len(rhymes)):
            image = None
            while not image:
                image = self.imageScraper.get_image_links(rhymes[i])
            word_sound = self.textToSpeech.get_audio(rhymes[i])
            rhymes[i] = {'text': rhymes[i], 'image': image[0]['image_link'], 'rhyme': True, 'sound': word_sound}
        queue['rhymes'] = rhymes

    def getNotRhymesForWord(self,queue):
        # Get words that do not rhyme with the word
        not_rhymes = self.wordGenerator.generate_words(3)
        
        for i in range(len(not_rhymes)):
            image = None
            while not image:
                image = self.imageScraper.get_image_links(not_rhymes[i])
            word_sound = self.textToSpeech.get_audio(not_rhymes[i])
            not_rhymes[i] = {'text': not_rhymes[i], 'image': image[0]['image_link'], 'rhyme': False, 'sound': word_sound}
        queue["not_rhymes"] = not_rhymes
        
    def generate_other_words_and_images(self):
        #"""Generate other words and retrieve their corresponding images."""
        other_words = self.wordGenerator.generate_words(3)
        result = []

        for word in other_words:
            while True:
                image = self.get_images_for_word(word)
                if image:
                    # Append the word and its image link to the result list
                    result.append({
                        'query': word,
                        'image_link': image[0]['image_link']
                    })
                    break
        return result
    
    def get_images_for_word(self,word):
        #"""Get image links for the provided word."""
        try:
           while True:
                image_links = self.imageScraper.get_image_links(word)
                if image_links:
                    return image_links
        except Exception as e:
            print(f'Error getting images for word "{word}": {e}')
            return None
