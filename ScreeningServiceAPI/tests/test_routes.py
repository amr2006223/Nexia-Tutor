
import sys
import os


# Ensure project root is in Python's search path
project_root = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
if project_root not in sys.path:
    sys.path.insert(0, project_root)

# Now that the project root is in sys.path, you can import main
from main import app as flask_app
from unittest.mock import patch
import pytest
import sys
import os


@pytest.fixture
def app():
    flask_app.config.update({
        "TESTING": True,
    })
    yield flask_app
    # Any necessary cleanup code can go here

@pytest.fixture
def client(app):
    return app.test_client()

@pytest.fixture
def runner(app):
    return app.test_cli_runner()
    
def test_getAverageSuccess(client):
    response = client.get("/screening/average")
    assert response.status_code == 200
    assert response.headers["Content-Type"] == "application/json"
    dataDict = response.get_json()
    for data in dataDict:
        assert "average" in data
        assert "name" in data
        

def test_prediction_valid_input(client):
    # Define a py te input payload
    payload = {
        "field1": "value1",
        "field2": "value2",
        # Add other required fields with valid data
    }

    # Mock `recordHandler.prepareRecordForPrediction`
    # and `randomForestModelTrainer.predictData`
    with patch('main.recordHandler.prepareRecordForPrediction') as mock_prepare_record, \
         patch('main.randomForestModelTrainer.predictData') as mock_predict_data, \
         patch('main.recordHandler.generateResult') as mock_generate_result, \
         patch('main.requestHandler.generateReportForUser') as mock_generate_report:

        # Configure the return values for the mock functions
        mock_prepare_record.return_value = ("mockedData", "mockedDataFrame")
        mock_generate_report.return_value = {"result": "testResult", "prediction": "mocked_prediction"}

        # Send POST request with the valid input
        response = client.post("/screening/predict", json=payload)

        # Check the response status code is 200 (OK)
        assert response.status_code == 200

        # Check the response data
        response_data = response.get_json()
        assert "result" in response_data
        assert "prediction" in response_data
        assert "testResult" in response_data["result"] 
        assert "mocked_prediction" in response_data["prediction"]

def test_prediction_invalid_input(client):
    # Define an invalid input payload (e.g., missing required fields or invalid data types)
    payload = {
        # Invalid Payload
    }

    # Send POST request with the invalid input
    response = client.post("/screening/predict", json=payload)

    # Check the response status code is 400 (Bad Request) or 500 (Internal Server Error) based on how your app handles invalid inputs
    assert response.status_code in [400, 500]

    # Check the response data for error message
    response_data = response.get_json()
    assert "error" in response_data
    assert isinstance(response_data["error"], str)  # The error message should be a string

def test_prediction_empty_input(client):
    # Send POST request with an empty request body
    response = client.post("/screening/predict", data='')

    # Check the response status code is 400 (Bad Request) or 500 (Internal Server Error)
    assert response.status_code in [400, 500]

    # Check the response data for error message
    response_data = response.get_json()
    assert "error" in response_data
    assert isinstance(response_data["error"], str)  # The error message should be a string
