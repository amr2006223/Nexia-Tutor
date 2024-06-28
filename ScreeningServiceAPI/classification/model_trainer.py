import pandas as pd
import numpy as np
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split,cross_val_score
from classification.data_preprocessor import DataPreProcessing
from imblearn.over_sampling import SMOTE

class RandomForestModelTrainer:
    def __init__(self):
        self.model = RandomForestClassifier()
        self.X_train = None
        self.X_test = None
        self.y_train = None
        self.y_test = None
        self.y_pred = None

    def prepareDataForTraining(self, dataset, dataManipulator):
        dataPreProcessing = DataPreProcessing()
        columns = dataPreProcessing.SeparateColumns(dataset)
        desktopData = dataManipulator.DictToDataframe(columns)
        dataPreProcessing.cleanData(desktopData)
        desktopData = dataPreProcessing.removeErroredAccuracy(desktopData)
        desktopData = dataPreProcessing.handle_missing_data(desktopData)
        desktopData = dataPreProcessing.drop_columns_with_missing_data(desktopData)
        cleanedDesktopData = desktopData
        self.splitData(cleanedDesktopData)
        return desktopData

    def splitData(self, dataframe):
        y = dataframe['Dyslexia']
        X = dataframe.loc[:, dataframe.columns != 'Dyslexia']
        smote = SMOTE(random_state=42)
        X_resampled, y_resampled = smote.fit_resample(X, y)
        self.X_train, self.X_test, self.y_train, self.y_test = train_test_split(
            X_resampled, y_resampled, test_size=0.2, random_state=42, stratify=y_resampled
        )

    def trainData(self):
        best_rf_params = {
            'n_estimators': 200,
            'max_depth': 40,
            'min_samples_leaf': 2,
            'bootstrap': False,
            'random_state': 42
        }
        self.model = RandomForestClassifier(**best_rf_params)
        self.model.fit(self.X_train, self.y_train)
        self.y_pred = self.model.predict(self.X_test)

    def crossValidate(self):
        cv_scores = cross_val_score(self.model, self.X_train, self.y_train, cv=5)
        return cv_scores

        
    def predictData(self, record):
        prediction = self.model.predict(record)
        return prediction
