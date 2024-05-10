import pandas as pd
from . import init
import pytest
from classification.data_manipulation import DataManipulator  # Assuming your class is in a file named data_manipulator.py

init()
@pytest.fixture
def data_manipulator():
    return DataManipulator()

# Create sample data
sample_data = pd.DataFrame({
    'Age': [25, 34, 40, 45, 50],
    'Dyslexia': [1, 0, 1, 1, 0],
    'Accuracy1': [0.9, 0.85, 0.8, 0.75, 0.7],
    'Accuracy2': [0.88, 0.82, 0.78, 0.74, 0.68],
    'Accuracy3': [0.87, 0.81, 0.76, 0.72, 0.66]
})

def test_get_sample_user(data_manipulator):
    # Label to filter by
    label = 1
    result = data_manipulator.getSampleUser(label, sample_data)

    # Check if the result does not contain the 'Dyslexia' column
    assert 'Dyslexia' not in result.columns

    # Check if the sample's 'Dyslexia' value matches the label
    assert sample_data.loc[sample_data['Dyslexia'] == label].shape[0] > 0


def test_sort_values_by_age(data_manipulator):
    sorted_asc = data_manipulator.sortValuesByAge(sample_data)
    sorted_desc = data_manipulator.sortValuesByAge(sample_data, asc=False)

    # Verify the data is sorted correctly
    assert sorted_asc['Age'].is_monotonic_increasing
    assert sorted_desc['Age'].is_monotonic_decreasing

def test_filter_data_by_age(data_manipulator):
    start_age = 30
    end_age = 45
    filtered_data = data_manipulator.filter_data_by_age(sample_data, start_age, end_age)

    # Verify that the ages in the filtered data are within the specified range
    assert all(start_age <= age <= end_age for age in filtered_data['Age'])


def test_dict_to_dataframe(data_manipulator):
    columns = {'Age': [25, 30], 'Dyslexia': [1, 0]}
    df = data_manipulator.DictToDataframe(columns)

    # Verify that the output is a DataFrame
    assert isinstance(df, pd.DataFrame)

    # Verify the contents of the DataFrame
    assert list(df.columns) == list(columns.keys())
    for key in columns.keys():
        assert list(df[key]) == columns[key]


def test_get_average(data_manipulator):
    start = 1
    end = 3
    average = data_manipulator.get_average(start, end, sample_data)

    # Calculate the expected average using sample data
    expected_average = (sample_data['Accuracy1'].mean() + sample_data['Accuracy2'].mean() + sample_data['Accuracy3'].mean()) / 3

    # Check if the calculated average is as expected
    assert average == expected_average

