import csv
import pandas as pd
from collections import defaultdict

class DataPreProcessing:
    def SeparateColumns(self, data):
        columns = defaultdict(list)
        with open(data, 'r') as f:
            reader = csv.reader(f, delimiter=';')
            headers = next(reader)
            for row in reader:
                for h, v in zip(headers, row):
                    columns[h].append(v)
        return dict(columns)

    def cleanData(self,data):
        for col in data.columns.values:
            data[col] = data[col].astype('string')
        #----------
        for col in data.columns.values:
            data[col] = data[col].astype('float', errors = 'ignore')
        #-----------
        data['Gender'] = data.Gender.map({'Male': 1, 'Female': 2})
        data['Dyslexia'] = data.Dyslexia.map({'No': 0, 'Yes': 1})
        data['Nativelang'] = data.Nativelang.map({'No': 0, 'Yes': 1})
        data['Otherlang'] = data.Otherlang.map({'No': 0, 'Yes': 1})
     
    def removeErroredAccuracy(self,dataframe):
        for i in range(1, 32):
            accuracyData = dataframe["Accuracy" + str(i)]
            # Create a mask to identify rows where accuracy is greater than 1
            mask = accuracyData > 1
            # Drop rows based on the mask
            dataframe = dataframe[~mask]
        return dataframe