# service_init.py
import py_eureka_client.eureka_client as eureka_client
def register_with_eureka():
    rest_port = 8000
    eureka_client.init(eureka_server="http://localhost:8761/eureka/",
                       
                   app_name="screening",
                   instance_port=rest_port)
    # # Initialize the Eureka client
    # eureka_client = Client(
    #     eureka_domain='localhost',  # Adjust this to your Eureka server domain
    #     eureka_port=8761,           # Adjust this to your Eureka server port
    #     app_name='Screening_service',  # Name of your Python service
    #     instance_port=8000,         # Port where your Python service is running
    # )

    # # Register the service with Eureka
    # eureka_client.register()

