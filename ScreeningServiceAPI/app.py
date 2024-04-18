import json
import uuid
# import requests
import aiohttp
from flask_cors import CORS
from flask import Flask, jsonify, request
from classification.data_loader import DataLoader
from classification.data_manipulation import DataManipulator
from classification.data_preprocessor import DataPreProcessing
from classification.model_trainer import RandomForestModelTrainer
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
# Preprocess our data

dekstopData = None

# register_with_eureka()
app = Flask(__name__)
CORS(app)
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
    



@app.route("/screening/sample")
def sample():
    # Get a random with dyslexia sample
    randomUserWithDyslexia = dataManipulator.getSampleUser(1, desktopData)
    # Get a random user without dyslexia sample
    randomUserWithoutDyslexia = dataManipulator.getSampleUser(0, desktopData)
    # print(record)
    # Predict Data
    predictionUserWithDyslexia = randomForestModelTrainer.predictData(randomUserWithDyslexia)
    predictionUserWithoutDyslexia = randomForestModelTrainer.predictData(randomUserWithoutDyslexia)
    result =f"User with Dyslexia was predicted {'with Dyslexia' if predictionUserWithDyslexia[0] == 1 else 'without Dyslexia' }<br>User without Dyslexia was predicted {'with Dyslexi' if predictionUserWithoutDyslexia[0] == 1 else 'without Dyslexia'}"
    return result

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


# @app.route("/average", methods = ["POST"])
# def getAverage():
#     try:
#         # Attempt to access JSON data from the request
#         data = request.get_json()

#         if data is None:
#             raise ValueError("No JSON data in the request")

#         # Process the data (replace this with your logic)
#         result = {"message": "Received POST request", "data": data}

#         # Return a JSON response
#         return jsonify(result)

#     except Exception as e:
#         # Handle the exception (print or log the error)
#         error_message = f"Error processing the request: {str(e)}"
#         return jsonify({"error": error_message}), 400  # Return a 400 Bad Request status

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
    
    
async def make_async_request(api_url, data):
    async with aiohttp.ClientSession() as session:
        async with session.post(api_url, json=data) as response:
            return response
        
@app.route("/screening/predict",methods=["POST"])
async def predict():
    # try:
        # Attempt to access JSON data from the request
        data = request.get_json()
        # print(without_spaces = data["record"].replace(" ", ""))
        if data is None:
            raise ValueError("No JSON data in the request")
        #Predict Data
        # df = pd.read_json(json_data)
        json_data = json.loads(data)
        list_of_dicts = [json_data["record"]]
        print(type(json_data["record"]))
        # print(list_of_dicts)
        df = pd.DataFrame.from_dict(list_of_dicts)
        # print(df)
        prediction = randomForestModelTrainer.predictData(df)
        print(prediction[0])
       
    
        # Process the data (replace this with your logic)
        current_datetime = datetime.now()
        serialized_datetime = current_datetime.isoformat()
        generated_uuid = uuid.uuid4()
        result = {
            "id": json_data["id"],
            "data": 
            [
                {
                "id": str(generated_uuid),
                "prediction":int(prediction[0]),
                "record":json_data["record"],
                "date": serialized_datetime,
                }
            ]  
        }
        #Api Url
        api_url = 'http://localhost:8080/report-generation/add'
        response = await make_async_request(api_url, result)
        if response.status == 200:
            return jsonify({'message': 'POST request successful',"prediction":int(prediction[0])})
        else:
            print(response)
            return jsonify({'error': f'Error in POST request: {response}'})
    # Return a JSON response

    # except Exception as e:
    #     # Handle the exception (print or log the error)
    #     error_message = f"Error processing the request: {str(e)}"
    #     return jsonify({"error": error_message}), 500  # Return a 400 Bad Request status
    
            
    # return json.dumps(data)
if(__name__ == "__main__"):
    app.run(debug=True, port=5002)