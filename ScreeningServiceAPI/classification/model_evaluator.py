from sklearn.metrics import accuracy_score, precision_score, f1_score, classification_report
from sklearn.model_selection import cross_val_score
class ModelEvaluator:
    y_test = ""
    y_pred = ""
    
    def __init__(self,y_test,y_pred):
        self.y_test = y_test
        self.y_pred = y_pred
        
    def getAccuracy(self):
        return accuracy_score(self.y_test, self.y_pred)
    
    def getPercision(self):
        return precision_score(self.y_test, self.y_pred)
    
    def getF1Score(self):
        return f1_score(self.y_test, self.y_pred)
    
    def getClassificationReport(self):
        return classification_report(self.y_test, self.y_pred)
    
    def crossValidator(self,model,data):
        return cross_val_score(model,data.data,data.target)
        