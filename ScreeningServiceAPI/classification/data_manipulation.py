import pandas as pd

class DataManipulator:
    
    def getSampleUser(self, label:int, data):
        data[data['Dyslexia'] == label].sample(n=1, random_state=42)
        return data.drop(['Dyslexia'],axis=1)
    
    def sortValuesByAge(self, dataframe, asc:bool = True):
        return  dataframe.sort_values(by = ["Age"], ascending=asc)
    
    def filter_data_by_age(self,data, start_age, end_age):
        filtered_data = data[(data['Age'] >= start_age) & (data['Age'] <= end_age)].copy()
        return filtered_data
    
    def DictToDataframe(self,columns):
        df = pd.DataFrame.from_dict(columns)
        return df
    def get_average(self,start, end, data):
        average = 0.0
        count = 0
        for i in range(start,end+1):
            average += data[["Accuracy"+str(i)]].mean()["Accuracy"+str(i)]
            count +=1
        print("Accuracy Average of Q" + str(start) + " to Q" + str(end) + " " + str(average/count))
        return average/count
    
    def filter_data_by_age(data, start_age, end_age):
        filtered_data = data[(data['Age'] >= start_age) & (data['Age'] <= end_age)].copy()
        return filtered_data