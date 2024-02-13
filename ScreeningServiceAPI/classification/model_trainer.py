from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split

class RandomForestModelTrainer:
    model = ""
    X_train = ""
    X_test= ""
    y_train= ""
    y_test = ""
    
    def __init__(self):
        self.model = RandomForestClassifier()
    
    def trainData(self):
        self.model.fit(self.X_train, self.y_train)
        
    def splitData(self,dataframe):
        y = dataframe['Dyslexia']
        X = dataframe.loc[:, dataframe.columns != 'Dyslexia']
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33, random_state=42)
        self.X_train = X_train
        self.X_test = X_test
        self.y_train = y_train
        self.y_test = y_test
        return X_train, X_test, y_train, y_test
    
    def predictData(self,record):
        prediction =  self.model.predict(record)
        return prediction
    
        
