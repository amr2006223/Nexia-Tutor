from openai import OpenAI, OpenAIError 
import time  # Module for handling time-related functions
class OpenAI :
    api_key = "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"
    client = OpenAI(api_key=api_key)
    # Function to generate an image with retry logic
    def generate_image_with_retry(self,word, model="dall-e-2", size="1024x1024"):
        try:
            # Call the OpenAI API to generate an image
            res = self.client.images.generate(
                model=model,
                prompt=word,
                n=1,
                size=size
            )

            # Return the API response
            return res

        except OpenAIError as e:
            # Retry if rate limit is exceeded
            if "rate_limit_exceeded" in str(e):
                print("Rate limit exceeded. Waiting and retrying...")
                time.sleep(60)
                return self.generate_image_with_retry(word, model, size)
            else:
                # Raise other OpenAI errors
                raise e
