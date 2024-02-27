from keybert import KeyBERT
from flask import Flask, request, jsonify
import utils
from flask_cors import CORS
import os
app = Flask(__name__)
CORS(app)
# Download NLTK stopwords

@app.route("/bert")
def bert():
    cleaned_text = utils.preprocess_text(utils.extract_text_from_pdf("Sentences.pdf"))
    keywords = kw_model.extract_keywords(utils.extract_text_from_pdf("Sentences.pdf"), keyphrase_ngram_range=(1, 1),highlight=True)
    return keywords

@app.route("/bert2")
def bert2():
    cleaned_text = utils.preprocess_text(utils.extract_text_from_pdf("Sentences.pdf"))
    keywords = kw_model.extract_keywords(cleaned_text, keyphrase_ngram_range=(1, 1),highlight=True)
    return keywords 

@app.route("/extract-pdf-info")
def extract_pdf_info():
    cleaned_text = utils.preprocess_text(utils.extract_text_from_pdf("Sentences.pdf"))
    sentences = utils.tokenize_sentences(cleaned_text)
    tfidf_matrix, tfidf_vectorizer = utils.apply_tfidf(sentences)
    # keywords = utils.extract_keywords(tfidf_matrix, tfidf_vectorizer)
    # summary = utils.summarize_text(sentences)
    top_scored_words = utils.get_top_scored_words(tfidf_matrix, tfidf_vectorizer)
    return {"top words":top_scored_words}
  
@app.route('/upload_pdf', methods=['POST'])
def upload_pdf():
    if 'file' not in request.files:
        return jsonify({'error': 'No file part in the request'}), 400

    file = request.files['file']
        
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    if file and file.filename.endswith('.pdf'):
        filename = 'temp.pdf'  # Specify the desired filename
        filePath = os.path.join('uploads', filename)
        file.save(filePath)
        cleaned_text = utils.preprocess_text(utils.extract_text_from_pdf(filePath))
        keywords = kw_model.extract_keywords(cleaned_text, keyphrase_ngram_range=(1, 1),highlight=True)
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
    app.run(debug=True)
