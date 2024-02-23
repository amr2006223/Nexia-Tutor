import random
class WordGenerator:
    @staticmethod
    def load_words():
        with open("WebScraping/words.txt", 'r') as file:
            words = file.read().splitlines()
        return words

    @staticmethod
    def generate_words(num):
        words = WordGenerator.load_words()
        return random.sample(words, num)
