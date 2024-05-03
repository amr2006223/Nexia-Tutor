import json
import uuid
# import requests
import asyncio
from flask_cors import CORS, cross_origin
from flask import Flask, jsonify, request
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
    # Initialize our objects\
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
    # data_filterd_for_user = dataManipulator.filter_data_by_age(3,30)
    allAverages = [
                    {
                        "average":dataManipulator.get_average(1,4,cleanedDekstopData),
                        "name" : "Alphabetic Awareness"
                    },
                    {
                        "average":dataManipulator.get_average(5, 9, cleanedDekstopData),
                        "name" : "Phonological Awareness"
                    },
                    {
                        "average":dataManipulator.get_average(18,21,cleanedDekstopData),
                        "name" : "Visual Working Memory"
                    },
                ]
    return jsonify(allAverages)
    # dataManipulator.get_average(10, 13, desktopData)
    # dataManipulator.get_average(14,17,desktopData)
    # dataManipulator.get_average(22, 23, desktopData)
    # dataManipulator.get_average(24,24,desktopData)
    # dataManipulator.get_average(25, 25, desktopData)
    # dataManipulator.get_average(26,26,desktopData)
    # dataManipulator.get_average(27, 28, desktopData)
    # dataManipulator.get_average(29,29,desktopData)
    # dataManipulator.get_average(30, 30, desktopData)
    # dataManipulator.get_average(31, 32, desktopData)
    
@app.route("/screening/predict",methods=["POST"])
@cross_origin()
def prediction():
    try:
        requestData, dataframe = recordHandler.prepareRecordForPrediction(request.get_data())
        prediction = randomForestModelTrainer.predictData(dataframe)
        result = recordHandler.generateResult(prediction,requestData)
        return requestHandler.generateReportForUser(result,prediction)
        # return jsonify({'prediction':str(prediction[0])}) 
    except Exception as e:
        errorMessage = f"Error processing request: {str(e)}"
        return jsonify({"error": errorMessage}), 500

if(__name__ == "__main__"):
    app.run(debug=True, port=5002)