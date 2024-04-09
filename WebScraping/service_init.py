# service_init.py
from eureka import Client

def register_with_eureka():
    # Initialize the Eureka client
    eureka_client = Client(
        eureka_domain='localhost',  # Adjust this to your Eureka server domain
        eureka_port=8761,           # Adjust this to your Eureka server port
        app_name='Scraping_service',  # Name of your Python service
        instance_port=8665,         # Port where your Python service is running
    )

    # Register the service with Eureka
    eureka_client.register()

if __name__ == "__main__":
    register_with_eureka()
