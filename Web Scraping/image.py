# Import necessary modules for the Flask web framework and web scraping
from flask import Flask, request, jsonify
from bs4 import BeautifulSoup
import requests
from urllib.parse import unquote

# Create a Flask web application
app = Flask(__name__)

# Define a function to get image links based on a search query
def get_image_links(search_query):
    # Construct the URL for Google Images search with the given query
    search_url = f"https://www.google.com/search?q={search_query}&tbm=isch"

    # Set headers to pretend like a web browser
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
    }

    # Send a request to the search URL
    response = requests.get(search_url, headers=headers)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        # Parse the HTML content of the response
        soup = BeautifulSoup(response.text, 'html.parser')
        
        # Find image tags in the HTML with a specific class
        img_tags = soup.find_all('img', class_='rg_i Q4LuWd')

        # Collect image links
        image_links = []
        
        # Loop through the found image tags
        for img_tag in img_tags:
            # Get the image source from 'data-src' or 'src'
            data_src = img_tag.get('data-src')
            src = img_tag.get('src')

            # If 'data-src' exists, use it; otherwise, use 'src'
            if data_src:
                image_links.append({'query': search_query, 'link': unquote(data_src)})
            elif src and src.startswith('data:image/jpeg;'):
                image_links.append({'query': search_query, 'link': unquote(src)})

        # Return the list of image links
        return image_links
    else:
        # Print an error message if the request was not successful
        print(f"Failed to retrieve images. Status code: {response.status_code}")
        return []

# Define a route '/image_search' for the GET method
@app.route('/image_search', methods=['GET'])
def image_search():
    # Get the 'search_query' parameter from the request's query parameters
    search_query = request.args.get('search_query')

    # Check if the 'search_query' parameter is missing
    if not search_query:
        return jsonify({'error': 'Missing search_query parameter'}), 400

    # Call the get_image_links function with the search query
    image_links = get_image_links(search_query)
    
    # Return the image links as a JSON response
    return jsonify({'image_links': image_links})

# Run the Flask application if the script is executed directly
if __name__ == '__main__':
    app.run(debug=True)

    #http://127.0.0.1:5000/image_search?search_query=cat
