import json
import uuid
# import requests
import asyncio
from flask_cors import CORS, cross_origin
from flask import Flask, jsonify, request
from classification.data_loader import DataLoader
from classification.data_manipulation import DataManipulator
from classification.data_preprocessor import DataPreProcessing
from classification.model_trainer import RandomForestModelTrainer
from classification.record_handler import RecordHandler
from classification.request_handler import RequestHandler
from datetime import datetime
import pandas as pd
# from service_init import register_with_eureka


Dataset = "assets/data/Dyt-desktop.csv"
UserWithAlphabeticAwarness = "assets/data/importantUser.csv"
# Initialize our objects
dataLoader = DataLoader(UserWithAlphabeticAwarness)
dataPreProcessing = DataPreProcessing()
randomForestModelTrainer = RandomForestModelTrainer()
dataManipulator = DataManipulator()
recordHandler = RecordHandler()
requestHandler = RequestHandler()
# Preprocess our data

dekstopData = None

# register_with_eureka()
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
    #global dataLoader,dataPreProcessing,randomForestModelTrainer,dataManipulator,desktopData
    columns = dataPreProcessing.SeparateColumns(Dataset)
    desktopData = dataManipulator.DictToDataframe(columns)
    dataPreProcessing.cleanData(desktopData)
    desktopData = dataPreProcessing.removeErroredAccuracy(desktopData)
    # Split and Train Data
    randomForestModelTrainer.splitData(desktopData)
    # Train Data
    randomForestModelTrainer.trainData()
    
    
# @app.before_request
# def train_model():
    


@app.route("/screening/mockUser")
def getAlphabeticAwarnessUser():
    # get sample that has alphabetic awarness and dyslexia issues
    record = dataLoader.csvToDataframe()
    # print(record)
    # Predict Data
    prediction = randomForestModelTrainer.predictData(record)
    result_dict = record.to_dict(orient='records')[0]
    current_datetime = datetime.now()
    serialized_datetime = current_datetime.isoformat()
    generated_uuid = uuid.uuid4()
    data = {
            "data": [
                {
                "id": str(generated_uuid),
                "prediction":int(prediction[0]),
                "record":result_dict,
                "date": serialized_datetime
                }
                    ]   
         }
    return json.dumps(data)


@app.route("/screening/average")
def getAverage():
    columns = dataPreProcessing.SeparateColumns(Dataset)
    desktopData = dataManipulator.DictToDataframe(columns)
    dataPreProcessing.cleanData(desktopData)
    desktopData = dataPreProcessing.removeErroredAccuracy(desktopData)
    # data_filterd_for_user = dataManipulator.filter_data_by_age(3,30)
    # allAverages = {"average":dataManipulator.get_average(1,4,desktopData),"name":"Alphabetic Awareness"}
    allAverages = [
                    {
                        "average":dataManipulator.get_average(1,4,desktopData),
                        "name" : "Alphabetic Awareness"
                    },
                    {
                        "average":dataManipulator.get_average(5, 9, desktopData),
                        "name" : "Phonological Awareness"
                    },
                    {
                        "average":dataManipulator.get_average(18,21,desktopData),
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
        requestData, dataframe = recordHandler.prepareDataForPrediction(request.get_data())
        prediction = randomForestModelTrainer.predictData(dataframe)
        result = recordHandler.generateResult(prediction,requestData)
        return requestHandler.generateReportForUser(result,prediction)
        # return jsonify({'prediction':str(prediction[0])}) 
    except Exception as e:
        errorMessage = f"Error processing request: {str(e)}"
        return jsonify({"error": errorMessage}), 500

if(__name__ == "__main__"):
    app.run(debug=True, port=5002)