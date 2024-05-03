from keybert import KeyBERT
from flask import Flask, request, jsonify
import utils
from flask_cors import CORS
import os

app = Flask(__name__)
CORS(app)

@app.route('/extracting/upload_pdf', methods=['POST'])
def upload_pdf():
    if 'file' not in request.files:
        return jsonify({'error': 'No file part in the request'}), 400

    file = request.files['file']
        
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    if not os.path.exists("uploads"):
        # Create the directory
        os.makedirs("uploads")

    # Now you can proceed with your existing code
    if file and file.filename.endswith('.pdf'):
        filename = 'temp.pdf'  # Specify the desired filename
        filePath = os.path.join("uploads", filename)
        file.save(filePath)
        # Extract and process text from the PDF
        cleaned_text = utils.preprocess_text(utils.extract_text_from_pdf(filePath))
        # Extract keywords
        keywords = kw_model.extract_keywords(cleaned_text, keyphrase_ngram_range=(1, 1))
        # Remove the PDF file after processing
        os.remove(filePath)
        
        for i in range(len(keywords)):
            keywords[i] = keywords[i][0]
        print(keywords)
        return jsonify({'keywords': keywords}), 200
    else:
        return jsonify({'error': 'Invalid file type, only PDF files are allowed'}), 400

with app.app_context():
    kw_model = KeyBERT()
    
if __name__ == "__main__":
    app.run(debug=True,port=5000)
