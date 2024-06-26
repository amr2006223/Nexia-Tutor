import re
import os
import nltk
import string
import numpy as np
import networkx as nx
from PyPDF2 import PdfReader
from flask import jsonify
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
from nltk.stem import SnowballStemmer
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer

# Download NLTK stopwords
nltk.download('stopwords')
# Download the 'punkt' tokenizer data
nltk.download('punkt')

stop_words = set(stopwords.words('english'))

def get_top_scored_words(tfidf_matrix, tfidf_vectorizer, top_n=5):
    feature_names = tfidf_vectorizer.get_feature_names_out()
    scores = np.asarray(tfidf_matrix.mean(axis=0)).flatten()
    top_indices = scores.argsort()[::-1][:top_n]
    top_words = [feature_names[idx] for idx in top_indices]
    return top_words

# Step 1: Extract Text from PDF
def extract_text_from_pdf(pdf_path):
    text = ""
    with open(pdf_path, "rb") as file:
        pdf_reader = PdfReader(file)
        for page in pdf_reader.pages:
            text += page.extract_text()
    return text

# Step 2: Preprocess Text
def preprocess_text(text):
    # Remove newline characters
    text = text.replace("\n", " ")
    # Remove Unicode characters
    text = text.encode('ascii', 'ignore').decode()
    # Remove bullet points or numbering system
    text = re.sub(r'^[•●∙‣⁃◦⦿⦾◯◉⚫⚬⚪◼◻⬤⚆⚇✴️⦁⚈]+|\d+\.\s+', '', text)
    # Remove stop words
    punctuation = set(string.punctuation)
    text = ' '.join([word.lower() for word in text.split() if word.lower() not in stop_words and word.lower() not in punctuation])
    # Remove code-related content using regex
    text = re.sub(r'\b\S+\.py\S*\b', '', text)
    text = re.sub(r'\b\S+\.html?\S*\b', '', text)
    # Apply stemming using Porter Stemmer
    # stemmer = SnowballStemmer("english")
    # text = ' '.join([stemmer.stem(word) for word in text.split()])
    # Add more regex patterns as needed to remove other code-related content
    return text

# Step 3: Tokenization
def tokenize_words(text):
    return word_tokenize(text)

# Step 4: TF-IDF Vectorization
def apply_tfidf(sentences):
    tfidf_vectorizer = TfidfVectorizer()
    tfidf_matrix = tfidf_vectorizer.fit_transform(sentences)
    return tfidf_matrix, tfidf_vectorizer

def extract_keywords(tfidf_matrix, tfidf_vectorizer, top_n=5):
    feature_names = tfidf_vectorizer.get_feature_names_out()
    keywords = []
    for sentence_vector in tfidf_matrix:
        # Convert the sentence vector to a 1D array, then sort and take top indices
        top_indices = np.asarray(sentence_vector.toarray()).flatten().argsort()[::-1][:top_n]
        # Get the corresponding feature names (words)
        top_features = [feature_names[idx] for idx in top_indices]
        keywords.append(top_features)
    return keywords

# Step 6: Text Summarization using TextRank
def summarize_text(sentences, num_sentences=3):
    similarity_matrix = build_similarity_matrix(sentences)
    sentence_scores = nx.pagerank(nx.from_numpy_array(similarity_matrix))
    ranked_sentences = sorted(((sentence_scores[i],s) for i,s in enumerate(sentences)), reverse=True)
    summary = ' '.join([sentence for score, sentence in ranked_sentences[:num_sentences]])
    return summary

def build_similarity_matrix(sentences):
    tfidf_matrix, _ = apply_tfidf(sentences)
    similarity_matrix = cosine_similarity(tfidf_matrix, tfidf_matrix)
    return similarity_matrix

def extractWordsFromPDF(file, kw_model):
    # Now you can proceed with your existing code
    createDirectoryIfNotExits()
    if file and file.filename.endswith('.pdf'):
        filename = 'temp.pdf'  # Specify the desired filename
        filePath = os.path.join("uploads", filename)
        file.save(filePath)
        # Extract and process text from the PDF
        cleaned_text = preprocess_text(extract_text_from_pdf(filePath))
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
    
def createDirectoryIfNotExits():
    if not os.path.exists("uploads"):
        # Create the directory
        os.makedirs("uploads")