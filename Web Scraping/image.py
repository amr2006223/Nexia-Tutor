from flask import Flask, jsonify
import requests
from bs4 import BeautifulSoup

app = Flask(__name__)

def scrape_images(search_term):
    url = f'https://unsplash.com/s/photos/{search_term}'
    response = requests.get(url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.text, 'html.parser')
        image_elements = soup.find_all('img', class_='oCCRx')

        image_urls = [img['src'] for img in image_elements]

        return image_urls
    else:
        return []

@app.route('/api/images/<search_term>')
def get_images(search_term):
    image_urls = scrape_images(search_term)
    return jsonify({"images": image_urls})

if __name__ == '__main__':
    app.run(debug=True)
