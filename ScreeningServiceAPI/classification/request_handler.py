import aiohttp
import asyncio
from flask import jsonify
class RequestHandler:
    async def make_async_request(self,api_url, data, token=""):
        header = {}
        if(token != ""):
            header['Authorization'] = f'Bearer {token}'
            
        async with aiohttp.ClientSession() as session:
            async with session.post(api_url, json=data, headers=header) as response:
                return response
            
    def generateReportForUser(self,result,prediction):
        try:
            #Api Url
            api_url = 'http://localhost:8080/report-generation/add'
            response = asyncio.run(self.make_async_request(api_url, result))
            if response.status == 200:
                return jsonify({'message': 'POST request successful',"prediction":int(prediction[0])})
            else:
                return jsonify({'error': f'Error in POST request: {response}'})
        # Return a JSON response
        except Exception as e:
                # Handle the exception (print or log the error)
                error_message = f"Error processing the request: {str(e)}"
                return jsonify({"error": error_message}), 500  # Return a 400 Bad Request status