import threading
class Threading:
    def executeThread(self,functions):
        threads = []

        # Create and start the threads
        for target, args in functions:
            # Create a thread with the target function and arguments
            thread = threading.Thread(target=target, args=args)
            # Add the thread to the list of threads
            threads.append(thread)
            # Start the thread
            thread.start()

        # Join the threads to wait for their completion
        for thread in threads:
            thread.join()
