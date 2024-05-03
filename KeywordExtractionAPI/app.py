from keybert import KeyBERT
from flask import Flask, request, jsonify
import utils
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

with app.app_context():
    kw_model = KeyBERT()

@app.route('/extracting/upload_pdf', methods=['POST'])
def upload_pdf():
    if 'file' not in request.files:
        return jsonify({'error': 'No file part in the request'}), 400

    file = request.files['file']

    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    return utils.extractWordsFromPDF(file,kw_model)

if __name__ == "__main__":
    app.run(debug=True,port=5000)
