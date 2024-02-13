from keybert import KeyBERT
from flask import Flask
import utils
app = Flask(__name__)

# Download NLTK stopwords

@app.route("/bert")
def bert():
    # keywords = kw_model.extract_keywords(utils.extract_text_from_pdf("girl.pdf"), keyphrase_ngram_range=(1, 1),highlight=True)
    return keywords

@app.route("/bert2")
def bert2():
    # keywords = kw_model.extract_keywords(cleaned_text4, keyphrase_ngram_range=(1, 1),highlight=True)
    return keywords 

@app.route("/extract-pdf-info")
def extract_pdf_info():
    sentences = utils.tokenize_sentences(cleaned_text4)
    tfidf_matrix, tfidf_vectorizer = utils.apply_tfidf(sentences)
    # keywords = utils.extract_keywords(tfidf_matrix, tfidf_vectorizer)
    # summary = utils.summarize_text(sentences)
    top_scored_words = utils.get_top_scored_words(tfidf_matrix, tfidf_vectorizer)
    return {"top words":top_scored_words}
  

with app.app_context():
    # kw_model = KeyBERT()
    cleaned_text = utils.preprocess_text(utils.extract_text_from_pdf("Sentences.pdf"))
    cleaned_text2 = utils.preprocess_text(utils.extract_text_from_pdf("Sentences2.pdf"))
    cleaned_text3 = utils.preprocess_text(utils.extract_text_from_pdf("english.pdf"))
    cleaned_text4 = utils.preprocess_text(utils.extract_text_from_pdf("girl.pdf"))
    cleaned_text5 = utils.preprocess_text(utils.extract_text_from_pdf("test.pdf"))
    cleaned_text6 = utils.preprocess_text(utils.extract_text_from_pdf("testing.pdf"))
    cleaned_text7 = utils.preprocess_text(utils.extract_text_from_pdf("testing2.pdf"))
    
if __name__ == "__main__":
    app.run(debug=True)
