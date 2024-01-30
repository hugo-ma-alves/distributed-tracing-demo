import http.client
import json
import threading
import time

def send_request():
    while True:
        conn = http.client.HTTPConnection("localhost", 8080)
        headers = {
            "Content-Type": "application/json",
            "User-Agent": "Apache-HttpClient/4.5.14 (Java/17.0.9)",
            "Accept-Encoding": "br,deflate,gzip,x-gzip"
        }
        data = {
            "clientName": "John Doe",
            "products": [
                {"id": 1, "quantity": 2},
                {"id": 3, "quantity": 1}
            ]
        }
        conn.request("POST", "/api/orders", body=json.dumps(data), headers=headers)
        response = conn.getresponse()
        print(response.status)
        conn.close()
        time.sleep(0.3)  # Optional: to prevent overwhelming the server

# Create and start 5 threads
threads = []
for i in range(5):
    thread = threading.Thread(target=send_request)
    thread.start()
    threads.append(thread)

# Keep the main thread running
try:
    while True:
        time.sleep(1)
except KeyboardInterrupt:
    print("Script stopped by user.")
