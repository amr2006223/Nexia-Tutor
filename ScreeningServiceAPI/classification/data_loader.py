import pandas as pd
from classification.data_manipulation import DataManipulator
from classification.data_preprocessor import DataPreProcessing

class DataLoader:
    dataPreProcessing = DataPreProcessing()
    dataManipulator = DataManipulator()
    def __init__(self, file_path: str):
        self.file_path = file_path  # Use the correct attribute name here

    def excel_to_dataframe(self):
        df = pd.read_excel(self.file_path)
        return df
    
    def csvToDataframe(self):
        columns = self.dataPreProcessing.SeparateColumns(self.file_path)
        df = self.dataManipulator.DictToDataframe(columns)
        # Clean data later
        # self.dataPreProcessing.cleanData(df)
        df = df.drop(["Dyslexia"],axis=1)
        return df