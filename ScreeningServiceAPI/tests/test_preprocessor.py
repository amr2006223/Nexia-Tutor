
import pytest
import pandas as pd
from . import init
from classification.data_preprocessor import DataPreProcessing

init()
@pytest.fixture
def data_preprocessing():
    return DataPreProcessing()

def test_separate_columns(data_preprocessing):
    # Create a sample CSV file
    csv_data = "tests/test_data.csv"
    with open(csv_data, "w") as f:
        f.write("Age;Gender;Dyslexia\n")
        f.write("25;Male;Yes\n")
        f.write("30;Female;No\n")

    # Call the function
    columns = data_preprocessing.SeparateColumns(csv_data)

    # Check the resulting dictionary
    assert columns == {
        "Age": ["25", "30"],
        "Gender": ["Male", "Female"],
        "Dyslexia": ["Yes", "No"]
    }

def test_clean_data(data_preprocessing):
    # Create a sample DataFrame
    data = pd.DataFrame({
        'Age': ['25', '30'],
        'Gender': ['Male', 'Female'],
        'Dyslexia': ['Yes', 'No'],
        'Nativelang': ['Yes', 'No'],
        'Otherlang': ['No', 'Yes']
    })

    # Call the function
    data_preprocessing.cleanData(data)

    # Check the data types and mappings
    assert data['Gender'].dtype == 'int64'
    assert data['Gender'].tolist() == [1, 2]

    assert data['Dyslexia'].dtype == 'int64'
    assert data['Dyslexia'].tolist() == [1, 0]

    assert data['Nativelang'].dtype == 'int64'
    assert data['Nativelang'].tolist() == [1, 0]

    assert data['Otherlang'].dtype == 'int64'
    assert data['Otherlang'].tolist() == [0, 1]
