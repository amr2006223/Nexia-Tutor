# Import necessary tools to make a website and fetch information from the internet
from flask import Flask, request, jsonify
import requests
from bs4 import BeautifulSoup

# Create a website using Flask
app = Flask(__name__)

# Define a way for the website to respond when someone asks for rhymes
@app.route('/get_rhymes', methods=['GET'])
def get_rhymes():
    # Get a word from the person who is asking for rhymes
    word = request.args.get('word')

    # Check if the person forgot to provide a word; if they did, tell them it's needed
    if not word:
        return jsonify({'error': 'Word parameter is missing'}), 400

    # Try to find rhymes for the provided word
    rhymes = fetch_rhymes(word)

    # Check if we found rhymes; if we did, tell the person what the rhymes are
    if rhymes is not None:
        return jsonify({'word': word, 'rhymes': rhymes})
    else:
        # If something went wrong while finding rhymes, tell the person there was an issue
        return jsonify({'error': 'Failed to retrieve rhymes'}), 500

# Define a function to find rhymes for a given word
def fetch_rhymes(word):
    # The website we're using to find rhymes
    base_url = "https://www.rhymezone.com"

    # Construct the URL to search for rhymes for the provided word
    search_url = f"{base_url}/r/rhyme.cgi?Word={word}&org1=syl&org2=l&org3=y&typeofrhyme=perfect"

    # Make a request to the website to get the information
    response = requests.get(search_url)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        # Parse the information we got from the website
        soup = BeautifulSoup(response.text, 'html.parser')

        # Find all the words on the website that are rhymes
        rhyme_elements = soup.find_all('a', class_='r')

        # Try to get specific rhymes, or just take the first three if those don't exist
        specific_indices = [3, 10, 15, 20, 23]
        rhymes = [rhyme.text for i, rhyme in enumerate(rhyme_elements) if i in specific_indices]

        # If no specific rhymes, take the first three
        if not rhymes:
            rhymes = [rhyme.text for i, rhyme in enumerate(rhyme_elements) if i < 5]

        # Give back the list of rhymes
        return rhymes
    else:
        # If something went wrong with the website request, don't give any rhymes
        return None

# Run the website if this script is run directly
if __name__ == '__main__':
    app.run(debug=True)


# rhymes = [rhyme.text for rhyme in rhyme_elements[2:7]]
#http://127.0.0.1:5000/get_rhymes?word=test