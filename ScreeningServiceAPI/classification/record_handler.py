import json
import pandas as pd
from datetime import datetime
import uuid
from classification.model_trainer import RandomForestModelTrainer
class RecordHandler:
    def __init__(self):
        self.randomForestModelTrainer = RandomForestModelTrainer()
    def ConvertJsonRecordToString(self,jsonRecord):
        data = ''
        json_string = jsonRecord.decode('utf-8')
        index = json_string.find('"record"')
        if index != -1:
            substring = json_string[:index]
            data = data + substring
        else: 
            return "error"
        json_data = json.loads(json_string)
        record = json_data["record"]
        data = data + '"record":{'
        data+= '"Gender":"2","Nativelang":"0","Otherlang":"0","Age":"8",'
        for i in range(9):
            data = data + f'"Clicks{i+1}":' + '"' + record[i][f"clicks"] + '"' + ','
            data = data + f'"Hits{i+1}":' + '"' + record[i][f"hits"] + '"' + ','
            data = data + f'"Misses{i+1}":' + '"' + record[i][f"misses"] + '"' + ','
            data = data + f'"Score{i+1}":'+ '"' + record[i][f"score"] + '"'+ ','
            data = data + f'"Accuracy{i+1}":'+ '"' + record[i][f"accuracy"] + '"' + ','
            data = data + f'"Missrate{i+1}":'+ '"' + record[i][f"missrate"] + '"' + ','
        data += '"Clicks10":"6","Hits10":"6","Misses10":"0","Score10":"6","Accuracy10":"1","Missrate10":"0","Clicks11":"2","Hits11":"1","Misses11":"1","Score11":"1","Accuracy11":"0.5","Missrate11":"0.5","Clicks12":"0","Hits12":"0","Misses12":"0","Score12":"0","Accuracy12":"0","Missrate12":"0","Clicks13":"0","Hits13":"0","Misses13":"0","Score13":"0","Accuracy13":"0","Missrate13":"0","Clicks14":"2","Hits14":"0","Misses14":"2","Score14":"0","Accuracy14":"0","Missrate14":"1","Clicks15":"3","Hits15":"3","Misses15":"0","Score15":"3","Accuracy15":"1","Missrate15":"0","Clicks16":"5","Hits16":"4","Misses16":"1","Score16":"4","Accuracy16":"0.8","Missrate16":"0.2","Clicks17":"3","Hits17":"3","Misses17":"0","Score17":"3","Accuracy17":"1","Missrate17":"0","Clicks18":"2","Hits18":"2","Misses18":"0","Score18":"2","Accuracy18":"0","Missrate18":"0","Clicks19":"2","Hits19":"2","Misses19":"0","Score19":"2","Accuracy19":"0","Missrate19":"0","Clicks20":"4","Hits20":"1","Misses20":"3","Score20":"1","Accuracy20":"0","Missrate20":"0.75","Clicks21":"3","Hits21":"3","Misses21":"0","Score21":"3","Accuracy21":"0.5","Missrate21":"0","Clicks22":"3","Hits22":"3","Misses22":"0","Score22":"3","Accuracy22":"1","Missrate22":"0","Clicks23":"3","Hits23":"3","Misses23":"0","Score23":"3","Accuracy23":"1","Missrate23":"0","Clicks24":"1","Hits24":"1","Misses24":"0","Score24":"1","Accuracy24":"1","Missrate24":"0","Clicks25":"3","Hits25":"1","Misses25":"2","Score25":"1","Accuracy25":"0.333333","Missrate25":"0.666667","Clicks26":"2","Hits26":"2","Misses26":"0","Score26":"2","Accuracy26":"1","Missrate26":"0","Clicks27":"1","Hits27":"0","Misses27":"1","Score27":"0","Accuracy27":"0","Missrate27":"1","Clicks28":"0","Hits28":"0","Misses28":"0","Score28":"0","Accuracy28":"0","Missrate28":"0","Clicks29":"25","Hits29":"1","Misses29":"2","Score29":"1","Accuracy29":"0.04","Missrate29":"0.08","Clicks30":"2","Hits30":"2","Misses30":"0","Score30":"2","Accuracy30":"1","Missrate30":"0","Clicks31":"29","Hits31":"2","Misses31":"0","Score31":"2","Accuracy31":"0.0689655","Missrate31":"0","Clicks32":"12","Hits32":"1","Misses32":"2","Score32":"1","Accuracy32":"0.0833333","Missrate32":"0.166667"}}'
        return data
    
    def prepareRecordForPrediction(self,requestData):
        data = self.ConvertJsonRecordToString(requestData)
        if data is None:
            raise ValueError("No JSON data in the request")
        #Predict Data
        json_data = json.loads(data)
        list_of_dicts = [json_data["record"]]
        df = pd.DataFrame.from_dict(list_of_dicts)
        
        return json_data, df
    
    def generateResult(self,prediction,json_data):
        current_datetime = datetime.now()
        serialized_datetime = current_datetime.isoformat()
        generated_uuid = uuid.uuid4()
        result = {
            "id": json_data["id"],
            "data": 
            [
                {
                "id": str(generated_uuid),
                "prediction":int(prediction[0]),
                "record":json_data["record"],
                "date": serialized_datetime,
                }
            ]  
        }
        return result
            
                
            
            
            