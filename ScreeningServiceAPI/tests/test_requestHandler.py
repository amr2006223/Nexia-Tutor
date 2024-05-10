from main import app as flask_app
import pytest
from unittest.mock import patch, AsyncMock
from flask import jsonify
from classification.request_handler import RequestHandler
from . import init
init()
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

def test_generate_report_for_user_success(client):
    # Mock the make_async_request method
    with patch('main.RequestHandler.make_async_request', new=AsyncMock()) as mock_make_async_request:
        # Configure the mock to return a successful response
        mock_make_async_request.return_value.status = 200

        # Define the inputs for the function
        result = {
            # Add required fields for a valid result
        }
        prediction = [1]  # Assuming prediction is a list with at least one item

        # Create an instance of the class
        request_handler = RequestHandler()

        # Activate Flask context
        with client.application.app_context():
            # Call the function
            response = request_handler.generateReportForUser(result, prediction)

            # Check the response status code
            assert response.status_code == 200

            # Check the response data
            response_data = response.get_json()
            assert response_data == {'message': 'POST request successful', 'prediction': int(prediction[0])}


def test_generate_report_for_user_error(client):
    # Mock the make_async_request method
    with patch('main.RequestHandler.make_async_request', new=AsyncMock()) as mock_make_async_request:
        # Configure the mock to return an error response
        mock_make_async_request.return_value.status = 500

        # Define the inputs for the function
        result = {
            # Add required fields for a valid result
        }
        prediction = [1]

        # Create an instance of the class
        request_handler = RequestHandler()

        # Activate the Flask context for the function call
        with client.application.app_context():
            # Call the function
            response = request_handler.generateReportForUser(result, prediction)

            # Check the response status code
            assert response.status_code == 200

            # Check the response data
            response_data = response.get_json()
            assert 'error' in response_data  # Check that an error is returned
            assert 'Error in POST request' in response_data['error']

            # Verify that make_async_request was called with the correct arguments
            mock_make_async_request.assert_called_once_with('http://localhost:8080/report-generation/add', result)


def test_generate_report_for_user_exception(client):
    # Mock the make_async_request method
    with patch('main.RequestHandler.make_async_request', new=AsyncMock(side_effect=Exception('Mocked Exception'))) as mock_make_async_request:
        # Define the inputs for the function
        result = {
            # Add required fields for a valid result
        }
        prediction = 1

        # Create an instance of the class
        request_handler = RequestHandler()

        # Activate the Flask context for the function call
        with client.application.app_context():
            # Call the function
            response, status_code = request_handler.generateReportForUser(result, prediction)

        # Check the status code
        assert status_code == 500  # Check that a 500 status code is returned

        # Get the JSON data from the response
        response_data = response.get_json()

        # Check the response data
        assert 'error' in response_data  # Check that an error is in the response data
        assert 'Error processing the request' in response_data['error']
        
        # Verify that make_async_request was called
        mock_make_async_request.assert_called_once_with('http://localhost:8080/report-generation/add', result)


