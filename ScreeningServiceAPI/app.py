import json
import uuid
from flask_cors import CORS, cross_origin
from flask import Flask, jsonify, request
import numpy as np
from classification.model_evaluator import ModelEvaluator
from classification.data_manipulation import DataManipulator
from classification.data_preprocessor import DataPreProcessing
from classification.model_trainer import RandomForestModelTrainer
from classification.record_handler import RecordHandler
from classification.request_handler import RequestHandler
from datetime import datetime
import pandas as pd

app = Flask(__name__)
CORS(
    app,
    resources={r"/*": {"origins": "*"}},
    headers={
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers": "Content-Type",
    },
)

with app.app_context():
    dataset = "assets/data/Dyt-desktop.csv"
    # Initialize our objects
    recordHandler = RecordHandler()
    requestHandler = RequestHandler()
    randomForestModelTrainer = RandomForestModelTrainer()
    dataManipulator = DataManipulator()
    #Preprocess Data
    cleanedDekstopData = randomForestModelTrainer.prepareDataForTraining(dataset,dataManipulator)
    # Train Data
    randomForestModelTrainer.trainData()   
@app.route("/screening/average")
def getAverage():
    allAverages = [
        {
            "average": dataManipulator.get_average(1, 4, cleanedDekstopData),
            "name": "Alphabetic Awareness"
        },
        {
            "average": dataManipulator.get_average(5, 9, cleanedDekstopData),
            "name": "Phonological Awareness"
        },
        {
            "average": dataManipulator.get_average(18, 21, cleanedDekstopData),
            "name": "Visual Working Memory"
        },
    ]
    return jsonify(allAverages)

@app.route("/screening/predict", methods=["POST"])
@cross_origin()
def prediction():
    try:
        requestData, dataframe = recordHandler.prepareRecordForPrediction(request.get_data())
        prediction = randomForestModelTrainer.predictData(dataframe)
        result = recordHandler.generateResult(prediction, requestData)
        return requestHandler.generateReportForUser(result, prediction)
    except Exception as e:
        errorMessage = f"Error processing request: {str(e)}"
        return jsonify({"error": errorMessage}), 500

@app.route("/screening/accuracy", methods=["GET"])
def getAccuracy():
    try:
        y_test = randomForestModelTrainer.y_test
        y_pred = randomForestModelTrainer.y_pred
        evaluator = ModelEvaluator(y_test, y_pred)
        accuracy = evaluator.getAccuracy()
        return jsonify({"accuracy": accuracy})
    except Exception as e:
        errorMessage = f"Error evaluating accuracy: {str(e)}"
        return jsonify({"error": errorMessage}), 500
    
@app.route("/screening/cross-validation", methods=["GET"])
def performCrossValidation():
    try:
        cv_scores = randomForestModelTrainer.crossValidate()
        mean_cv_score = np.mean(cv_scores)
        return jsonify({
            "cross_validation_scores": cv_scores.tolist(),
            "mean_cross_validation_score": mean_cv_score
        })
    except Exception as e:
        errorMessage = f"Error performing cross-validation: {str(e)}"
        return jsonify({"error": errorMessage}), 500

if __name__ == "__main__":
    app.run(debug=True, port=5002)
